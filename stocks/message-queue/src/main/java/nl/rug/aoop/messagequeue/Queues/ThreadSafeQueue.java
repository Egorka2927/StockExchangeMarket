package nl.rug.aoop.messagequeue.Queues;

import nl.rug.aoop.messagequeue.Messages.Message;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Queue that is thread safe class.
 */
public class ThreadSafeQueue implements MessageQueue {

    private PriorityBlockingQueue<Message> threadSafeQueue;

    /**
     * Constructor that initializes the queue.
     */
    public ThreadSafeQueue() {
        threadSafeQueue = new PriorityBlockingQueue<>();
    }

    @Override
    public void enqueue(Message message) {
        if (message != null && message.getHeader() != null && message.getBody() != null) {
            threadSafeQueue.add(message);
        }
    }

    @Override
    public Message dequeue() {
        if (threadSafeQueue.isEmpty()) {
            return null;
        } else {
            return threadSafeQueue.poll();
        }
    }

    @Override
    public int getSize() {
        return threadSafeQueue.size();
    }
}
