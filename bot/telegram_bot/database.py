import psycopg2
from dotenv import load_dotenv
import os


class UserDatabaseService:

    def __init__(self):
        load_dotenv()
        self.__database_name = os.getenv("DATABASE_NAME")
        self.__host = os.getenv("HOST")
        self.__port = os.getenv("PORT")
        self.__user = os.getenv("USER_NAME")
        self.__password = os.getenv("PASSWORD")

        self.__conn = psycopg2.connect(
            database = self.__database_name,
            host = self.__host,
            user = self.__user,
            password = self.__password,
            port = self.__port
        )

        self.__cursor = self.__conn.cursor()

    def check_if_exists(self,phone_number : str) -> bool:
        self.__cursor.execute(f"SELECT * from users WHERE phone_number = '{phone_number}' ;")
        if(self.__cursor.fetchone()):
            return True
        return False

    def check_if_exists_by_id(self,user_id : str) -> bool:
        self.__cursor.execute(f"SELECT * from vendor WHERE telegram_user_id = '{user_id}';")
        if(self.__cursor.fetchone()):
            return True
        return False

    def check_if_optedin(self,phone_number:str) -> bool:
        self.__cursor.execute(f"SELECT v.opted_for_telegram_notifications from users u JOIN vendor v on u.user_id = v.user_id WHERE u.phone_number = '{phone_number}';")
        user = self.__cursor.fetchone()
        return user[0]
    
    def check_if_optedin_by_id(self,user_id:str) -> bool:
        self.__cursor.execute(f"SELECT opted_for_telegram_notifications from vendor WHERE telegram_user_id='{user_id}'")
        user = self.__cursor.fetchone()
        return user[0]

    def optin(self,phone_number:str,user_id:str,chat_id)-> bool:
        try:
            self.__cursor.execute("""
                    UPDATE vendor
                    SET opted_for_telegram_notifications = true,
                    telegram_user_id = %s,telegram_chat_id = %s
                    FROM users
                    WHERE vendor.user_id = users.user_id
                    AND users.phone_number = %s""",(user_id,chat_id,phone_number))
            rows_affected = self.__cursor.rowcount
            self.__conn.commit()
        except Exception as e:
            return 0
        return rows_affected

    def optout(self,user_id:str)-> int:
        try:
            self.__cursor.execute("""
                    UPDATE vendor
                    SET opted_for_telegram_notifications = false,
                    telegram_user_id = NULL
                    WHERE vendor.telegram_user_id = %s""",(user_id,))
            rows_affected = self.__cursor.rowcount
            self.__conn.commit()
        except Exception as e:
            return 0
        return rows_affected

    def fetch_shop_reviews(shopId:int)-> list:
        self.__cursor.execute("""
            SELECT * FROM shop_reviews 
        """)


        
        
