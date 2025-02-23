package placingpuzzle;
import board.Board;
import generateimage.GenerateImage;
import java.util.*;
import puzzlepieces.Piece;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PlacingPuzzle {
    private final Board board;
    private final List<Piece> pieces;

    public PlacingPuzzle(Board board, List<Piece> pieces) {
        this.board = board;
        this.pieces = pieces;
    }

    public boolean isValidPlacement(Board board, Piece piece, int idxBoardRow, int idxBoardCol){
        for (int i = 0; i < piece.getPieceRow(); i++){
            for (int j = 0; j < piece.getPieceCol(); j++){
                if (piece.isAnyPiece(i,j)){

                    if ((idxBoardRow+i >= board.getBoardRow()) || (idxBoardCol+j >= board.getBoardCol())){
                        return false;
                    }

                    if ((!board.isEmptyAt(idxBoardRow+i, idxBoardCol+j)) && (piece.isAnyPiece(i,j))){
                        return false;

                    }
                    if (board.getCharAt(idxBoardRow+i, idxBoardCol+j) == '#' && !(piece.isAnyPiece(i,j))){
                        return false;
                    }
                }
            }
        }
        return true;

    }

    public void placingPieceToBoard(int idxBoardRow, int idxBoardCol, Piece piece){
        char[][] tempBoard = this.board.getBoard();
        String[][] tempColor = this.board.getBoardColor();
        for (int i = 0; i < piece.getPieceRow(); i++){
            for (int j = 0; j < piece.getPieceCol(); j++){
                if (piece.isAnyPiece(i,j)){
                    tempBoard[idxBoardRow+i][idxBoardCol+j] = piece.getLetter();
                    tempColor[idxBoardRow+i][idxBoardCol+j] = piece.getColor();
                }
            }
        }
        this.board.setBoard(tempBoard);
        this.board.setBoardColor(tempColor);

    }

    public void removingPieceFromBoard(int idxBoardRow, int idxBoardCol, Piece piece){
        char[][] tempBoard = this.board.getBoard();

        String[][] tempColor = this.board.getBoardColor();
        for (int i = 0; i < piece.getPieceRow(); i++){
            for (int j = 0; j < piece.getPieceCol(); j++){
                if (piece.isAnyPiece(i,j)){
                    if (tempBoard[idxBoardRow+i][idxBoardCol+j] == '#'){
                        tempBoard[idxBoardRow+i][idxBoardCol+j] = '#';
                    }
                    else{
                        tempBoard[idxBoardRow+i][idxBoardCol+j] = '.';
                    }
                    tempColor[idxBoardRow+i][idxBoardCol+j] = " ";
                }
            }
        }
        this.board.setBoard(tempBoard);
        this.board.setBoardColor(tempColor);
    }


    public boolean mainSolvingPuzzle(int pieceIdx, int counter){
        if (pieceIdx >= this.pieces.size()){
            if (pieceIdx > this.pieces.size()){
                return false;
            }
            for (int p = 0; p < this.board.getBoardRow(); p++){
                for (int q = 0; q < this.board.getBoardCol(); q++){
                    if (this.board.isEmptyAt(p,q)){
                        return false;
                    }
                }
            }
            String RESET = "\u001B[0m";
            String GREEN = "\u001B[32m";
            String RED = "\u001B[31m";

            System.out.println(GREEN + "Problem Solved!" + RESET);
            System.out.println();
            this.board.printBoard();
            System.out.println();
            System.out.println(GREEN + "Attempts: " + RESET + RED + counter + RESET);
            
            GenerateImage generateImage = new GenerateImage();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String stringTime = now.format(formatter);
            String saveTo = "test/output/result" + stringTime + ".png";
            generateImage.GenerateImage(this.board, saveTo);
            return true;
        }
        for (int i = 0; i < board.getBoardRow(); i++){
            for (int j = 0; j < board.getBoardCol(); j++){
                for (int k = 0; k < 4; k++){
                    for (int l = 0; l < 2; l++){
                        counter++;
                        if (isValidPlacement(board, pieces.get(pieceIdx),i,j)){
                            placingPieceToBoard(i,j,pieces.get(pieceIdx));


                            if (mainSolvingPuzzle(pieceIdx+1, counter)){
                                return true;
                            }

                            removingPieceFromBoard(i,j,pieces.get(pieceIdx));
                        }
                        pieces.get(pieceIdx).mirrorPiece();
                    }
                    pieces.get(pieceIdx).rotate90AntiClockwise();
                }
            }
        }
        return false;
    }
}