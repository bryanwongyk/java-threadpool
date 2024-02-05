package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*

Runnable is provided to pool threads which allows them to take tasks out of a given task pool queue.
 */
class PoolThreadRunnable implements Runnable {
    private Thread thread = null;
    private final BlockingQueue<Runnable> taskQueue;
    private boolean isStopped;

    public PoolThreadRunnable(BlockingQueue<Runnable> queue) {
        taskQueue = queue;
    }

    @Override
    public void run() {
        this.thread = Thread.currentThread();

        while (!this.isStopped) {
            try {
                // if this thread is idle and not stopped, dequeue the next task off the queue to run
                // this is a blocking call
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch (InterruptedException e) {
                // log and print exception, but keep thread pool alive
                e.printStackTrace();
            }
        }
    }

    // synchronize as this is expected to be called by a thread outside the thread pool
    public synchronized void stop() {
        this.isStopped = true;
        // break the pool thread out of the .take() call which throws InterruptedException
        this.thread.interrupt();
    }

    // synchronize as this will be called by another thread within the thread pool
    public synchronized boolean isStopped() {
        return this.isStopped;
    }
}
