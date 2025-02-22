package readsave;

import java.io.*;
import java.util.*;
import puzzlepieces.Piece;

public class ReadFromFile {
    public List<String> readFileFromFile(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            return null;
        }
        return lines;
    }

    public List<String> readFileFromString(String filename){
        List<String> lines = new ArrayList<>();
        String filenamewithfolder = "test/input/" + filename;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filenamewithfolder));
            String currentline;
            while ((currentline = reader.readLine()) != null) {
                lines.add(currentline);
            }
        }
        catch (IOException e) {
            return null;
        }
        return lines;
    }

    public List<Integer> makeBoard(List<String> lines){
        List<Integer> result = new ArrayList<>();
        int rowBoard = Integer.parseInt(lines.get(0).split(" ")[0]);
        int colBoard = Integer.parseInt(lines.get(0).split(" ")[1]);
        int totalPieces = Integer.parseInt(lines.get(0).split(" ")[2]);
        String type = lines.get(1);
        int inttype = -1;
        if ("DEFAULT".equals(type)){
            inttype = 0;
        }
        else if ("CUSTOM".equals(type)){
            inttype = 1;
        }
        else {
            inttype = 2;
        }
        result.add(rowBoard);
        result.add(colBoard);
        result.add(inttype);
        result.add(totalPieces);
        return result;
    }

    public char[][] makeBoardCustom(List<String> lines){
        int rowBoard = Integer.parseInt(lines.get(0).split(" ")[0]);
        int colBoard = Integer.parseInt(lines.get(0).split(" ")[1]);
        char[][] board = new char[rowBoard][colBoard];
        int idx = 2;
        try{
            for (int i = 0; i < rowBoard; i++){
                String temp = lines.get(idx+i);
                for (int j = 0; j <colBoard; j++){
                    if (temp.charAt(j) == '.'){
                        board[i][j] = '#';
                    }
                    else if (temp.charAt(j) == 'X'){
                        board[i][j] = '.';
                    }
                }
            }
            return board;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    public List<Piece> makePieces(List<String> lines, int startLine){


        String[] colors = {
            "\u001B[31m",
            "\u001B[33m",
            "\u001B[34m",
            "\u001B[35m",
            "\u001B[36m",
            "\u001B[37m",
            "\u001B[90m",
            "\u001B[91m",
            "\u001B[92m",
            "\u001B[93m",
            "\u001B[94m",
            "\u001B[95m",
            "\u001B[96m",
            "\u001B[97m",
            "\u001B[40m",
            "\u001B[41m",
            "\u001B[42m",
            "\u001B[43m",
            "\u001B[44m",
            "\u001B[45m",
            "\u001B[46m",
            "\u001B[47m",
            "\u001B[48m",
            "\u001B[49m",
        };
        int totalPieces = Integer.parseInt(lines.get(0).split(" ")[2]);
        String tempLine;
        List<Piece> pieces = new ArrayList<>();

        int maxLength = lines.size();
        int idx = startLine;
        tempLine = lines.get(idx);
        Character currentChar;
        int i = 0;
        while (idx < maxLength){
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
            int longestRow = 1;
            int longestCol = tempLine.length();
            int savedidx = idx;
            while (true){
                if (idx == maxLength-1){
                    idx++;
                    break;
                }
                idx++;
                if (idx >= lines.size()) {
                    System.out.println("Banyak pieces tidak sesuai");
                    break;
                }
                tempLine = lines.get(idx);
                if ((currentChar != tempLine.charAt(0)) && !(tempLine.charAt(0) == ' ')){
                    break;
                }
                int endChar = 0;
                boolean isStillSameCharacter = true;
                while (tempLine.charAt(endChar) == ' '){
                    endChar++;
                    if (tempLine.charAt(endChar) == ' '){
                        continue;
                    }
                    if (tempLine.charAt(endChar) == currentChar){
                        isStillSameCharacter = true;
                        break;
                    }
                    else{
                        isStillSameCharacter = false;
                        break;
                    }
                }

                if (!isStillSameCharacter){
                    break;
                }

                if (tempLine.length() > longestCol){
                    longestCol = tempLine.length();
                }
                longestRow++;
            }
            int lastidx = idx;
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
            Piece newPiece = new Piece(intoPiece, longestRow, longestCol, currentChar, colors[i]);
            pieces.add(newPiece);
            i++;
        }
        return pieces;
    }
}