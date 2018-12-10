package utils;

import utils.Message;

import java.util.concurrent.BlockingQueue;

public class ThreadHandler implements Runnable{

    private BlockingQueue<Message> queue;
    private Boolean stop = false;

    public ThreadHandler(BlockingQueue<Message> queue){
        this.queue = queue;
    }

    public void run() {
        while (!stop) {
            try {
                Message message = queue.take();
                System.out.println("\n"+ "==>> Received message from topic [" + message.getTopic() + "] " + message.getPayload() + " <<==");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
                break;
            }
        }
    }

    public void stop() {
        stop = true;
    }
}