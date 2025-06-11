import logging
import os


def _logger(name: str, log_mod: str = 'w'):
    name = name.split('.')[-1]
    logger = logging.getLogger(name)
    logger.setLevel(logging.INFO)
    
    if not os.path.exists('logs'):
        os.makedirs('logs')
        
    # настройка обработчика и форматировщика для logger
    handler = logging.FileHandler(f"logs/{name}.log", mode=log_mod, encoding='utf-8')
    formatter = logging.Formatter(
        fmt="%(asctime)s - %(levelname)s - %(message)s",
        datefmt="%Y-%m-%d %H:%M:%S"
    )

    # добавление форматировщика к обработчику 
    handler.setFormatter(formatter)
    # добавление обработчика к логгеру
    logger.addHandler(handler)

    return logger