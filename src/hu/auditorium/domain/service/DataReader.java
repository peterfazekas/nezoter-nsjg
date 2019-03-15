package hu.auditorium.domain.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hu.auditorium.domain.model.Category;
import hu.auditorium.domain.model.Seat;

public class DataReader {

	public List<Seat> getData(String occupitionFileName, String CategoryFileName) {
		return parse(fileRead(occupitionFileName), fileRead(CategoryFileName));
	}
	
	private List<Seat> parse(List<String> occupations, List<String> categories) {
		List<Seat> seats = new ArrayList<>();
		for (int row = 0; row < occupations.size(); row++) {
			String occupation = occupations.get(row);
			String category = categories.get(row);
			for (int column = 0; column < occupation.length(); column++) {
				seats.add(createSeat(row + 1, column + 1, occupation.charAt(column), category.charAt(column)));
			}
		}
		return seats;
	}
	
	private Seat createSeat(int row, int column, char occupation, char category) {
		boolean isOccupied = occupation == 'x';
		Category cat = Category.getCategory(Character.getNumericValue(category));
		return new Seat(row, column, isOccupied, cat);
	}
	
	private List<String> fileRead(String fileName) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			lines = br.lines().collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
