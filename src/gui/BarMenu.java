package gui;

import data.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.List;

public class BarMenu extends JFrame {
    Table table;
    JButton displayStudent;
    JButton displayTree;
    JButton addStudent;
    JButton deleteStudent;
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
        JMenu menuFile = new JMenu("Fichier  ");
        JMenu choisir = new JMenu("Affichage ");
        JMenu editMenu = new JMenu("Modification");

        JMenuItem menuCSV = new JMenuItem("Enregistrer en CSV", KeyEvent.VK_S);
        JMenuItem menuQuitter = new JMenuItem("Quitter");

        JMenuItem menuChoisirBlocs = new JMenuItem("Choisir Bloc");
        JMenuItem menuChoisirProgram = new JMenuItem("Choisir Programme");
        JMenuItem menuChoisirStudent = new JMenuItem("Choisir Student");

        JMenuItem menuAddCourse = new JMenuItem("Ajouter Cours");
        JMenuItem menuAddNote = new JMenuItem("Ajouter/modifier note");
        JMenuItem menuDeleteNote = new JMenuItem("Supprimer note");
        JMenuItem menuAddStudent = new JMenuItem("Ajouter Etudiant");
        JMenuItem menuDeletStudent = new JMenuItem("Effacer Etudiant");
        //JMenuItem menuDeactivateStudent = new JMenuItem("Desactiver Etudiant");

        displayStudent = new JButton("Désactiver étudiants");
        displayTree = new JButton("Arborescence des programmes");
        addStudent = new JButton("Ajouter Etudiant");
        deleteStudent = new JButton("Supprimer Etudiant");

        menuCSV.addActionListener(this::MenuCSVListener);
        menuQuitter.addActionListener(this::quitter);
        menuChoisirBlocs.addActionListener(this::MenuChoisirBlocs);
        menuChoisirProgram.addActionListener(this::MenuChoisirProgram);
        menuChoisirStudent.addActionListener(this::MenuChoisirStudent);
        menuAddStudent.addActionListener(this::addStudent);
        menuDeletStudent.addActionListener(this::deleteStudent);
        //menuDeactivateStudent.addActionListener(this::ButtonDisplayStudent);
        menuAddCourse.addActionListener(this::addPCourse);
        menuAddNote.addActionListener(this::addNote);
        menuDeleteNote.addActionListener(this::removeNote);
        displayStudent.addActionListener(this::ButtonDisplayStudent);
        displayTree.addActionListener(this::buttonDisplayTree);
        //addStudent.addActionListener(this::addStudent);
        //deleteStudent.addActionListener(this::deleteStudent);


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
        menuBar.add(editMenu);

        menuFile.add(menuCSV);
        menuFile.add(menuQuitter);

        choisir.add(menuChoisirBlocs);
        choisir.add(menuChoisirProgram);
        choisir.add(menuChoisirStudent);

        editMenu.add(menuAddCourse);
        editMenu.add(menuAddNote);
        editMenu.add(menuDeleteNote);
        editMenu.add(menuAddStudent);
        editMenu.add(menuDeletStudent);
        //editMenu.add(menuDeactivateStudent);

        menuBar.add(displayStudent);
        menuBar.add(displayTree);
        //menuBar.add(addStudent);
        //menuBar.add(deleteStudent);

        return menuBar;
    }

    private void MenuCSVListener(ActionEvent event) {
        ExportXML exportXML = new ExportXML(table);
        exportXML.exportXML();
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
        } else {
            table.getContentPane().getComponent(1).setVisible(true);
        }
        isDisplayTree = !isDisplayTree;
        table.revalidate();
    }

    private void addStudent(ActionEvent event) {
        JTextField studentID = new JTextField();
        JTextField studentName = new JTextField();
        JTextField studentSurname = new JTextField();
        JTextField studentProgram = new JTextField();
        Object[] message = {
                "N° étudiant :", studentID,
                "Nom:", studentSurname,
                "Prénom :", studentName,
                "Programme :", studentProgram
        };
        int n = JOptionPane.showConfirmDialog(null, message, "Ajouter un étudiant", JOptionPane.OK_CANCEL_OPTION);
        if(n == JOptionPane.OK_OPTION) {
            Program program = table.getXmlReader().getMapProgram().get(studentProgram.getText());
            if(studentID.getText().equals("") || studentName.getText().equals("") || studentSurname.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir les informations", "ERROR"
                        , JOptionPane.ERROR_MESSAGE);
            }
            if (program == null && !(studentID.getText().equals("") || studentName.getText().equals("") || studentSurname.getText().equals(""))) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir un programme existant", "ERROR"
                        , JOptionPane.ERROR_MESSAGE);
            }
            if (program != null && !(studentID.getText().equals("") || studentName.getText().equals("") || studentSurname.getText().equals(""))) {
                table.addStudent(new Student(studentID.getText(), studentName.getText(), studentSurname.getText(), program));
            }
        }
    }

    private void addNote(ActionEvent event) {
        JTextField studentID = new JTextField();
        JTextField courseID = new JTextField();
        JTextField note = new JTextField();
        Object[] message = {
                "N° étudiant :", studentID,
                "Code Cours:", courseID,
                "Note :", note
        };
        int n = JOptionPane.showConfirmDialog(null, message, "Ajouter un étudiant", JOptionPane.OK_CANCEL_OPTION);
        if(n == JOptionPane.OK_OPTION) {
            table.addNote(studentID.getText(), courseID.getText(), note.getText());
        }
    }

    public void addPCourse (ActionEvent event){
        JTextField courseID = new JTextField();
        JTextField courseName = new JTextField();
        JTextField creditCourse = new JTextField();
        Object [] msg = {
                "Code cours" , courseID,
                "Nom cours", courseName,
                "Credit du cours ", creditCourse
        };
        int n = JOptionPane.showConfirmDialog(null, msg, "Ajouter un Cours", JOptionPane.OK_CANCEL_OPTION);
        if(n == JOptionPane.OK_OPTION){
            //Course course = table.getXmlReader().getMapCourse().get(courseID);
            table.addCourse(courseID.getText(), courseName.getText(), creditCourse.getText());

        }
    }

    private void removeNote(ActionEvent event) {
        JTextField studentID = new JTextField();
        JTextField courseID = new JTextField();
        Object[] message = {
                "N° étudiant :", studentID,
                "Code Cours:", courseID,
        };
        int n = JOptionPane.showConfirmDialog(null, message, "Ajouter un étudiant", JOptionPane.OK_CANCEL_OPTION);
        if(n == JOptionPane.OK_OPTION) {
            table.removeNote(studentID.getText(), courseID.getText());
        }
    }

    private void deleteStudent(ActionEvent event) {
        String message = "Quel est le numéro étudiant? ";
        String studentID = JOptionPane.showInputDialog(this, message, "Effacer étudiant", JOptionPane.PLAIN_MESSAGE);
        table.deleteStudent(studentID);
    }

    private void quitter(ActionEvent event) {
        System.exit(0);
    }
}