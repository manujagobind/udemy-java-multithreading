package thread.interrupt.example1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main (String args[]) {

        List<Thread> threads = new ArrayList<>();

        threads.add(new Thread(new BlockingTask()));
        threads.add(new Thread(new LongComputationTask(
            new BigInteger("10000"), new BigInteger("100000000")))
        );

        for (Thread thread : threads) {
            // thread.setDaemon(true);
            thread.start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread thread : threads) {
            // Send an interrupt to an already running thread
            thread.interrupt();
        }
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " - Running");
            try {
                Thread.sleep(5000000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
            }
        }
    }

    private static class LongComputationTask implements Runnable {

        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " - Running");
            System.out.println(this.base + "^" + this.power + " = " + this.pow());
        }

        private BigInteger pow() {

            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ONE; i.compareTo(this.power) != 0; i = i.add(BigInteger.ONE)) {

                if (Thread.currentThread().isInterrupted()) {
                    // When there's no InterruptedException to handle, we can check for isInterrupted()
                    System.out.println(Thread.currentThread().getName() + " - Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }

                result = result.multiply(this.base);
            }

            return result;
        }
    }
}