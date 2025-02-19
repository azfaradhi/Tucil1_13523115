package puzzlepieces;

public class Piece {
    private boolean[][] piece;
    private int row;
    private int col;
    private char letter;
    private String color;

    public Piece(boolean[][] piece, int row, int col, char letter, String color) {
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.letter = letter;
        this.color = color;
    }

    public int getPieceRow(){
        return this.row;
    }
    
    public int getPieceCol(){
        return this.col;
    }

    public char getLetter(){
        return this.letter;
    }

    public boolean[][] getPieceMatrix(){
        return this.piece;
    }

    public boolean isAnyPiece(int row, int col){
        return piece[row][col];
    }

    public String getColor(){
        return this.color;
    }

    public void rotate90AntiClockwise(){
        int n = this.row;
        int m = this.col;
        boolean[][] rotated = new boolean[m][n];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
            rotated[m-1-j][i] = piece[i][j];
            }
        }
        this.piece = rotated;
        int temp = this.row;
        this.row = this.col;
        this.col = temp;
    }

    public void mirrorPiece(){
        int n = this.row;
        int m = this.col;
        boolean[][] mirrored = new boolean[n][m];

        for (int i = 0; i< n; i++){
            for (int j = 0; j< m; j++){
                mirrored[i][m-1-j] = piece[i][j];
            }
        }
        this.piece = mirrored;
    }

    public void printPiece(){
        System.out.println("Piece " + this.letter + " (" + this.row + "x" + this.col + "):");
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                System.out.print(this.piece[i][j] ? this.letter : " ");
            }
            System.out.println();
        }
    }

}

