package pl.connectis.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorExample {

	public static void main(String[] args) {
		ThreadPoolExecutorExample example = new ThreadPoolExecutorExample();
		example.threadPoolExecutors();
	}

	private void threadPoolExecutors() {
		java.util.concurrent.ThreadPoolExecutor threadPoolExecutor = new java.util.concurrent.ThreadPoolExecutor(
				10,
				10,
				1000,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>());

		List<CompletableFuture<Object>> jobs = new ArrayList<>();
		AtomicInteger atomicInteger = new AtomicInteger();
		for (int i = 0; i < 1000; i++) {
			jobs.add(CompletableFuture.supplyAsync(() -> {
				int counter = atomicInteger.incrementAndGet();
				System.out.println(Thread.currentThread().getName() + ": counter = " + counter);
				return null;
			}, threadPoolExecutor));
		}

		for (CompletableFuture<Object> job : jobs) {
			try {
				job.get();
			} catch (ExecutionException | InterruptedException e) {
				System.out.println(e);
			}
		}

		System.out.println("All jobs finished! Shutting down ThreadPoolExecutor!");
		threadPoolExecutor.shutdown();
	}
}
