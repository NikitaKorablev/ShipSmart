from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
import time


def input_and_select_from_dropdown(driver, input_xpath: str, dropdown_class: str, input_text: str, field_name, max_attempts=3, retry_delay=2):
    """Ввод текста в поле и выбор первого элемента из dropdown с повторными попытками."""
    try:
        input_field = WebDriverWait(driver, 30).until(
            EC.presence_of_element_located((By.XPATH, input_xpath))
        )
        # input_field.click()
        # input_field.send_keys(input_text)
        # print(f"Введен текст '{input_text}' в поле '{field_name}'")
        
        for attempt in range(max_attempts):
            try:
                input_field.send_keys(input_text)
                print(f"Введен текст '{input_text}' в поле '{field_name}'")
                dropdown_items = WebDriverWait(driver, 20).until(
                    EC.presence_of_all_elements_located((By.CLASS_NAME, dropdown_class))
                )
                if dropdown_items:
                    first_item = dropdown_items[0]
                    print(f"Выбран первый элемент для '{field_name}': {first_item.text}")
                    driver.execute_script("arguments[0].click();", first_item)  # JavaScript-клик
                    return
                print(f"Попытка {attempt + 1}/{max_attempts}: Dropdown элементы не найдены для '{field_name}'")
                time.sleep(retry_delay)
            except:
                print(f"Попытка {attempt + 1}/{max_attempts}: Dropdown не найден для '{field_name}'")
                if attempt < max_attempts - 1:
                    time.sleep(retry_delay)
        
        print(f"Dropdown не появился для '{field_name}' после {max_attempts} попыток, нажимаем Enter")
        input_field.send_keys(Keys.ENTER)
    
    except Exception as e:
        print(f"Ошибка при обработке поля '{field_name}': {e}")


def input_dimension(driver, label_text: str, dimension_value: str):
    """Ввод значения в поле размера."""
    try:
        dimension_input = WebDriverWait(driver, 5).until(
            EC.presence_of_element_located(
                (By.XPATH, f"//p[text()='{label_text}']/following-sibling::div[@class='cdek-input choice-form__input']//input")
            )
        )
        # dimension_input.click()
        dimension_input.send_keys(str(dimension_value))
        print(f"Введено значение для '{label_text}': {dimension_value}")
    except Exception as e:
        print(f"Ошибка при вводе значения для '{label_text}': {e}")
