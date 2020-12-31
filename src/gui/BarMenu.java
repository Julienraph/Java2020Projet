package gui;

import data.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class BarMenu extends JFrame {
    Table table;
    JButton displayStudent;
    JButton displayTree;
    JButton addStudent;
    Boolean isDisplayTree = true;

    public BarMenu (Table table) {
        super("MenuBar");
        this.table = table;
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setJMenuBar(createMenu());
    }

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");

        JMenuItem menuCSV = new JMenuItem("Enregistrer en CSV", KeyEvent.VK_S);
        JMenuItem menuChoisirBlocs = new JMenuItem("Choisir Blocs");
        JMenuItem menuChoisirProgram = new JMenuItem("Choisir Programmes");
        JMenuItem menuChoisirStudent = new JMenuItem("Choisir Student");
        displayStudent = new JButton("Désactiver étudiants");
        displayTree = new JButton("Arborescence des programmes ");
        addStudent = new JButton("ajouter student");

        menuCSV.addActionListener(this::MenuCSVListener);
        menuChoisirBlocs.addActionListener(this::MenuChoisirBlocs);
        menuChoisirProgram.addActionListener(this::MenuChoisirProgram);
        menuChoisirStudent.addActionListener(this::MenuChoisirStudent);
        displayStudent.addActionListener(this::ButtonDisplayStudent);
        displayTree.addActionListener(this::buttonDisplayTree);
        addStudent.addActionListener(this::addStudent);

        menuCSV.setMnemonic(KeyEvent.VK_S);
        menuCSV.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        menuBar.add(menuFile);
        menuFile.add(menuCSV);
        menuFile.add(menuChoisirBlocs);
        menuFile.add(menuChoisirProgram);
        menuFile.add(menuChoisirStudent);
        menuBar.add(displayStudent);
        menuBar.add(displayTree);
        menuBar.add(addStudent);
        return menuBar;
    }

    private void MenuCSVListener(ActionEvent event) {
        ExportCSV exportCSV = new ExportCSV();
        exportCSV.exportCSV(table.getData(), table.getColumnNames());
    }

    private void MenuChoisirBlocs(ActionEvent event) {
        CheckBlocs checkBlocs = new CheckBlocs(table, 0);
    }

    private void MenuChoisirProgram(ActionEvent event) {
        CheckBlocs checkBlocs = new CheckBlocs(table, 1);
    }

    private void MenuChoisirStudent(ActionEvent event) {
        CheckBlocs checkBlocs = new CheckBlocs(table, 2);
    }

    private void ButtonDisplayStudent(ActionEvent event) {
        Boolean isDisplay = !table.getDisplayStudent();
        table.setDisplayStudent(isDisplay);
        if (isDisplay) {
            displayStudent.setText("Désactiver étudiants");
        } else {
            displayStudent.setText("Activer étudiants");
        }
        table.buttonClickDisplay();
    }

    private void buttonDisplayTree(ActionEvent event) {
        if (isDisplayTree) {
            table.getContentPane().getComponent(1).setVisible(false);
            isDisplayTree = !isDisplayTree;
        } else {
            table.getContentPane().getComponent(1).setVisible(true);
            isDisplayTree = !isDisplayTree;
        }
        table.revalidate();
    }

    private void addStudent(ActionEvent event) {
        Student student = new Student("22335843", "Aais", "Arnaud", new Program("L3 Informatique", "SLINF3 180"));
        table.ajouterStudent(student);
    }
}
