package readinput;

import java.io.*;
import java.util.*;
import puzzlepieces.Piece;
import board.Board;



public class ReadFromFile {
    public List<String> readFile(String filename){
        List<String> lines = new ArrayList<>();
        String filenamewithfolder = "test/" + filename;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filenamewithfolder));
            String currentline;
            while ((currentline = reader.readLine()) != null) {
                lines.add(currentline);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public List<Integer> makeBoard(List<String> lines){
        List<Integer> result = new ArrayList<>();
        int rowBoard = Integer.parseInt(lines.get(0).split(" ")[0]);
        int colBoard = Integer.parseInt(lines.get(0).split(" ")[1]);
        int totalPieces = Integer.parseInt(lines.get(0).split(" ")[2]);
        Board board = new Board(rowBoard,colBoard);
        String type = lines.get(1);
        int inttype = -1;
        if ("DEFAULT".equals(type)){
            inttype = 0;
        }
        else if ("CUSTOM".equals(type)){
            inttype = 1;
        }
        else if ("PYRAMID".equals(type)){
            inttype = 2;
        }
        result.add(rowBoard);
        result.add(colBoard);
        result.add(inttype);
        return result;
    }

    public List<Piece> makePieces(List<String> lines){

        int totalPieces = Integer.parseInt(lines.get(0).split(" ")[2]);
        String tempLine;
        List<Piece> pieces = new ArrayList<>();

        int maxLength = lines.size();
        // System.out.println("Max length: ");
        // System.out.println(maxLength);
        int idx = 2;
        tempLine = lines.get(idx);
        Character currentChar;
        int i = 0;
        while (i < totalPieces){
            // System.out.println("Masuk while");
            int idxtemp = 0;
            while (true){
                if (tempLine.charAt(idxtemp) == ' '){
                    idxtemp++;
                }
                else{
                    currentChar = tempLine.charAt(idxtemp);
                    break;
                }
            }
            // System.out.println(currentChar);
            int longestRow = 1;
            int longestCol = tempLine.length();
            int savedidx = idx;
            // System.out.printf("sebelum masuk while: ");
            while (true){
                // System.out.printf("idx: ");
                // System.out.println(idx);
                if (idx == maxLength-1){
                    idx++;
                    break;
                }
                idx++;
                tempLine = lines.get(idx);
                if ((currentChar != tempLine.charAt(0)) && (tempLine.charAt(0) != ' ')){
                    break;
                }
                if (tempLine.length() > longestCol){
                    longestCol = tempLine.length();
                }
                longestRow++;
            }
            int lastidx = idx;
            // System.out.println("Dimensi pieces");
            // System.out.println(longestRow);
            // System.out.println(longestCol);
            // System.out.println("done");
            // System.out.println(lastidx);
            boolean[][] intoPiece = new boolean[longestRow][longestCol];
            for (int j = savedidx; j < lastidx; j++){
                String temp = lines.get(j);
                for (int k = 0; k < temp.length(); k++){
                    if (temp.charAt(k) == currentChar){
                        intoPiece[j-savedidx][k] = true;
                    }
                    else{
                        intoPiece[j-savedidx][k] = false;
                    }
                }
            }
            Piece newPiece = new Piece(intoPiece, longestRow, longestCol, currentChar);
            pieces.add(newPiece);
            i++;
            // System.out.printf("Index terakhir: ");
            // System.out.println(idx);
        }

        return pieces;
    }
}