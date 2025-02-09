from dotenv import load_dotenv
import requests
import os



class APIService:

    def __init__(self):
        load_dotenv()
        self.__api_token = os.getenv("BOT_TOKEN")
        self.__base_url = os.getenv("BASE_URL")
        self.__auth_url = os.getenv("AUTH_URL")


    def check_if_user_exists_by_phone_number(self,phone_number :str) -> bool:
        res = requests.get(self.__auth_url+"user_exists_by_phoneNumber/"+phone_number)
        return res.json()

    def check_if_vendor_exists_by_telegram_user_id(self,telegram_user_id:str)-> bool:
        res = requests.get(self.__auth_url+"vendor_exists_by_telegram_user_id/"+telegram_user_id)
        return res.json()
    
    def check_if_optedin_by_phone_number(self,phone_number:str)->bool:
        res = requests.get(self.__base_url+"users/vendors/optedin/"+phone_number,
        headers={
            "Bot-Token":self.__api_token,
            "Phone-Number":phone_number
        })

        return res

if __name__=='__main__':
    api = APIService()
    print(api.check_if_optedin("918825596752"))



