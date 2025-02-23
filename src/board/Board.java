package board;

public class Board {
    private final int row;
    private final int col;
    public char[][] board;
    public String[][] color;

    public Board(int row, int col, int type){
        this.row = row;
        this.col = col;
        this.board = new char[row][col];
        this.color = new String[row][col];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                this.board[i][j] = '.';
                this.color[i][j] = ".";
            }
        }
    }

    public int getBoardRow(){
        return this.row;
    }

    public int getBoardCol(){
        return this.col;
    }

    public char getCharAt(int row, int col){
        return this.board[row][col];
    }

    public boolean isEmptyAt(int row, int col){
        return this.board[row][col] == '.';
    }

    public char[][] getBoard(){
        char[][] result = new char[this.row][this.col];
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                result[i][j] = this.board[i][j];
            }
        }
        return result;
        }
        
    public void setBoard(char[][] newBoard) {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
            this.board[i][j] = newBoard[i][j];
            }
        }
    }
    
    public String[][] getBoardColor(){
        String[][] result = new String[this.row][this.col];
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                result[i][j] = this.color[i][j];
            }
        }
        return result;
    }

    public void setBoardColor(String[][] newColor){
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                this.color[i][j] = newColor[i][j];
            }
        }
    }
    public void printBoard(){
        String RESET = "\u001B[0m";
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                if (this.board[i][j] == '.' || this.board[i][j] == '#'){
                    System.out.print(" ");
                }
                else{
                    System.out.print(this.color[i][j] + this.board[i][j] + RESET);
                }
            }
            System.out.println();
        }
    }
}