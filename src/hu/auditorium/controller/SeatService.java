package hu.auditorium.controller;

import java.util.List;
import java.util.Optional;

import hu.auditorium.domain.model.Seat;

public class SeatService {

	private final List<Seat> seats;

	public SeatService(List<Seat> seats) {
		this.seats = seats;
	}
	
	public long getUniqueSeatCount() {
		return seats.stream().filter(this::isUniqueFreeSeat).count();
	}
	
	private boolean isUniqueFreeSeat(Seat seat) {
		return isFree(seat) && hasLeftNeighbour(seat) && hasRightNeighbour(seat);
	}
	
	private boolean isFree(Seat seat) {
		return !seat.isOccupied();
	}
	
	private boolean hasLeftNeighbour(Seat seat) {
		int row = seat.getRow();
		int column = seat.getColumn() - 1;
		return hasNeighbour(row, column);
	}
	
	private boolean hasRightNeighbour(Seat seat) {
		int row = seat.getRow();
		int column = seat.getColumn() + 1;
		return hasNeighbour(row, column);
	}

	private boolean hasNeighbour(int row, int column) {
		return getSeat(row, column).map(Seat::isOccupied).orElse(true);
	}
	
	private Optional<Seat> getSeat(int row, int column) {
		return seats.stream().filter(i -> i.getRow() == row && i.getColumn() == column).findAny();
	}
	
}
