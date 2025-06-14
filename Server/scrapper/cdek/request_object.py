from werkzeug.datastructures import MultiDict 

class CDEKRequestObject:
    def __init__(self, args: MultiDict[str, str]) -> None:
        self.sender_city = args.get("senderCityId", "")
        self.receiver_city = args.get("receiverCityId", "")
        self.length = int(args.get("length", 0))
        self.width = int(args.get("width", 0))
        self.height = int(args.get("height", 0))
        self.weight = int(args.get("weight", 0))

    def is_valid(self) -> bool:
        if self.sender_city == "" or self.receiver_city == "":
            return False
        
        if (self.length <= 0 or self.height <= 0 
            or self.width <= 0 or self.weight <= 0):
            return False

        return True