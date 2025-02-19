import board.Board;
import puzzlepieces.Piece;
import readsave.ReadFromFile;
import readsave.SaveToFile;
import board.Board;
import placingpuzzle.PlacingPuzzle;
import java.util.*;
import interfacee.Gui;


public class main{

    public static void main(String[] args) {
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        System.out.println(GREEN + "Selamat datang! Silahkan masukkan nama file: " + RESET);

        Scanner myObj = new Scanner(System.in);
        String fileName = myObj.nextLine();
        ReadFromFile read = new ReadFromFile();
        List<String> lines = read.readFile(fileName);
        List<Integer> boardInfo = read.makeBoard(lines);
        boolean valid = true;

        Board board = new Board(boardInfo.get(0), boardInfo.get(1), boardInfo.get(2));
        int addForStartIndex = 2;
        if (boardInfo.get(2) == 1){
            char[][] newBoard = read.makeBoardCustom(lines);
            board.setBoard(newBoard);
            addForStartIndex += board.getBoardRow();
        }
        board.printBoard();

        List<Piece> pieces = read.makePieces(lines,addForStartIndex);

        for (Piece piece : pieces){
            System.out.println("Piece " + piece.getLetter() + " (" + piece.getPieceRow() + "x" + piece.getPieceCol() + "):");
            piece.printPiece();
        }

        if (pieces.size() != boardInfo.get(3)){
            System.out.println("Jumlah piece tidak sesuai dengan yang diberikan!");
            System.exit(0);
        }
        PlacingPuzzle main = new PlacingPuzzle(board, pieces);

        int pieceIdx = 0;
        long startTime = System.currentTimeMillis();
        // if (boardInfo.get(3) != pieces.size()){
        //     System.out.println("Jumlah piece tidak sesuai dengan yang diberikan!");
        //     System.exit(0);
        // }

        boolean result = main.mainSolvingPuzzle(0,0);
        if (!result){
            System.out.println("Solusi tidak ditemukan!");
        }

        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Waktu Eksekusi: " + duration + " milliseconds");

        System.out.println("Mau di save ke file? (Y/N)");
        String save = myObj.nextLine();
        if (save.equals("Y")){
            System.out.println("Masukkan nama file:");
            String filename = myObj.nextLine();
            SaveToFile.saveToFile(filename, board);
        }





    }
}