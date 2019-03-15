package hu.auditorium;

import java.util.Scanner;

import hu.auditorium.controller.Auditorium;
import hu.auditorium.domain.service.Console;
import hu.auditorium.domain.service.DataReader;
import hu.auditorium.domain.service.DataWriter;

public class App {

	private final Auditorium auditorium;
	private final Console console;
	private final DataWriter writer;
	
	public App() {
		console = new Console(new Scanner(System.in));
		DataReader data = new DataReader();
		auditorium = new Auditorium(data.getData("foglaltsag.txt", "kategoria.txt"));
		writer = new DataWriter("szabad.txt");
	}
	
	public static void main(String[] args) {
		new App().run();
	}

	private void run() {
		System.out.println("2. feladat:");
		int row = console.readInt("- adja meg a sor számát: ");
		int column = console.readInt("- adja meg a szék számát: ");
		System.out.println(auditorium.getSeatStaus(row, column));
		System.out.println("3. feladat: " + auditorium.getSoldSeatRange());
		System.out.println("4. feladat: " + auditorium.getMostPopularCategory());
		System.out.println("5. feladat: " + auditorium.getCurrentIncome());
		System.out.println("6. feladat: " + auditorium.getUniqueSeatCount());
		writer.printAll(auditorium.getAuditoriumState());
	}

}
