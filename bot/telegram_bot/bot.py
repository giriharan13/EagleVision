import telebot
import os
from dotenv import load_dotenv
from database import UserDatabaseService
from telebot import types #add-ons connected


load_dotenv()
token = os.getenv("BOT_TOKEN")

bot = telebot.TeleBot(token)

user_db_service = UserDatabaseService()

def main():
    bot.polling()


@bot.message_handler(commands=['start'])
def help(message):
    bot.send_message (message.chat.id, """
    Welcome to Eagle Vision Bot for vendors!
    This bot allows you to receive shop reviews and pings directly to your Telegram account and notifies you of updates.

    /help - to get the list of commands available

    """) 

@bot.message_handler(commands=['help'])
def help(message):
    bot.send_message (message.chat.id, """
    Welcome to EagleVision Bot🦅!
        Telegram Notifications:Telegram Notifications notifies you when your items/shops get pingged or reviewed by customers.

        Here is a list of available commands:
        /optin - Subscribe to Telegram notifications
        /optout - Unsubscribe from Telegram notifications
        /shop {shopName} - Provides statistics about a shop
        /item {shopName} {itemName} - Provides statistics about a item
        /ping {itemName} {quantity} - pings a item with the available quantity
        /itemreviews {shopName} {itemName} - fetches the top 10 reviews of your item in a shop
        /shopreviews {shopName} - fetches the top 10 reviews of your shops
        
    """) 



@bot.message_handler(commands=['optin'])
def request_for_contact(message):
    keyboard = types.ReplyKeyboardMarkup (row_width = 1, resize_keyboard = True,one_time_keyboard=True)
    button_phone = types.KeyboardButton (text = "Send contact", request_contact = True) 
    keyboard.add (button_phone)
    bot.send_message (message.chat.id, 'Please share your contact information to opt in for notifications.', reply_markup = keyboard) 

@bot.message_handler(commands=['optout'])
def request_for_contact(message):
    if(not user_db_service.check_if_exists_by_id(message.from_user.id)):
        print(message.contact.phone_number[2:])
        bot.reply_to(message,"Your number is not associated with any vendor account.")
        return

    if(not user_db_service.check_if_optedin_by_id(message.from_user.id)):
        bot.reply_to(message,"You have not opted in for Telegram notifications yet!")
        return
    
    rows_affected = user_db_service.optout(str(message.from_user.id))
    print(rows_affected)
    if(rows_affected==0):
        bot.reply_to(message,"An internal error occurred. Please try again later.")
        return
    bot.reply_to(message,"You have successfully opted out of Telegram notifications.")
     


@bot.message_handler(content_types = ['contact']) 
def turn_on_notifications(message):

    if(message.from_user.id != message.contact.user_id):
        bot.reply_to(message,"Please share your own contact information, not someone else's!")
        return
    if(not user_db_service.check_if_exists(message.contact.phone_number[2:])):
        print(message.contact.phone_number)
        bot.reply_to(message,"Your number is not associated with any vendor account.")
        return

    if(user_db_service.check_if_optedin(message.contact.phone_number[2:])):
        bot.reply_to(message,"You are already subscribed to Telegram notifications!s!")
        return
    
    rows_affected = user_db_service.optin(message.contact.phone_number[2:],message.contact.user_id)
    print(rows_affected)
    if(rows_affected==0):
        bot.reply_to(message,"An internal error occured.Please try again later.")
        return
    bot.reply_to(message,"You have successfully opted in for Telegram notifications.")

    

if __name__=='__main__':
    main()


