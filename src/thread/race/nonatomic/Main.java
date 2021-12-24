package thread.race.nonatomic;

public class Main {

    public static void main (String args[]) throws InterruptedException {

        InventoryCounter inventoryCounter = new InventoryCounter();

        Thread incrementingThread = new IncrementingThread(inventoryCounter);
        Thread decrementingThread = new DecrementingThread(inventoryCounter);

        
        // Parallel exection
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("The count of items is: " + inventoryCounter.getItems());
    }

    private static class IncrementingThread extends Thread {

        InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0 ; i < 1000 ; i++) {
                this.inventoryCounter.increment();
            }
        }
    }

    private static class DecrementingThread extends Thread {

        InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0 ; i < 1000 ; i++) {
                this.inventoryCounter.decremen();
            }
        }
    }

    private static class InventoryCounter {

        private int items = 0;

        /*
            public synchronized void method() {
                ...
            }

            is equivalent to:

            public void method() {
                synchronized(this) {
                    ...
                }
            }

            If a class has multiple synchronized blocks, only one of those
            methods can be accessed at a time by the same object.

            Another syntax for creating synchronized blocs:

            Object LOCK_OBJECT = new Object();

            public void method() {
                ...
                synchronized(LOCK_OBJECT) {
                    ...
                }
                ...
            }
        */

        public synchronized void increment() {
            this.items++;
        }

        public synchronized void decremen() {
            this.items--;
        }

        public int getItems() {
            return items;
        }
    }
    
}
