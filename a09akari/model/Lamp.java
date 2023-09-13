package com.comp301.a09akari.model;

public class Lamp {
    private final int lampRow;
    private final int lampColumn;
    public Lamp(int row, int column){
        this.lampRow = row;
        this.lampColumn = column;
    }

    public int getLampRow() {
        return lampRow;
    }

    public int getLampColumn() {
        return lampColumn;
    }


    public boolean equals(Lamp lamp){
        return (lamp.getLampColumn() == this.lampColumn && lamp.getLampRow() == this.lampRow);
    }
}
