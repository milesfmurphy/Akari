package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle{
    private int[][] board;
    public PuzzleImpl(int[][] board) {
        this.board = board;
        // Your constructor code here
    }

    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
    }

    @Override
    public CellType getCellType(int r, int c) {
        if(r > this.getHeight() || c > this.getWidth() || r < 0 || c < 0){
            throw new IndexOutOfBoundsException();
        }
        int cellLocation = board[r][c];
        if(cellLocation < 5){
            return CellType.CLUE;
        } else if(cellLocation == 5){
            return CellType.WALL;
        } else return CellType.CORRIDOR;
    }

    @Override
    public int getClue(int r, int c) {
        if(r > this.getHeight() || c > this.getWidth() || r < 0 || c < 0){
            throw new IndexOutOfBoundsException();
        }
        if(board[r][c] > 4){
            throw new IllegalArgumentException("Cell is not a clue cell.");
        } else return board[r][c];
    }
}
