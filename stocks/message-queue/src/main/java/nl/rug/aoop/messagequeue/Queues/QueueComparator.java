package nl.rug.aoop.messagequeue.Queues;

import nl.rug.aoop.messagequeue.Messages.Message;

import java.util.Comparator;

/**
 * Queue comparator class used to compare the timestamp in the ordered queue.
 */
public class QueueComparator implements Comparator<Message> {

    /**
     * Method used to compare two timestamps.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return Whether the first or second object is greater.
     */
    @Override
    public int compare(Message o1, Message o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
