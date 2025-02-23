package generateimage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import board.Board;

public class GenerateImage {
    String GREEN = "\u001B[32m";
    String RESET = "\u001B[0m";
    private int size = 50;
    private static final Map<String, Color> ansiColorMap = initColorMap();
    private static Map<String, Color> initColorMap() {
        Map<String, Color> map = new HashMap<>();
        
        map.put("\u001B[31m", new Color(205, 0, 0));    
        map.put("\u001B[33m", new Color(205, 205, 0));
        map.put("\u001B[34m", new Color(0, 0, 205));
        map.put("\u001B[35m", new Color(205, 0, 205));
        map.put("\u001B[36m", new Color(0, 205, 205));
        map.put("\u001B[37m", new Color(229, 229, 229));
        map.put("\u001B[90m", new Color(127, 127, 127));
        map.put("\u001B[91m", new Color(255, 0, 0));
        map.put("\u001B[92m", new Color(0, 255, 0));
        map.put("\u001B[93m", new Color(255, 255, 0));
        map.put("\u001B[94m", new Color(0, 0, 255));
        map.put("\u001B[95m", new Color(255, 0, 255));
        map.put("\u001B[96m", new Color(0, 255, 255));
        map.put("\u001B[97m", new Color(255, 255, 255));
        map.put("\u001B[40m", new Color(0, 0, 0));
        map.put("\u001B[41m", new Color(205, 0, 0));
        map.put("\u001B[42m", new Color(0, 205, 0));
        map.put("\u001B[43m", new Color(205, 205, 0));
        map.put("\u001B[44m", new Color(0, 0, 205));
        map.put("\u001B[45m", new Color(205, 0, 205));
        map.put("\u001B[46m", new Color(0, 205, 205));
        map.put("\u001B[47m", new Color(229, 229, 229));
        map.put("\u001B[48m", new Color(127, 127, 127));
        map.put("\u001B[49m", new Color(0, 0, 0));
        
        return map;
    }

    public void GenerateImage(Board board, String outputPath) {
        int n = board.getBoardRow();
        int m = board.getBoardCol();
        String[][] colorCodes = board.getBoardColor();
        char[][] boardArray = board.getBoard();

        BufferedImage image = new BufferedImage(m * size, n * size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        g2d.setColor(new Color(30, 30, 30));
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char cell = boardArray[i][j];
                if (cell != '.' || cell != '#') {
                    String colorCode = colorCodes[i][j];
                    Color cellColor = ansiColorMap.getOrDefault(colorCode, Color.WHITE);
                    
                    g2d.setColor(cellColor);
                    g2d.fillRect(
                        j * size,
                        i * size,
                        size,
                        size
                    );
                    
                    g2d.setColor(new Color(60, 60, 60));
                    g2d.drawRect(
                        j * size,
                        i * size,
                        size,
                        size
                    );

                }
            }
        }
        
        g2d.dispose();
        
        try {
            ImageIO.write(image, "PNG", new File(outputPath));
            System.out.println(GREEN + "Result image saved to: " + outputPath + RESET);
        } catch (IOException e) {
            System.out.println();
        }
    }

}