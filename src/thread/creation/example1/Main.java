package thread.creation.example1;

public class Main {

    public static void main(String args[]) {

        Thread thread = new MyWorkerThread();

        thread.start();
    }

    private static class MyWorkerThread extends Thread {

        @Override
        public void run() {

            System.out.println("Hello from " + this.getName());
        }
    }
}

/* 

public class Main {
    
    public static void main(String args[]) {

        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {

                System.out.println("Hello from " + Thread.currentThread().getName());
                System.out.println("Thread priority is " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("my-worker-thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("Hello from " + Thread.currentThread().getName() + " before starting a new thread.");
        thread.start();
        System.out.println("Hello from " + Thread.currentThread().getName() + " after starting a new thread.");
    }
}

*/