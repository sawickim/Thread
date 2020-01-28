package pl.connectis.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LockingExample {

	public static void main(String[] args) {
		LockingExample example = new LockingExample();
		example.locking();
	}

	private void locking() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Future> jobs = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			System.out.println("Submitting job no. " + i);
			jobs.add(executorService.submit(this::doIntensiveTask4));
		}

		getAndShutDown(executorService, jobs);
	}

	private synchronized void doIntensiveTask() {
		somethingIntensive();
	}

	private void doIntensiveTask2() {
		synchronized (this) {
			somethingIntensive();
		}
	}

	private void doIntensiveTask3() {
		Object newObject = new Object();
		synchronized (newObject) {
			somethingIntensive();
		}
	}

	private void doIntensiveTask4() {
		String myLock = "myLock";
		synchronized (myLock) {
			somethingIntensive();
		}
	}

	private static synchronized void doIntensiveTask5() {
		somethingIntensive();
	}

	private static void doIntensiveTask6() {
		synchronized (LockingExample.class) {
			somethingIntensive();
		}
	}

	private static void somethingIntensive() {
		try {
			System.out.println(Thread.currentThread().getName() + ": Running intensive task...");
			Thread.sleep(1500);
			System.out.println(Thread.currentThread().getName() + ": Running intensive task... Finished!");
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void getAndShutDown(ExecutorService executorService, List<Future> jobs) {
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
