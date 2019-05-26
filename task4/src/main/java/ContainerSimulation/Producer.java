package ContainerSimulation;

import java.util.Random;

public class Producer implements Runnable {
    private Container container;

    public Producer(Container requests) {
        this.container = requests;
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getId() + "]" + "started producer thread");
        try {
            for (int i = 0; i < 5; i++) {
                Random r = new Random();
                int id = r.nextInt(100) + 1;// 1-100
                System.out.println("[" + Thread.currentThread().getId() + "]" + "try to add entry " + id);
                if (!container.addEntry(id)) {
                    System.out.println("[" + Thread.currentThread().getId() + "]" + "failed to add entry");
                    break;
                }
                Thread.sleep(40);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("[" + Thread.currentThread().getId() + "]" + "producer thread quited");
        }

    }
}
