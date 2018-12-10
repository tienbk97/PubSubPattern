package service;

import service.Broker;
import utils.Message;
import utils.ThreadHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Subscriber {
    Map<String, BlockingQueue<Message>> list = new HashMap<String, BlockingQueue<Message>>();
    Map<String, ThreadHandler> threadList = new HashMap<String, ThreadHandler>();

    public void subscribe(String topic, Broker broker){
        BlockingQueue<Message> queue = broker.getQueueByTopic(topic);
        list.put(topic, queue);
        ThreadHandler threadHandler = new ThreadHandler(queue);
        threadList.put(topic, threadHandler);
        new Thread(threadHandler).start();
    }

    public void unsubscribe(String topic, Broker broker) {
        ThreadHandler threadHandler = threadList.get(topic);
        threadHandler.stop();
    }
}
