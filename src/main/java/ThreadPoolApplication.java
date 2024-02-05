import threadpool.ThreadPool;

public class ThreadPoolApplication {
    public static void main(String[] args) throws Exception {
        int nThreads = 3;
        ThreadPool threadPool = new ThreadPool(nThreads);

        for (int i=0; i<10; i++) {
            int taskNo = i;
            threadPool.execute(() -> {
                // task to put onto task queue
                String message = Thread.currentThread().getName() + ": Task " + taskNo;
                System.out.println(message);
            });
        }

        threadPool.watUntilAllTasksFinished();
        threadPool.stop();
    }
}
