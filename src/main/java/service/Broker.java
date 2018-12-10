package service;

import utils.Message;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {

    private static Map<String, BlockingQueue<Message>> out;

    private static BlockingQueue<Message> in;

    private static Broker _instance;

    private Broker() {
        this.out = new HashMap<String, BlockingQueue<Message>>();
        this.in  = new LinkedBlockingQueue<>();
    }

    public static BlockingQueue<Message> getQueueByTopic(String topic) {
        if (!out.containsKey(topic)){
            BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
            out.put(topic, queue);
            return out.get(topic);
        } else {
            return out.get(topic);
        }
    }

    public static void addMessage(Message message) {
        try {
            _instance.getInQueue().put(message);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static BlockingQueue<Message> getInQueue(){
        return in;
    }

    public static Broker get_instance() {
        if (_instance == null) {
            _instance = new Broker();
            start();
        }
        return _instance;
    }

    public static void start() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Message message = in.take();
                        String topic = message.getTopic();
                        if (out.containsKey(topic)){
                            BlockingQueue<Message> queue = out.get(topic);
                            queue.put(message);
                        } else {
                            BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
                            out.put(topic, queue);
                            queue.put(message);
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
