from quart import Quart, jsonify, request

from scrapper.cdek.request_object import CDEKRequestObject
from scrapper.cdek.scrapper import GetCDEKDeliveryCostAndTime

from utils.logger_init import _logger


app = Quart(__name__)
logger = _logger(name=__name__)

@app.route('/get_delivery_cost', methods=['GET'])
async def get_delivery_cost():
    params = CDEKRequestObject(request.args)
    if not params.is_valid():
        response_data = {
            "status": "error",
            "message": "Invalid request parameters"
        }
        return jsonify(response_data), 400

    cost, time = GetCDEKDeliveryCostAndTime.execute(params)
    response_data = {
        "status": "success",
        "message": "Request processed successfully",
        "cost": cost,
        "time": time
    }
    return jsonify(response_data), 200

# Запуск Quart приложения
if __name__ == '__main__':
    app.run(debug=True, port=8100, host="0.0.0.0")