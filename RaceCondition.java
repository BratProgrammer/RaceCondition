import java.util.ArrayList;
import java.util.Objects;

public class RaceCondition {
    private String name = "";
    private boolean isAlive = true;
    public ArrayList<String> list = new ArrayList<String>();

    private Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            while (isAlive) {
                if (name == "Маша") {
                    System.out.println(name);
                }
            }
            System.out.println("thread1 end");
        }
    });

    private Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            int index = 0;

            while (isAlive) {
                name = list.get(index % 6);
                index++;
                if (index == Integer.MAX_VALUE) index = 0;
            }
            System.out.println("thread2");
        }
    });
    public void startRace() {
        list.add("Маша");
        list.add("if обманут");
        list.add("if обманут");
        list.add("if обманут");
        list.add("if обманут");
        list.add("if обманут");

        thread1.start();
        thread2.start();
    }

    public void killProcesses() {
        isAlive = false;
    }
}
