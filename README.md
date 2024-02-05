## What is a thread pool?
A thread pool represents a group of worker threads which execute tasks.
Each thread is reused to execute the next task when it becomes available.

Without a thread pool, we would be creating threads to execute tasks in an unbounded fashion. 
Having too many threads can incur great overhead.

## What is this project?
In most real use-cases, we can use Java's standard `ExecutorService` to create a thread pool.

This is just an exercise to implement a thread pool to understand how it works.

[Thread Pool Overview](./threadpool-overview.png)

### References:
- https://www.youtube.com/watch?v=ZcKt5FYd3bU&list=PLL8woMHwr36EDxjUoCzboZjedsnhLP1j4&index=12
- https://www.javacodegeeks.com/2016/12/implement-thread-pool-java.html