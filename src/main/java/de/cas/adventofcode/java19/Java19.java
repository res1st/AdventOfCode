package de.cas.adventofcode.java19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Java19 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Map<String, Integer> map = HashMap.newHashMap(120);
		System.out.println(map);

		printJava17(new Position(2, 3));
		printIf(new Position(2, 3));
		printSwitch(new Position(2, 3));
		printPath(new Path(new Position(2, 3), new Position(7, 5)));
		
		doVirtualThread();
	}

	/**
	 * still preview :(
	 */
	private static void doVirtualThread() throws InterruptedException, ExecutionException {
//		ExecutorService executor = Executors.newFixedThreadPool(100);
		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

		List<Task> tasks = new ArrayList<>();
		for (int i = 0; i < 1_000; i++) {
		  tasks.add(new Task(i));
		}

		long time = System.currentTimeMillis();

		List<Future<Integer>> futures = executor.invokeAll(tasks);

		long sum = 0;
		for (Future<Integer> future : futures) {
		  sum += future.get();
		}

		time = System.currentTimeMillis() - time;

		System.out.println("sum = " + sum + "; time = " + time + " ms");

		executor.shutdown();
		
	}

	private static void printJava17(Object object) {
		switch (object) {
		case Position position ->
			System.out.println("object is a position, x = " + position.x() + ", y = " + position.y());
			default -> System.out.println("def");
		}
	}

	private static void printIf(Object object) {
		if (object instanceof Position(int x,int y)) {
			System.out.println("object is a position, x = " + x + ", y = " + y);
		}
	}

	private static void printSwitch(Object object) {
		switch (object) {
		case Position(int x,int y) -> System.out.println("object is a position, x = " + x + ", y = " + y);
		default -> System.out.println("def");
		}
	}
	
	private static void printPath(Object object) {
		  switch (object) {
		    case Path(Position(int x1, int y1), Position(int x2, int y2))
		        -> System.out.println("object is a path, x1 = " + x1 + ", y1 = " + y1 
		                                            + ", x2 = " + x2 + ", y2 = " + y2);
		        default -> System.out.println("def");
		  }
		}

	public record Position(int x, int y) {
	}
	
	public record Path(Position from, Position to) {}
}
