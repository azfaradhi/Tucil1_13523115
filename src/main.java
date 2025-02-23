import board.Board;
import puzzlepieces.Piece;
import readsave.ReadFromFile;
import readsave.SaveToFile;
import board.Board;
import placingpuzzle.PlacingPuzzle;
import java.util.*;

public class main{

    public static void main(String[] args) {
    
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String BLUE = "\u001B[34m";
        String RED = "\u001B[31m";
        System.out.println(GREEN + 
"""
 ____                _        ____        _                
|  _ \\ _   _ _______| | ___  / ___|  ___ | |_   _____ _ __ 
| |_) | | | |_  /_  / |/ _ \\ \\___ \\ / _ \\| \\ \\ / / _ \\ '__|
|  __/| |_| |/ / / /| |  __/  ___) | (_) | |\\ V /  __/ |   
|_|    \\__,_/___/___|_|\\___| |____/ \\___/|_| \\_/ \\___|_|   
""" + RESET);
        System.out.println(BLUE + "Made by: Azfa Radhiyya Hakim" + RESET);
        System.out.println();

        while (true){

            System.out.print(GREEN + "Enter file name: " + RESET);

            Scanner myObj = new Scanner(System.in);
            String fileName = myObj.nextLine();
            ReadFromFile read = new ReadFromFile();
            List<String> lines = read.readFile(fileName);

            while (lines == null){
                System.out.println(RED + "File not found!"+ RESET);
                System.out.print(GREEN + "Try again? (Y/N): " + RESET);
                String tryAgain = myObj.nextLine();
                if (tryAgain.equals("Y") || tryAgain.equals("y")){
                    System.out.print(GREEN + "Enter file name: " + RESET);
                    fileName = myObj.nextLine();
                    lines = read.readFile(fileName);
                }
                else{
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
            }
            System.out.println();
            System.out.println(GREEN + "Your wish is my command!" + RESET);
            System.out.println();
            System.out.println(GREEN + "Loading..." + RESET);
            System.out.println();


            List<Integer> boardInfo = read.makeBoard(lines);
            boolean valid = true;

            Board board = new Board(boardInfo.get(0), boardInfo.get(1), boardInfo.get(2));
            int addForStartIndex = 2;
            if (boardInfo.get(2) == 1){
                char[][] newBoard = read.makeBoardCustom(lines);
                board.setBoard(newBoard);
                addForStartIndex += board.getBoardRow();
            }
            List<Piece> pieces = read.makePieces(lines,addForStartIndex);

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
            long endTime = System.currentTimeMillis();

            if (!result){
                System.out.println(RED + "No solution found!" + RESET);
            }
            else{
                long duration = endTime - startTime;
                System.out.println(GREEN + "Duration: " + RESET + RED + duration + RESET + GREEN +  " milliseconds" + RESET);

                System.out.print(GREEN + "Save to file? (Y/N): " + RESET);
                String save = myObj.nextLine();
                if (save.equals("Y") || save.equals("y")){
                    System.out.print(GREEN + "Enter file name: " + RESET);
                    String filename = myObj.nextLine();
                    SaveToFile.saveToFile(filename, board);
                    System.out.println(GREEN + "File saved!" + RESET);
                }
            }

            System.out.print(GREEN + "Another game? (Y/N): " + RESET);
            String tryAgain = myObj.nextLine();
            if (tryAgain.equals("N") || tryAgain.equals("n")){
                System.out.println("Goodbye!");
                break;
            }
            else if (tryAgain.equals("Y") || tryAgain.equals("y")){
                continue;
            }

        }
        System.exit(0);









    }
}