package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final List<PoolThreadRunnable> runnables;
    private boolean isStopped;

    public ThreadPool(int nThreads) {
        this.taskQueue = new LinkedBlockingQueue<>(); // prefer LinkedBlockingQueue over ArrayBlockingQueue which would require us to initialize it with a size
        this.runnables = new ArrayList<>();
        this.isStopped = false;

        // create each pool thread and start them
        for (int i=0; i<nThreads; i++) {
            PoolThreadRunnable poolThreadRunnable = new PoolThreadRunnable(this.taskQueue);
            this.runnables.add(poolThreadRunnable);
            new Thread(poolThreadRunnable).start();
        }
    }

    // queue task to BlockingQueue to be executed
    public synchronized void execute(Runnable task) throws Exception {
        if (this.isStopped) throw new IllegalStateException("ThreadPool is stopped!");
        this.taskQueue.offer(task);
    }

    public synchronized void stop() {
        // stop the thread pool
        this.isStopped = true;
        // stop all thread runnables
        for (PoolThreadRunnable runnable: runnables) {
            runnable.stop();
        }
    }

    /*
    Keep looping to put the main thread to sleep to wait for all the pool threads to finish.
     */
    public synchronized void watUntilAllTasksFinished() {
        while (this.taskQueue.size() > 0) {
            try {
                // main thread
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
