import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FixedRaceConditionWithSemaphore {
    private String name = "";
    private boolean isAlive = true;
    public ArrayList<String> list = new ArrayList<String>();

    private Semaphore semaphore = new Semaphore(1);

    public void startProcesses() {
        list.add("Маша");
        list.add("if обманут");
        list.add("if обманут");
        list.add("if обманут");
        list.add("if обманут");
        list.add("if обманут");

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isAlive) {
                    if (semaphore.tryAcquire()) {
                        if (name == "Маша") {
                            System.out.println(name);
                        }
                        semaphore.release();
                    }
                }
                System.out.println("thread1 end");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;

                while (isAlive) {
                    if (semaphore.tryAcquire()) {
                        name = list.get(index % 6);
                        index++;
                        if (index == Integer.MAX_VALUE) index = 0;
                        semaphore.release();
                    }
                }
                System.out.println("thread2 end");
            }
        });

        thread1.start();
        thread2.start();
    }

    public void killProcesses() {
        isAlive = false;
    }
}
