from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from datetime import datetime
import time
from typing import Any
import requests
import json

from scrapper.cdek.tools.wait import wait_for_page_load, wait_for_url_change
from scrapper.cdek.tools.input import input_and_select_from_dropdown, input_dimension
from scrapper.cdek.tools.click import click_element
from scrapper.cdek.tools.extract import extract_cost, extract_cost_new, extract_delivery_time
from scrapper.cdek.request_object import CDEKRequestObject


post_url = "https://www.cdek.ru/api-lkfl/estimateV2/"
headers = {
    'accept': 'application/json',
    'accept-language': 'ru,en;q=0.9',
    'priority': 'u=1, i',
    'referer': 'https://www.cdek.ru/ru/',
    'sec-ch-ua': '"Chromium";v="134", "Not:A-Brand";v="24", "YaBrowser";v="25.4", "Yowser";v="2.5"',
    'sec-ch-ua-mobile': '?0',
    'sec-ch-ua-platform': '"Windows"',
    'sec-fetch-dest': 'empty',
    'sec-fetch-mode': 'cors',
    'sec-fetch-site': 'same-origin',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 YaBrowser/25.4.0.0 Safari/537.36',
    'x-site-code': 'ru',
    'x-user-lang': 'rus',
}

class GetCDEKDeliveryCostAndTime:

    @staticmethod
    def save_page_source(driver, prefix="page"):
        """Сохранение HTML-кода страницы в файл с временной меткой."""
        try:
            timestamp = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
            filename = f"{prefix}_{timestamp}.html"
            with open(filename, "w", encoding="utf-8") as f:
                f.write(driver.page_source)
            print(f"Страница сохранена в файл: {filename}")
        except Exception as e:
            print(f"Ошибка при сохранении страницы: {e}")

    @staticmethod
    def execute(params: CDEKRequestObject) -> tuple[int, str|list[dict[str, Any]]]:
        payload = {
            "payerType": "sender",
            "currencyMark": "RUB",
            "senderCityId": params.sender_city,
            # "senderCityId": "01581370-81f3-4322-9a28-3418adfabd97",
            "receiverCityId": params.receiver_city,
            # "receiverCityId": "b7af1c1b-b82c-464d-b744-e12ce0ff5f98",
            "packages": [
                {
                    "height": params.height,
                    "width": params.width,
                    "length": params.length,
                    "weight": params.weight
                }
            ]
        }

        session = requests.Session()
        response = session.post(post_url, json=payload, headers=headers)
        status_code = response.status_code
        if status_code == 200:
            data = (json.loads(response.text)).get('data', [])
        else:
            data = response.text

        return status_code, data

    @staticmethod
    def _execute(params: CDEKRequestObject) -> tuple[str, str]:
        url = "https://www.cdek.ru/ru/"
        
        # Настройка headless-браузера
        chrome_options = Options()
        chrome_options.add_argument("--headless")  # Запуск без GUI
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")
        chrome_options.add_argument("--disable-gpu")
        chrome_options.add_argument("--disable-direct-composition")
        chrome_options.add_argument("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36")
        chrome_options.add_argument("--enable-unsafe-swiftshader")
        chrome_options.add_argument("--disable-blink-features=AutomationControlled")
        chrome_options.add_argument("--disable-site-isolation-trials")
        chrome_options.add_argument("--disable-features=UserAgentClientHint")
        chrome_options.add_argument("--window-size=1920,1080")
        chrome_options.add_argument("--accept-third-party-cookies")  # Включение third-party cookies

        # Инициализация драйвера с автоматическим управлением ChromeDriver
        service = Service(ChromeDriverManager().install())
        driver = webdriver.Chrome(service=service, options=chrome_options)
        
        try:
            # Переход на указанный URL и ожидание загрузки страницы
            driver.get(url)
            wait_for_page_load(driver, 100)
            time.sleep(10)
            
            # Ввод текста и выбор из dropdown для поля "Откуда"
            input_and_select_from_dropdown(
                driver,
                "//div[contains(text(), 'Откуда')]/following-sibling::input[@class='cdek-input__input']",
                "cdek-dropdown-item__content",
                params.sender_city,
                "Откуда"
            )
            
            # Ввод текста и выбор из dropdown для поля "Куда"
            input_and_select_from_dropdown(
                driver,
                "//div[contains(text(), 'Куда')]/following-sibling::input[@class='cdek-input__input']",
                "cdek-dropdown-item__content",
                params.receiver_city,
                "Куда"
            )
            
            # Нажатие на SVG-кнопку для открытия dropdown "Размер посылки"
            click_element(
                driver,
                "//div[contains(@class, 'cdek-dropdown-trigger__control') and .//label[text()='Размер посылки']]",
                "SVG-кнопка для 'Размер посылки'"
            )
            
            # Нажатие на вкладку "Точные"
            click_element(
                driver,
                "//div[contains(@class, 'segment-control__tab') and text()='Точные']",
                "Вкладка 'Точные'"
            )
            
            # Ввод значений в поля размеров
            input_dimension(driver, "Длина", str(params.length))
            input_dimension(driver, "Ширина", str(params.width))
            input_dimension(driver, "Высота", str(params.height))
            input_dimension(driver, "Вес", str(params.weight))
            
            # Нажатие на кнопку "Подтвердить"
            click_element(
                driver,
                "//button[contains(@class, 'cdek-button') and text()='Подтвердить']",
                "Кнопка 'Подтвердить'"
            )
            
            # Нажатие на кнопку "Рассчитать"
            click_element(
                driver,
                "//button[contains(@class, 'cdek-button') and text()='Рассчитать']",
                "Кнопка 'Рассчитать'"
            )

            # Проверка перехода
            expected_urls = [
                "https://www.cdek.ru/ru/cabinet/calculate/",
                "https://my.cdek.ru/lkfl/delivery"
            ]
            wait_for_url_change(driver, expected_urls, timeout=30)
            # save_page_source(driver, prefix="after_url_change")
            
            # Извлечение стоимости в зависимости от URL
            current_url = driver.current_url
            if "my.cdek.ru" in current_url:
                cost = extract_cost_new(driver)
            else:
                cost = extract_cost(driver)
            delivery_time = extract_delivery_time(driver, current_url)
            return (cost, delivery_time)
        
        finally:
            # Закрытие браузера
            driver.quit()