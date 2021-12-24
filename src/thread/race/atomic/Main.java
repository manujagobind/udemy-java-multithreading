package thread.race.atomic;

import java.util.Random;

public class Main {

    public static void main (String args[]) {

        Metrics metrics = new Metrics();

        BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);
        BusinessLogic businessLogicThread2 = new BusinessLogic(metrics);
        
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogicThread1.start();
        businessLogicThread2.start();
        metricsPrinter.start();
    }

    public static class MetricsPrinter extends Thread {

        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {

            while (true) {
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                // As getAverage is not synchronized, MetricsPrinter will run in parallel to BusinessLogic
                double currentAverage = metrics.getAverage();

                System.out.println("The current aveerage is: " + currentAverage);
            }
        }
    }

    public static class BusinessLogic extends Thread {

        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {

            while (true) {

                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                }

                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        
        private long count = 0;

        // volatile guarantees atomic read and write
        private volatile double average = 0.0;

        public synchronized void addSample(long sample) {
            double currentSum = this.average * this.count;
            this.count++;
            this.average = (currentSum + sample) / this.count;
        }

        public double getAverage() {
            return this.average;
        }
    }
}
