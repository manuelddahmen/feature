package one.empty3.feature;

import java.util.ArrayList;
import java.util.List;

public class TimerKillThread extends Thread {
    public static List<TimerKillThread> instances
            = new ArrayList<>();
    public int MAX_THREADS = 10;
    private final Thread thread;
    private long time;

    public TimerKillThread(Thread t) {
        this.thread = t;
    }
    @Override
    public void run() {
        instances.add(this);
        this.time = System.nanoTime();

        while((System.nanoTime()-time)<4000000000l
                || instances.size()>=MAX_THREADS) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread.stop();
        instances.remove(this);
    }

}
