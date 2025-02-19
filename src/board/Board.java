package board;

public class Board {
    private int row;
    private int col;
    private char[][] board;

    public Board(int row, int col){
        this.row = row;
        this.col = col;
        this.board = new char[row][col];
    }

    
}