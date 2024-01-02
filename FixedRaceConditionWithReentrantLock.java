import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class FixedRaceConditionWithReentrantLock {
    private String name = "";
    private boolean isAlive = true;
    public ArrayList<String> list = new ArrayList<String>();

    private ReentrantLock lock = new ReentrantLock();

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
                    lock.lock();
                    if (name == "Маша") System.out.println(name);
                    lock.unlock();
                }
                System.out.println("thread1 end");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int index = 0;

                while (isAlive) {
                    lock.lock();
                    name = list.get(index % 6);
                    index++;
                    if (index == Integer.MAX_VALUE) index = 0;
                    lock.unlock();
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
