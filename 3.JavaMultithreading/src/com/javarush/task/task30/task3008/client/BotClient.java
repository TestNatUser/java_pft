package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {

    public static void main(String[] args) {
        BotClient client = new BotClient();
        client.run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (Math.random() * 100);
    }

    public class BotSocketThread extends SocketThread {

        protected void clientMainLoop(){
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            try {
                super.clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
            if (message != null && message.contains(":")) {
                String i = message.split(":")[1].trim();
                String name = message.split(":")[0].trim();
                String pattern="dd";
                if (i.equals("дата")) {
                    pattern = "d.MM.YYYY";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("день")) {
                    pattern = "d";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("месяц")) {
                    pattern = "MMMM";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("год")) {
                    pattern = "YYYY";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("время")) {
                    pattern = "H:mm:ss";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("час")) {
                    pattern = "H";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("минуты")) {
                    pattern = "m";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                } else if (i.equals("секунды")) {
                    pattern = "s";
                    sendTextMessage("Информация для "+name+": "+new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime()));
                }
            }
        }
    }
}
