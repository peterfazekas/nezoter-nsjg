package hu.auditorium.domain.model;

public class Seat {

	private final int row;
	private final int column;
	private final boolean occupied;
	private final Category category;

	public Seat(int row, int column, boolean occupied, Category category) {
		this.row = row;
		this.column = column;
		this.occupied = occupied;
		this.category = category;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public int getCategoryId() {
		return category.getId();
	}
	
	public int getPrice() {
		return category.getPrice();
	}

	@Override
	public String toString() {
		return isOccupied() ? "x" : String.valueOf(getCategoryId());
	}
	
	
}
