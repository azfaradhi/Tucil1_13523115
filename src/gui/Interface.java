package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import board.Board;
import puzzlepieces.Piece;
import placingpuzzle.PlacingPuzzle;
import readsave.ReadFromFile;
import readsave.SaveToFile;
import java.util.List;

public class Interface extends JFrame {
    private JLabel statusLabel;
    private JLabel imageLabel;
    private File selectedFile;
    private Board board;
    long startTime;
    long endTime;
    private final String OUTPUT_IMAGE_PATH = "test/output/result.png";

    public Interface() {
        setTitle("File Processor");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton selectFileButton = new JButton("Select File");
        JButton processButton = new JButton("Process File");
        JButton saveButton = new JButton("Save Result to TXT");

        statusLabel = new JLabel("No file selected");
        imageLabel = new JLabel();

        selectFileButton.addActionListener(e -> selectFile());
        processButton.addActionListener(e -> {
            if (selectedFile != null) {
                ReadFromFile read = new ReadFromFile();
                List<String> lines = read.readFileFromFile(selectedFile);
                if (lines == null) {
                    JOptionPane.showMessageDialog(this, "Failed to read file!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Integer> boardInfo = read.makeBoard(lines);
                board = new Board(boardInfo.get(0), boardInfo.get(1), boardInfo.get(2));

                int addForStartIndex = 2;
                if (boardInfo.get(2) == 1) {
                    char[][] newBoard = read.makeBoardCustom(lines);
                    board.setBoard(newBoard);
                    addForStartIndex += board.getBoardRow();
                }

                List<Piece> pieces = read.makePieces(lines, addForStartIndex);
                PlacingPuzzle main = new PlacingPuzzle(board, pieces);
                startTime = System.currentTimeMillis();
                boolean result = main.mainSolvingPuzzle(0, 0);
                endTime = System.currentTimeMillis();
                long time = endTime - startTime;
                if (!result){
                    JOptionPane.showMessageDialog(this, "No solution found!");
                }
                else{
                    JOptionPane.showMessageDialog(this, "Processing complete, duration: " + time + " milliseconds");
                    displayImage();
                }

            }
        });
        saveButton.addActionListener(e -> saveResult());

        panel.add(selectFileButton);
        panel.add(processButton);
        panel.add(saveButton);

        add(panel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.SOUTH);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            statusLabel.setText("Selected: " + selectedFile.getName());
        }
    }

    private void processFile() {
        if (selectedFile != null) {
            ReadFromFile read = new ReadFromFile();
            java.util.List<String> lines = read.readFileFromFile(selectedFile);
            java.util.List<Integer> boardInfo = read.makeBoard(lines);
            Board board = new Board(boardInfo.get(0), boardInfo.get(1), boardInfo.get(2));

            int addForStartIndex = 2;
            if (boardInfo.get(2) == 1) {
                char[][] newBoard = read.makeBoardCustom(lines);
                board.setBoard(newBoard);
                addForStartIndex += board.getBoardRow();
            }
            java.util.List<Piece> pieces = read.makePieces(lines, addForStartIndex);
            PlacingPuzzle main = new PlacingPuzzle(board, pieces);
            
            boolean result = main.mainSolvingPuzzle(0, 0);

            statusLabel.setText("Processing complete. Image saved.");
            displayImage();
        }
    }

    private void displayImage() {
        File file = new File(OUTPUT_IMAGE_PATH);
        if (file.exists()) {
            ImageIcon imageIcon = new ImageIcon(OUTPUT_IMAGE_PATH);
            imageLabel.setIcon(imageIcon);
            imageLabel.setText("");
        } else {
            imageLabel.setText("Image not found");
        }
    }

    private void saveResult() {
        if (board == null) { 
            JOptionPane.showMessageDialog(this, "No board data to save!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String fileName = JOptionPane.showInputDialog(this, "Enter file name:", "Save Result", JOptionPane.PLAIN_MESSAGE);
        if (fileName != null && !fileName.trim().isEmpty()) {
            SaveToFile save = new SaveToFile();
            save.saveToFile(fileName, board);
            JOptionPane.showMessageDialog(this, "File saved successfully in test/output/" + fileName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Interface frame = new Interface();
            frame.setVisible(true);
        });
    }
}
