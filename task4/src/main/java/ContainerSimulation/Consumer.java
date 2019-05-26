package ContainerSimulation;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    private Container container;
    private AtomicInteger count;
    private AtomicInteger success;
    private AtomicInteger fail;

    public Consumer(Container container, AtomicInteger count, AtomicInteger success, AtomicInteger fail) {
        this.container = container;
        this.count = count;
        this.success = success;
        this.fail = fail;
    }

    @Override
    public void run() {
        try {
            System.out.println("[" + Thread.currentThread().getId() + "]" + "started consumer thread");
//            Thread.sleep(500);

            for (int i = 0; i < 10; i++) {
                this.count.incrementAndGet();
                Entry entry;
                if ((entry = container.getEntry()) != null) {
                    this.success.incrementAndGet();
                    System.out.println("[" + Thread.currentThread().getId() + "]" + "got entry " + entry.getId());
                } else {
                    this.fail.incrementAndGet();
                    System.out.println("[" + Thread.currentThread().getId() + "]" + "failed to get entry");
                }
                Thread.sleep(40);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("[" + Thread.currentThread().getId() + "]" + "consumer thread quited");
        }
    }

}
