package readsave;

import java.io.*;
import board.Board;
public class SaveToFile {

    public static void saveToFile(String filename, Board board){
        try (FileWriter writer = new FileWriter("test/output/" + filename);
            BufferedWriter bw = new BufferedWriter(writer))
            {
                bw.write("Solution: ");
                bw.newLine();
                for (int i = 0; i < board.getBoardRow(); i++){
                    for (int j = 0; j < board.getBoardCol(); j++){
                        if (board.getBoard()[i][j] == '.' || board.getBoard()[i][j] == '#'){
                            bw.write(" ");
                        }
                        else{
                            bw.write(board.getBoard()[i][j]);
                        }
                    }
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.print(e);
            }
        }
    }
