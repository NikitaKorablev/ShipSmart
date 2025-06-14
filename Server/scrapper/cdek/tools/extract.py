from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time
import re


def extract_cost(driver, max_attempts: int = 5, retry_delay: int = 3) -> str:
    """Извлечение стоимости из элемента с классом 'cost' как число (для https://www.cdek.ru)."""
    for attempt in range(max_attempts):
        try:
            cost_element = WebDriverWait(driver, 15).until(
                EC.presence_of_element_located((By.XPATH, "//div[@class='cost']//span[@class='total']"))
            )
            cost_text = cost_element.text.strip()
            cost = cost_text.replace(" ₽", "").replace(" ", "").replace(",", ".")
            print(f"Извлечена стоимость (cdek.ru): {cost}")
            return cost
        except:
            print(f"Попытка {attempt + 1}/{max_attempts}: Стоимость не найдена (cdek.ru)")
            if attempt < max_attempts - 1:
                time.sleep(retry_delay)
    print(f"Стоимость не найдена (cdek.ru).\nКоличество попыток: {max_attempts}")
    return "0"

def extract_cost_new(driver, max_attempts: int = 5, retry_delay: int = 3) -> str:
    """Извлечение стоимости из элемента с классом 'title-h2' (для https://my.cdek.ru)."""
    for attempt in range(max_attempts):
        try:
            cost_element = WebDriverWait(driver, 15).until(
                EC.presence_of_element_located((By.XPATH, "//div[contains(@class, 'flex justify-between')]//div[contains(@class, 'title-h2')]"))
            )
            cost_text = cost_element.text.strip()
            cost = (cost_text.replace(" ₽", "").replace(" ", "").replace(",", "."))
            print(f"Извлечена стоимость (my.cdek.ru): {cost}")
            return cost
        except:
            print(f"Попытка {attempt + 1}/{max_attempts}: Стоимость не найдена (my.cdek.ru)")
            if attempt < max_attempts - 1:
                time.sleep(retry_delay)
    print(f"Стоимость не найдена (my.cdek.ru).\nКоличество попыток: {max_attempts}")
    return "0"


def extract_delivery_time(driver, current_url: str, max_attempts: int = 5, retry_delay: int = 3) -> str:
    """Извлечение минимального срока доставки как число (для cdek.ru или my.cdek.ru)."""
    if "my.cdek.ru" in current_url:
        # Для https://my.cdek.ru/lkfl/delivery
        for attempt in range(max_attempts):
            try:
                time_elements = WebDriverWait(driver, 15).until(
                    EC.presence_of_all_elements_located(
                        (By.XPATH, "//div[contains(@class, 'swiper-slide')]//div[contains(@class, 'body-regular-base-paragraph') and contains(text(), 'дня')]")
                    )
                )
                delivery_times: list[int] = []
                for element in time_elements:
                    time_text = element.text.strip()
                    # Извлекаем число из текста, например, "~3 дня" → 3
                    match = re.search(r'~(\d+)\s*дн', time_text)
                    if match:
                        delivery_times.append(int(match.group(1)))
                if delivery_times:
                    min_time = min(delivery_times)
                    print(f"Извлечён минимальный срок доставки (my.cdek.ru): {min_time} дня")
                    return str(min_time)
                print(f"Попытка {attempt + 1}/{max_attempts}: Срок доставки не найден (my.cdek.ru)")
                if attempt < max_attempts - 1:
                    time.sleep(retry_delay)
            except:
                print(f"Попытка {attempt + 1}/{max_attempts}: Срок доставки не найден (my.cdek.ru)")
                if attempt < max_attempts - 1:
                    time.sleep(retry_delay)
        print(f"Срок доставки не найден (my.cdek.ru).\nКоличество попыток: {max_attempts}")
        return "0"
    else:
        # Для https://www.cdek.ru/ru/cabinet/calculate/
        for attempt in range(max_attempts):
            try:
                time_element = WebDriverWait(driver, 15).until(
                    EC.presence_of_element_located(
                        (By.XPATH, "//div[@class='row' and contains(text(), 'Срок доставки')]//span[@class='value']")
                    )
                )
                time_text = time_element.text.strip()
                # Извлекаем минимальное число из диапазона, например, "2—3 рабочих дня" → 2
                match = re.search(r'(\d+)(?:—\d+)?', time_text)
                if match:
                    min_time = int(match.group(1))
                    print(f"Извлечён минимальный срок доставки (cdek.ru): {min_time} дня")
                    return str(min_time)
                print(f"Попытка {attempt + 1}/{max_attempts}: Срок доставки не найден (cdek.ru)")
                if attempt < max_attempts - 1:
                    time.sleep(retry_delay)
            except:
                print(f"Попытка {attempt + 1}/{max_attempts}: Срок доставки не найден (cdek.ru)")
                if attempt < max_attempts - 1:
                    time.sleep(retry_delay)
        print(f"Срок доставки не найден (cdek.ru).\nКоличество попыток: {max_attempts}")
        return "0"