package mpr;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;


/**
 The class is designed to create a simple window interface for the user.
 It is possible to enter the url and get statistics of unique words on the page,
 save the results to a file(".TXT" format), and clear the interaction fields.
 */


public class UserWindow {
    private int WIDTH = 800;
    private int HEIGHT = 600;

    public UserWindow() {
        JFrame frame = new JFrame("Статистика слов на странице");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         User interface elements are declared here
         */
        JLabel inputLabel = new JLabel("Введитe ссылку на страницу:");
        JLabel outputLabel = new JLabel("Результат:");
        JButton inputButton = new JButton("Считать");
        JButton clearButton = new JButton("Очистить");
        JButton saveButton = new JButton("Сохранить...");
        JTextField inputField = new JTextField(40);
        JTextArea outputArea = new JTextArea(30, 300);
        outputArea.setEditable(true);
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        outputArea.setLineWrap(true);
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(inputButton);
        JPanel outputPanel = new JPanel();
        outputPanel.add(outputLabel);
        outputPanel.add(scroll);
        JPanel editPanel = new JPanel();
        editPanel.add(clearButton);
        editPanel.add(saveButton);
        frame.getContentPane().add(inputPanel);
        frame.getContentPane().add(outputLabel);
        frame.getContentPane().add(scroll);
        frame.getContentPane().add(editPanel);
        frame.getContentPane().setLayout(new GridLayout(4, 1, 0, 0));
        outputLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.setVisible(true);

        /**
         Button listeners
         */

        class InputListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                ContentAction page = new ContentAction(inputField.getText());
                String outMy = page.getStatisticFromURL();
                outputArea.setText(outMy);
                inputField.setText("");
                frame.revalidate();
            }
        }

        class ClearListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                outputArea.setText("");
                inputField.setText("");
                frame.revalidate();
            }
        }

        class SaveListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "TEXT", "txt");
                fc.setFileFilter(filter);
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try (FileWriter fw = new FileWriter(fc.getSelectedFile()+".txt")) {
                        fw.write(outputArea.getText());
                        fw.close();
                    } catch (IOException ex) {
                        outputArea.setText("We have some problem with file: " + ex);
                    }
                }
            }
        }

        InputListener inputListener = new InputListener();
        inputButton.addActionListener(inputListener);
        ClearListener clearListener = new ClearListener();
        clearButton.addActionListener(clearListener);
        SaveListener saveListener = new SaveListener();
        saveButton.addActionListener(saveListener);
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}
