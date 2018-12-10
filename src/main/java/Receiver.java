import service.Broker;
import service.Subscriber;
import utils.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Receiver {

    static ServerSocket echoServer = null;
    static Socket clientSocket = null;
    static int port = 6789;

    public static void main(String[] args) {

        try {
            echoServer = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e);
        }
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            clientSocket = echoServer.accept();
                            BufferedReader is;
                            PrintStream os;
                            try {
                                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                os = new PrintStream(clientSocket.getOutputStream());
                                if (is == null || os == null) {
                                    System.out.println("Something wrong!!!");
                                }

                                String topic = is.readLine();
                                String payload = is.readLine();

                                Message message = new Message(topic, payload);

                                Broker.get_instance().addMessage(message);

                                is.close();
                                os.close();
                            } catch (IOException e) {
                                System.out.println(e);
                            }
                            clientSocket.close();
                        } catch (IOException e) {
                            System.out.println(e);
                        }
                    }
                }
            }
        ).start();

        Scanner keyboard = new Scanner(System.in);

        Subscriber subscriber = new Subscriber();
        while (true) {
            System.out.println("1. Subscribe");
            System.out.println("2. Unsubscribe");
            System.out.print("Pick one of the options above: ");
            String y = keyboard.nextLine();
            if (y.equalsIgnoreCase("1")) {
                System.out.print("Subscribe to topic: ");
                String topic = keyboard.nextLine();
                subscriber.subscribe(topic, Broker.get_instance());
            } else if (y.equalsIgnoreCase("2")) {
                System.out.print("Unsubscribe to topic: ");
                String topic = keyboard.nextLine();
                subscriber.unsubscribe(topic, Broker.get_instance());
            } else {
                break;
            }

            System.out.println("\n");
        }


    }
}
