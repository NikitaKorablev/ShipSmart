# from quart import Quart, 
from flask import Flask, jsonify, request

from scrapper.cdek.request_object import CDEKRequestObject
from scrapper.cdek.scrapper import GetCDEKDeliveryCostAndTime

from utils.logger_init import _logger


app = Flask(__name__)
logger = _logger(name=__name__)

@app.route('/get_delivery_cost', methods=['GET'])
def get_delivery_cost():
    params = CDEKRequestObject(request.args)
    if not params.is_valid():
        response_data = {
            "status": "error",
            "message": "Invalid request parameters"
        }
        logger.error(f"{400} : {response_data}")
        return jsonify(response_data), 400

    status, data = GetCDEKDeliveryCostAndTime.execute(params)
    if status == 200:
        response_data = {
            "status": "success",
            "message": "Request processed successfully",
            "data": data
        }
        return jsonify(response_data), 200
    else:
        response_data = {
            "status": "error",
            "message": data
        }
        logger.error(f"{status} : {response_data}")
        return jsonify(response_data), status


# Запуск Flask приложения
if __name__ == '__main__':
    app.run(debug=True, port=8100, host="0.0.0.0")