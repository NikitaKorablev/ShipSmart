from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def click_element(driver, xpath, element_name):
    """Клик по элементу с использованием JavaScript и fallback на стандартный клик."""
    try:
        element = WebDriverWait(driver, 10).until(
            EC.element_to_be_clickable((By.XPATH, xpath))
        )
        try:
            driver.execute_script("arguments[0].click();", element)  # Попытка JavaScript-клика
            print(f"Нажат элемент (JavaScript): '{element_name}'")
        except:
            element.click()  # Fallback на стандартный клик
            print(f"Нажат элемент (стандартный клик): '{element_name}'")
    except Exception as e:
        print(f"Не удалось найти или нажать элемент '{element_name}': {e}")
