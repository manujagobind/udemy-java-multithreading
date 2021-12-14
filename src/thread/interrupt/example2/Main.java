package thread.interrupt.example2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    public static void main (String args[]) throws InterruptedException {

        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 4656L, 23L, 2435L, 5566L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (Thread thread : threads) {
            thread.setDaemon(true); // Allows the application to shut down even if any worker thread does not complete
            thread.start();
        }

        // Wait for all worker threads to finish, bail out if it takes more than 2s
        for (Thread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < inputNumbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);

            if (factorialThread.isFinished()) {
                System.out.println("Factorial for " + inputNumbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + inputNumbers.get(i) + " is still in progress.");
            }
        }
    }

    public static class FactorialThread extends Thread {

        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        @Override
        public void run() {
            this.result = this.factorial();
            this.isFinished = true;
        }

        public BigInteger factorial() {

            BigInteger tempResult = BigInteger.ONE;

            for (long i = this.inputNumber ; i > 0 ; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return this.result;
        }
    }
}
