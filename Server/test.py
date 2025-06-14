import requests
import json

# Создаем объект сессии
session = requests.Session()

# Выполняем GET-запрос, чтобы получить cookies
# url = "https://www.cdek.ru/ru/"
# response = session.get(url)

# Cookies автоматически сохраняются в объекте сессии
# Можно посмотреть cookies, если нужно
# print(session.cookies.get_dict())

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

# payload = {
#     'str': 'Москва',
#     'limit': '10',
# }

# response = session.get("https://www.cdek.ru/api-lkfl/cities/autocomplete/", params=payload, headers=headers)
# # Проверяем результат
# print(session.cookies.get_dict())
# print(response.status_code)
# data = json.loads(response.text)
# uuid_sender = data.get('data')[0].get('uuid')
# city_code_sender = data.get('data')[0].get('id')
# print(uuid_sender, city_code_sender)

# response = session.get(f"https://www.cdek.ru/api-lkfl/currencies/?cityCode={city_code_sender}", headers=headers)
# print(response.status_code)
# print(response.text)

# payload = {
#     'str': 'Нижний Новгород',
#     'limit': '10',
# }

# response = session.get("https://www.cdek.ru/api-lkfl/cities/autocomplete/", params=payload, headers=headers)
# # Проверяем результат
# print(session.cookies.get_dict())
# print(response.status_code)
# data = json.loads(response.text)
# uuid_receiver = data.get('data')[0].get('uuid')
# city_code_receiver = data.get('data')[0].get('id')
# print(uuid_receiver, city_code_receiver)

# response = session.get(f"https://www.cdek.ru/api-lkfl/currencies/?cityCode={city_code_receiver}", headers=headers)
# print(response.status_code)
# print(response.text)


# Данные для POST-запроса
payload = {
    "payerType": "sender",
    "currencyMark": "RUB",
    # "senderCityId": uuid_sender,
    "senderCityId": "01581370-81f3-4322-9a28-3418adfabd97",
    # "receiverCityId": uuid_receiver,
    "receiverCityId": "b7af1c1b-b82c-464d-b744-e12ce0ff5f98",
    "packages": [
        {
            "height": 50,
            "width": 100,
            "length": 100,
            "weight": 10
        }
    ]
}

# Отправляем POST-запрос с использованием той же сессии
post_url = "https://www.cdek.ru/api-lkfl/estimateV2/"
response = session.post(post_url, json=payload, headers=headers)

# Проверяем результат
print(response.status_code)
# print(response.text)
data = json.loads(response.text)
print(data.get("data"))

# Если нужно, можно снова посмотреть cookies после POST-запроса
# print(session.cookies.get_dict())