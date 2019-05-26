package multithread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Container container = new Container(); //Container

        // variables for evaluating
        AtomicInteger success = new AtomicInteger();
        AtomicInteger fail = new AtomicInteger();
        AtomicInteger count = new AtomicInteger();

        //Producer and Consumer objects
        Producer producer0 = new Producer(container);
        Producer producer1 = new Producer(container);
        Producer producer2 = new Producer(container);
        Producer producer3 = new Producer(container);
        Consumer consumer0 = new Consumer(container, count, success, fail);
        Consumer consumer1 = new Consumer(container, count, success, fail);

        // Open threads and execute add/get process.
        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(producer0);
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);

        service.execute(consumer0);
        service.execute(consumer1);

        //wait and print the counting numbers
        Thread.sleep(10 * 1000);
        service.shutdownNow();
        System.out.println("count: " + count + " success: " + success + " fail :" + fail);
    }
}
