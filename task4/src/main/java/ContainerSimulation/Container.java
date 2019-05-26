package ContainerSimulation;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Container {
    private LinkedBlockingDeque<Entry> entries = new LinkedBlockingDeque<>(20);
    private AtomicInteger curAmount = new AtomicInteger(0);
    private long timeout = 70;

    public Container() { }

    public boolean addEntry(int entryId) {
        checkTimeout();
        if (curAmount.get() >= 20) return false;
        System.out.println("[" + Thread.currentThread().getId() + "]" + "added " + entryId);
        curAmount.incrementAndGet();
        entries.addLast(new Entry(entryId));
        return true;
    }

    public Entry getEntry() {
        checkTimeout();
        if (curAmount.get() < 10) {
            //FIFO
            System.out.println("[" + Thread.currentThread().getId() + "]" + "get:FIFO");
            curAmount.decrementAndGet();
            return entries.pollFirst();
        } else {
            //FILO
            System.out.println("[" + Thread.currentThread().getId() + "]" + "get:FILO");
            curAmount.decrementAndGet();
            return entries.pollLast();
        }
    }

    private void checkTimeout() {
        while (true) {
            long now = System.currentTimeMillis();
            try {
                Entry entry = this.entries.getFirst();
                if (now - entry.getTime() > 2000) {
                    // need clean
                    System.out.println("[" + Thread.currentThread().getId() + "]" + "request " + entry.getId() + "is out.");
                    this.entries.removeFirst();
                } else {
                    break;
                }
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }
}
