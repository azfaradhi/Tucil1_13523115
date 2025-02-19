package puzzlepieces;

public class Piece {
    private boolean[][] piece;
    private int row;
    private int col;
    private char letter;

    public Piece(boolean[][] piece, int row, int col, char letter) {
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.letter = letter;
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

    public boolean[][] rotate90AntiClockwise(boolean[][] piece){
        int n = this.row;
        int m = this.col;
        boolean[][] rotated = new boolean[m][n];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                rotated[m-1-j][i] = piece[i][j];
            }
        }
        return rotated;
    }

    public boolean[][] mirrorPiece(boolean[][] piece){
        int n = this.row;
        int m = this.col;
        boolean[][] mirrored = new boolean[n][m];

        for (int i = 0; i< n; i++){
            for (int j = 0; j< m; j++){
                mirrored[i][m-1-j] = piece[i][j];
            }
        }
        return mirrored;
    }

    public void printPiece(){
        System.out.println("Piece " + this.letter + " (" + this.row + "x" + this.col + "):");
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                System.out.print(this.piece[i][j] ? "■ " : "□ ");
            }
            System.out.println();
        }
    }

}

