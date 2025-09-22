package com.snakeladder.model;

public class Position {
    private final int row;
    private final int column;
    private final int cellNumber;
    
    public Position(int row, int column, int cellNumber) {
        this.row = row;
        this.column = column;
        this.cellNumber = cellNumber;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getCellNumber() {
        return cellNumber;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return cellNumber == position.cellNumber;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(cellNumber);
    }
    
    @Override
    public String toString() {
        return String.format("Position{row=%d, col=%d, cell=%d}", row, column, cellNumber);
    }
}

