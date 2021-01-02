package gui;

import data.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class BarMenu extends JFrame {
    Table table;
    JButton displayStudent;
    JButton displayTree;
    JButton addStudent;
    JButton filtrer;
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
        JMenu menuFile = new JMenu("Fichier");
        JMenu choisir = new JMenu("Affichage");

        JMenuItem menuCSV = new JMenuItem("Enregistrer en CSV", KeyEvent.VK_S);
        JMenuItem menuQuitter = new JMenuItem("Quitter");

        JMenuItem menuChoisirBlocs = new JMenuItem("Choisir Bloc");
        JMenuItem menuChoisirProgram = new JMenuItem("Choisir Programme");
        JMenuItem menuChoisirStudent = new JMenuItem("Choisir Student");

        displayStudent = new JButton("Désactiver étudiants");
        displayTree = new JButton("Arborescence des programmes");
        addStudent = new JButton("Ajouter Etudiant");
        filtrer = new JButton("Filtrer");

        menuCSV.addActionListener(this::MenuCSVListener);
        menuQuitter.addActionListener(this::quitter);
        menuChoisirBlocs.addActionListener(this::MenuChoisirBlocs);
        menuChoisirProgram.addActionListener(this::MenuChoisirProgram);
        menuChoisirStudent.addActionListener(this::MenuChoisirStudent);
        displayStudent.addActionListener(this::ButtonDisplayStudent);
        displayTree.addActionListener(this::buttonDisplayTree);
        addStudent.addActionListener(this::addStudent);
        //filtrer.addActionListener(this::);

        ImageIcon menuCSVIcon = new ImageIcon(getClass().getResource("images/save.png"));
        menuCSV.setIcon(menuCSVIcon);
        ImageIcon menuQuitterIcon = new ImageIcon(getClass().getResource("images/exit.png"));
        menuQuitter.setIcon(menuQuitterIcon);
        ImageIcon blocIcon = new ImageIcon(getClass().getResource("images/bloc.png"));
        menuChoisirBlocs.setIcon(blocIcon);
        ImageIcon programIcon = new ImageIcon(getClass().getResource("images/program.png"));
        menuChoisirProgram.setIcon(programIcon);
        ImageIcon etudiantIcon = new ImageIcon(getClass().getResource("images/student.png"));
        menuChoisirStudent.setIcon(etudiantIcon);

        menuQuitter.setMnemonic(KeyEvent.VK_Q);
        menuQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.ALT_DOWN_MASK));
        menuCSV.setMnemonic(KeyEvent.VK_S);
        menuCSV.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        menuBar.add(menuFile);
        menuBar.add(choisir);

        menuFile.add(menuCSV);
        menuFile.add(menuQuitter);

        choisir.add(menuChoisirBlocs);
        choisir.add(menuChoisirProgram);
        choisir.add(menuChoisirStudent);

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

    private void quitter(ActionEvent event) {
        System.exit(0);
    }
}
