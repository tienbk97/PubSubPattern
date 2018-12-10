import service.Publisher;
import utils.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Sender {

    public static void main(String[] args){

        Publisher publisher = new Publisher() {
            Socket clientSocket = null;
            DataOutputStream os;
            BufferedReader is;
            String hostname = "0.0.0.0";
            int port = 6789;

            @Override
            public void sendMessage(Message message) {

                try {
                    clientSocket = new Socket(hostname, port);
                    os = new DataOutputStream(clientSocket.getOutputStream());
                    is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    if (clientSocket == null || os == null || is == null) {
                        System.err.println( "Something is wrong. One variable is null." );
                        return;
                    }

                    os.writeBytes(message.getTopic() + "\n");
                    os.writeBytes(message.getPayload());

                    is.close();
                    os.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Scanner keyboard = new Scanner(System.in);

        while (true) {
            System.out.print("Input message: ");
            String payload = keyboard.nextLine();
            System.out.print("Input topic: ");
            String topic = keyboard.nextLine();
            System.out.print("Send to topic [" + topic + "] \"" + payload + "\"? (Y/n):");
            String y = keyboard.nextLine();
            if (y.equalsIgnoreCase("Y")) {
                System.out.println("Sent to topic [" + topic + "] \"" + payload + "\"");
                publisher.sendMessage(new Message(topic, payload));
            } else {
                System.out.println("Aborted!!!");
            }
            System.out.print("Do you want to continue? (Y/n):");
            y = keyboard.nextLine();
            if (!y.equalsIgnoreCase("y")) break;
            System.out.println("\n");
        }

    }
}
