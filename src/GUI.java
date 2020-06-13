import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {

    private final String TITLE = "sudoku solver";
    private final int CELLSIZE = 50;

    private final JFrame frame = new JFrame(TITLE);
    private final JTextField[][] textFields = new JTextField[9][9];
    
    public GUI() {
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.add(panel);

        JPanel sudokuPanel = new JPanel(new GridLayout(3,3));
        sudokuPanel.setPreferredSize(new Dimension(9 * CELLSIZE, 9 * CELLSIZE));
        panel.add(sudokuPanel);

        // panels for the 3 by 3 blocks
        JPanel[][] blockPanels = new JPanel[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blockPanels[i][j] = new JPanel(new GridLayout(3,3));
                blockPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                sudokuPanel.add(blockPanels[i][j]);
            }
        }

        // text fields for the sudoku cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j] = new JTextField(2);
                textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                blockPanels[(int)Math.floor(i / 3.0)][(int)Math.floor(j / 3.0)].add(textFields[i][j]);
            }
        }

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel);

        // solve button
        JButton solveButton = new JButton("solve");
        solveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                SolveSudoku();
            }
        });
        buttonPanel.add(solveButton);

        // reset button
        JButton resetButton = new JButton("reset");
        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                ResetTextFields();
            }
        });
        buttonPanel.add(resetButton);

        frame.pack();
    }

    private void ResetTextFields() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                textFields[i][j].setText("");
                textFields[i][j].setBackground(Color.WHITE);
            }
        }
    }

    private void SolveSudoku() {

        // TODO
    }
}