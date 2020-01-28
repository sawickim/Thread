package pl.connectis.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.time.StopWatch;

public class ExecutorExample {

	public static void main(String[] args) throws Exception  {
		ExecutorExample example = new ExecutorExample();
		example.executor();
	}

	private void executor() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		System.out.println(Thread.currentThread().getName() + ": starting jobs");


		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Future> jobs =  new ArrayList<>();
		AtomicInteger atomicInteger = new AtomicInteger();
		for (int i = 0; i < 1000; i++) {
			Future job = executorService.submit(() -> {
				int counter = atomicInteger.incrementAndGet();
				System.out.println(Thread.currentThread().getName() + ": counter = " + counter);
			});
			jobs.add(job);
		}

		for (Future job : jobs) {
			job.get();
		}

		executorService.shutdown();
		System.out.println(stopWatch);
	}
}
