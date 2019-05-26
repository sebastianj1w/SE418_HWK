//package multithread;
//
//import java.util.NoSuchElementException;
//import java.util.Random;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class Producer0 implements Runnable {
//
//    private AtomicInteger count;
//    private AtomicInteger success;
//    private AtomicInteger fail;
//    private LinkedBlockingDeque<Request> requests;
//
//    public Producer0(AtomicInteger count, LinkedBlockingDeque<Request> requests, AtomicInteger success, AtomicInteger fail) {
//        this.count = count;
//        this.requests = requests;
//        this.success = success;
//        this.fail = fail;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("[" + Thread.currentThread().getId() + "]" + "started producer thread");
//        try {
//            while (true) {
//                Random r = new Random();
//                // add the production count
//                Integer need = r.nextInt(4) + 1;// 1-4
//                Integer count = this.count.addAndGet(need);
//                System.out.println("[" + Thread.currentThread().getId() + "]" + "production now: "+ count + " by " + Thread.currentThread().getId());
//                // deal with request
//                System.out.println("[" + Thread.currentThread().getId() + "]" + "requests size: " + this.requests.size());
//                if(this.requests.size() > 5)
//                {
//                    System.out.println("[" + Thread.currentThread().getId() + "]" + "FILO");
//                    // need FILO
//                    try{
//                        Request request = this.requests.getLast();
//                        if(request.getNum() <= this.count.get())
//                        {
//                            System.out.println("[" + Thread.currentThread().getId() + "]" + "request "+ request.getNum() + " has " + this.count.get() );
//                            this.count.updateAndGet(x -> (x - request.getNum()));
//                            this.requests.remove(request);
//                            this.success.incrementAndGet();
//                        }
//                        else{
//                            System.out.println("[" + Thread.currentThread().getId() + "]" + "count last: " + this.count.get());
//                            break;
//                        }
//                    }
//                    catch (NoSuchElementException e)
//                    {
//                        System.out.println("[" + Thread.currentThread().getId() + "]" + "now there is no request.");
//                        break;
//                    }
//                }
//                else{
//                    // can FIFO
//                    while(true)
//                    {
//                        System.out.println("[" + Thread.currentThread().getId() + "]" + "FIFO");
//                        try{
//                            Request request = this.requests.getFirst();
//                            if(request.getNum() <= this.count.get())
//                            {
//                                System.out.println("[" + Thread.currentThread().getId() + "]" + "request "+ request.getNum() + " has " + this.count.get() );
//                                this.count.updateAndGet(x -> (x - request.getNum()));
//                                this.requests.remove(request);
//                                this.success.incrementAndGet();
//                            }
//                            else{
//                                System.out.println("[" + Thread.currentThread().getId() + "]" + "count last: " + this.count.get());
//                                break;
//                            }
//                        }
//                        catch (NoSuchElementException e)
//                        {
//                            System.out.println("[" + Thread.currentThread().getId() + "]" + "now there is no request.");
//                            break;
//                        }
//                    }
//                }
//                System.out.println("[" + Thread.currentThread().getId() + "]" + "clean old request");
//                // clean the old request
//                while(true)
//                {
//                    // sure you can choose use System.currentTimeMillis multitimes
//                    long now = System.currentTimeMillis();
//                    try{
//                        Request request = this.requests.getFirst();
//                        if(now - request.getTime() > 2000)
//                        {
//                            // need clean
//                            System.out.println("[" + Thread.currentThread().getId() + "]" + "request " + request.hashCode() + "is out.");
//                            this.requests.takeFirst();
//                            this.fail.incrementAndGet();
//                        }
//                        else{
//                            break;
//                        }
//                    }catch (NoSuchElementException ignored)
//                    {
//                        break;
//                    }
//                }
//                System.out.println("[" + Thread.currentThread().getId() + "]" + "Producer sleep 4000ms");
//                Thread.sleep(4000);
//            }
//        } catch (Exception e) {
//            //e.printStackTrace();
//            Thread.currentThread().interrupt();
//        } finally {
//            System.out.println("[" + Thread.currentThread().getId() + "]" + "producer thread quit safely");
//        }
//    }
//
//    public AtomicInteger getCount() {
//        return count;
//    }
//
//    public void setCount(AtomicInteger count) {
//        this.count = count;
//    }
//
//    public LinkedBlockingDeque<Request> getRequests() {
//        return requests;
//    }
//
//    public void setRequests(LinkedBlockingDeque<Request> requests) {
//        this.requests = requests;
//    }
//}
