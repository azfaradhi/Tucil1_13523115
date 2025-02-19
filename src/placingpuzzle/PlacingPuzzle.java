package placingpuzzle;
import board.Board;
import puzzlepieces.Piece;
import java.io.*;
import java.util.*;

public class PlacingPuzzle {
    private Board board;
    private List<Piece> pieces;

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

            System.out.println("Solusi ditemukan!");
            this.board.printBoard();
            System.out.println("Jumlah percobaan: " + counter);
            return true;
        }
        for (int i = 0; i < this.board.getBoardRow(); i++){
            for (int j = 0; j < this.board.getBoardCol(); j++){
                for (int k = 0; k < 4; k++){
                    for (int l = 0; l < 2; l++){
                        counter++;
                        if (isValidPlacement(this.board, this.pieces.get(pieceIdx), i, j)){
                            placingPieceToBoard(i, j, this.pieces.get(pieceIdx));
                            // this.board.printBoard();

                            if (mainSolvingPuzzle(pieceIdx+1,counter)){
                                return true;
                            }

                            removingPieceFromBoard(i, j, pieces.get(pieceIdx));
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