package pl.connectis.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Streams {

	public static void main(String[] args) {
		List<String> strings = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			strings.add("");
		}

		strings.stream()
				.map((xdef) -> xdef + "abc")
				.collect(Collectors.toList());
	}

	private static String addABC(String myString) {
		return myString + "abc";
	}
}
