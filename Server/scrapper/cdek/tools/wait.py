from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


def wait_for_page_load(driver, timeout=10):
    """Ожидание полной загрузки страницы (document.readyState == 'complete')."""
    try:
        WebDriverWait(driver, timeout).until(
            lambda d: d.execute_script("return document.readyState") == "complete"
        )
        print("Страница полностью загружена (document.readyState == 'complete')")
    except:
        print(f"Ошибка: страница не загрузилась за {timeout} секунд")

def wait_for_url_change(driver, expected_urls, timeout=30):
    """Ожидание перехода на указанный URL с 5 попытками."""
    max_attempts = 5
    for attempt in range(1, max_attempts + 1):
        try:
            WebDriverWait(driver, timeout).until(
                lambda d: any(url in d.current_url for url in expected_urls)
            )
            print(f"Успешный переход на URL: {expected_urls} (попытка {attempt})")
            # save_page_source(driver, prefix=f"wait_for_url_success_attempt_{attempt}")
            return True
        except:
            current_url = driver.current_url
            print(f"Попытка {attempt}/{max_attempts}: Переход на URL {expected_urls} не произошел. Текущий URL: {current_url}")
            # save_page_source(driver, prefix=f"wait_for_url_attempt_{attempt}")
    
    print(f"Переход на URL {expected_urls} не произошел после {max_attempts} попыток")
    return False