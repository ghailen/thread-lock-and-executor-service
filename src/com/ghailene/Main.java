package com.ghailene;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static final String EOF = "EOF";

    public static void main(String[] args) {

        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();

        /** exEuctor like new thread(runnbale).start **/ /** and set the thread pool **/
        ExecutorService executorService = Executors.newFixedThreadPool(3);


        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_BLACK, bufferLock);
        MyConsumer myConsumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer myConsumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);
        /** without executor **/
        //   new Thread(producer).start();
        // new Thread(myConsumer1).start();
        //new Thread(myConsumer2).start();

        /** with executor **/
        executorService.execute(producer);
        executorService.execute(myConsumer1);
        executorService.execute(myConsumer2);

/** without lambad expression **/
      /*  Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_GREEN+ "I'om being printed for the cammable class");
                return "This is the callable result";
            }
        });*/
/** with lambad expression **/
        Future<String> future = executorService.submit(() -> {
            System.out.println(ThreadColor.ANSI_GREEN + "I'om being printed for the cammable class");
            return "This is the callable result";
        });
        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            System.out.println("Something went wrong");
        } catch (InterruptedException e) {
            System.out.println("Thread running the task was interrupted");
        }

        /** call shutodwn to shutdown the service exectorr**/
        executorService.shutdown();
    }
}
