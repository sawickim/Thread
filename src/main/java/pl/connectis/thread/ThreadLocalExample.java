package pl.connectis.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadLocalExample {

	public static void main(String[] args) {
		ThreadLocalExample example = new ThreadLocalExample();
		example.threadLocal();
	}

	private void threadLocal() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		java.lang.ThreadLocal<String> myThreadLocal = new java.lang.ThreadLocal<>();

		List<Future> jobs = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			int myI = i;
			jobs.add(executorService.submit(() -> {
				String threadLocalValue = myThreadLocal.get();
				System.out.println(Thread.currentThread().getName() + ": i = " + myI);
				System.out.println(Thread.currentThread().getName() + ": ThreadLocal value for my thread was: " + threadLocalValue);
				if (threadLocalValue == null) {
					String newValue = String.valueOf(new Random().nextInt(100));
					System.out.println(Thread.currentThread().getName() + ": Setting ThreadLocal value for my thread to: " + newValue);
					myThreadLocal.set(newValue);
				}

//				myThreadLocal.remove();
			}));
		}

		for (Future job : jobs) {
			try {
				job.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		}

		executorService.shutdown();
	}
}
