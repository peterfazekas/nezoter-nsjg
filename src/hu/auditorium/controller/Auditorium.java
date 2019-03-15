package hu.auditorium.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hu.auditorium.domain.model.Seat;

public class Auditorium {

	private final List<Seat> seats;

	public Auditorium(List<Seat> seats) {
		this.seats = seats;
	}

	public String getSeatStaus(int row, int column) {
		return String.format("A %d. sor %d. széke %s.", row, column,
				getSeat(row, column).isOccupied() ? "már foglalt" : "még üres");
	}

	private Seat getSeat(int row, int column) {
		return seats.stream().filter(i -> i.getRow() == row && i.getColumn() == column).findAny().get();
	}

	public String getSoldSeatRange() {
		long soldSeats = getSoldSeatCount();
		long totalSeats = seats.size();
		long percent = soldSeats * 100 / totalSeats;
		return String.format("Az előadása eddig %d jegyet adtak el, ez a nézőtér %d%%-a.", soldSeats, percent);
	}

	private long getSoldSeatCount() {
		return seats.stream().filter(Seat::isOccupied).count();
	}

	public String getMostPopularCategory() {
		return String.format("A legtöbb jegyet a(z) %d. árkategóriában értékesítették.", getMostPopularCategoryId());
	}

	private int getMostPopularCategoryId() {
		return createSeatCategoryMap().entrySet().stream().sorted((j, i) -> i.getValue().compareTo(j.getValue()))
				.findFirst().map(Entry::getKey).get();
	}

	private Map<Integer, Long> createSeatCategoryMap() {
		return seats.stream().filter(Seat::isOccupied)
				.collect(Collectors.groupingBy(Seat::getCategoryId, Collectors.counting()));
	}

	public String getCurrentIncome() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("hu", "HU"));
		return String.format("A színház pillanatnyi bevétele %s", nf.format(getIncome()));
	}

	private int getIncome() {
		return seats.stream().filter(Seat::isOccupied).mapToInt(Seat::getPrice).sum();
	}

	public String getUniqueSeatCount() {
		SeatService service = new SeatService(seats);
		return String.format("A színházban jelenleg %d db egyedülálló üres hely van.", service.getUniqueSeatCount());
	}

	public List<String> getAuditoriumState() {
		List<String> lines = new ArrayList<>();
		int offset = getMaxColumnValue();
		IntStream.range(0,  seats.size())
			.filter(i -> i % offset == 0)
			.forEach(i -> lines.add(getAuditorium().substring(i, i + offset)));
		return lines;
	}

	private int getMaxColumnValue() {
		return seats.stream().mapToInt(Seat::getColumn).max().getAsInt();
	}

	private String getAuditorium() {
		return seats.stream().map(Seat::toString).collect(Collectors.joining());
	}
}
