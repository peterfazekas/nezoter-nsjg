package hu.auditorium.domain.service;

import java.util.Scanner;

public class Console {

	private final Scanner scanner;

	public Console(Scanner scanner) {
		this.scanner = scanner;
	}

	public int readInt(String text) {
		System.out.print(text);
		return scanner.nextInt();
	}
	
}
