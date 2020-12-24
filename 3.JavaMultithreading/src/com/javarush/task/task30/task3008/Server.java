package com.javarush.task.task30.task3008;

import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        Iterator itr = connectionMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Connection> entry = (Map.Entry<String, Connection>) itr.next();
            try {
                entry.getValue().send(message);
            } catch (IOException e) {
                System.out.println("Сообщение не отправлено");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(ConsoleHelper.readInt());

        try {
            while (true) {
                Handler hand = new Handler(socket.accept());
                hand.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    private static class Handler extends Thread {
        Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(MessageType.NAME_REQUEST));
            Message message = connection.receive();
            MessageType type = message.getType();
            while (!type.equals(MessageType.USER_NAME)) {
                return serverHandshake(connection);
            }
            if (message.getData().isEmpty() || message.getData().equals("") || connectionMap.containsKey(message.getData())) {
                return serverHandshake(connection);
            } else {
                connectionMap.put(message.getData(), connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
            }
            return message.getData();
        }

        @Override
        public void run() {
            System.out.println("Установлено соединение с удалённым адресом "+socket.getRemoteSocketAddress());
            try {
                Connection connection = new Connection(socket);
                String name = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED,name));
                notifyUsers(connection,name);
                serverMainLoop(connection,name);
                Iterator itr = connectionMap.entrySet().iterator();
                while(itr.hasNext()) {
                    connectionMap.entrySet().removeIf(entry -> entry.getKey().equals(name));
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, name));
                    break;
                }
                ConsoleHelper.writeMessage("Соединение с сервером разоврвано");
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Ошибка при обмене данными");
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            Iterator itr = connectionMap.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, Connection> entry = (Map.Entry<String, Connection>) itr.next();
                String name = entry.getKey();
                if (!name.equals(userName)) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    Server.sendBroadcastMessage(new Message(MessageType.TEXT, userName + ": " + message.getData()));
                } else {
                    ConsoleHelper.writeMessage("Сообщение не текст");
                }
            }
        }
    }
}
