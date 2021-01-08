package gui;

import data.*;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Table extends JFrame {
    private final JTable table;
    private String[][] data;
    private String[] columnNames;
    private final XMLReader xmlReader = new XMLReader();
    private List<Program> programList = new ArrayList<>();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private List<TeachingUnit> listTeachingUnit;
    private final List<Student> listStudentFull =  new ArrayList<>(xmlReader.getMapStudent().values());
    private List<Student> listStudentParameter = new ArrayList<>();
    private Boolean isDisplayStudent = true;
    private Boolean isProgram = true;
    int lastUpdate;
    private static String courseN;
    private static String courseID;
    private static String courseC;


    public Table() {
        setLayout(new BorderLayout());
        programList.add(xmlReader.getMapProgram().get("SLINF3 180"));
        updateTableModelProgram();
        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JTree tree = createTree();
        JScrollPane westComponent = new JScrollPane(tree);
        westComponent.setPreferredSize(new Dimension(250, 0));
        add(scrollPane, BorderLayout.CENTER);
        add(westComponent, BorderLayout.WEST);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        Table gui = new Table();
        BarMenu menu = new BarMenu(gui);
        gui.setJMenuBar(menu.createMenu());
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setSize(1000,500);
        gui.setLocationRelativeTo(null);
        gui.setTitle("Université Côte d'Azur");
        gui.setVisible(true);
    }

    /*Si on choisit un/plusieurs programmes dans "Choisir programmes" de l'interface
    Cette fonction sera appelé pour les afficher.
     */
    public void checkBoxProgram(List<String> listProgramID) {
        programList = new ArrayList<>();
        for(String programID : listProgramID) {
            programList.add(xmlReader.getMapProgram().get(programID));
        }
        updateTableModelProgram();
    }

    /*Si on choisit un/plusieurs étudiants dans "Choisir Student" de l'interface
   Cette fonction sera appelé pour les afficher.
    */
    public void checkBoxStudent(List<String> listStudentID) {
        HashSet<Program> setProgram = new HashSet<>();
        programList = new ArrayList<>();
        listStudentParameter = new ArrayList<>();
        for(String studentID : listStudentID) {
            listStudentParameter.add(xmlReader.getMapStudent().get(studentID));
            setProgram.add(xmlReader.getMapStudent().get(studentID).getProgram());
        }
        programList = new ArrayList<>(setProgram);
        updateTableModelStudent();
    }

    /*Si on choisit un/plusieurs blocs dans "Choisir Blocs" de l'interface
   Cette fonction sera appelé pour les afficher.
    */
    public void checkBoxBlocs(List<String> listBlocID) {
        List<Bloc> setBlocList = new ArrayList<>();
        for(String blocID : listBlocID) {
            setBlocList.add(xmlReader.getMapBlocs().get(blocID));
        }
        listTeachingUnit = Data.BlocToList(setBlocList);
        updateTableModelBlocs();
    }

    public void update() {
        Comparator<Student> comparator = StudentComparator.sortName(true);
        data = Data.initData(listTeachingUnit, listStudentParameter, comparator, isProgram, isDisplayStudent, programList);
        columnNames = Data.initColumnName(programList, listTeachingUnit, isDisplayStudent);
        tableModel.setDataVector(data, columnNames);
    }

    public void updateTableModelBlocs() {
        programList = new ArrayList<>();
        listStudentParameter = new ArrayList<>();
        listStudentParameter = Data.initListStudentsBlocs(listStudentFull, listTeachingUnit);
        isProgram = false;
        lastUpdate = 0;
        update();

    }

    public void updateTableModelProgram() {
        listTeachingUnit = Data.programToBlocs(programList);
        System.out.println(listStudentParameter);
        listStudentParameter = Data.initListStudents(listStudentFull, programList);
        System.out.println(listStudentParameter);
        isProgram = true;
        lastUpdate = 1;
        update();
    }

    public void updateTableModelStudent() {
        listTeachingUnit = Data.initCoursStudent(listStudentParameter);
        isProgram = true;
        lastUpdate = 2;
        update();
    }

    public void buttonClickDisplay() {
        if(lastUpdate == 0) {updateTableModelBlocs();}
        if(lastUpdate == 1) {updateTableModelProgram();}
        if(lastUpdate == 2) {updateTableModelStudent();}
    }

    /*La méthode  createTree crée l'arborescence des programmes*/
    private JTree createTree() {
        List<Program> programList = new ArrayList<Program>(xmlReader.getMapProgram().values());
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Université Côte d'Azur");
        JTree tree = new JTree(root);
        for(Program program : programList) {
            DefaultMutableTreeNode programNode = new DefaultMutableTreeNode(program.getProgramName());
            for (Bloc bloc : program.getBlocs()) {
                DefaultMutableTreeNode blocNode = new DefaultMutableTreeNode(bloc.getName());
                for (Course course : bloc.getBlocCourses()) {
                    if (!(BlocSimple.class == bloc.getClass())) {
                        blocNode.add(new DefaultMutableTreeNode(course.getID() + " - " + course.getName()));
                    } else {
                        programNode.add(new DefaultMutableTreeNode(course.getID() + " - " + course.getName()));
                    }
                }
                if (!(BlocSimple.class == bloc.getClass())) {
                    {
                        programNode.add(blocNode);
                    }
                }
            }
            root.add(programNode);
        }
        return tree;
    }

    /*Cette fonction permet d'ajouter un étudiant de la liste des étudiants*/
    public void addStudent(Student student) {
        xmlReader.getMapStudent().put(student.getIdentifier(), student);
        xmlReader.getMapStudent().get(student.getIdentifier()).getNotesMap().put("SLUIN501", 15.0);
        listStudentParameter.add(student);
        update();
    }

    public void addCourse(String courseID , String courseName , String courseCredit) {
        Course course = new Course(courseID,courseName,Integer.parseInt(courseCredit));
        xmlReader.getMapCourse().get(course.getID());
        listTeachingUnit.add(course);
        updateTableModelStudent();
        updateTableModelProgram();
    }

    public void addProgram(String programID, String programName) {
        Program program = new Program(programID,programName);
        xmlReader.getMapProgram().get(program.getProgramID());
        programList.add(program);
        updateTableModelProgram();
    }

    public void addNote(String studentID, String courseID, String note){
        Student student = verif(studentID, courseID, note);
        if (student != null ) {
            student.getNotesMap().put(courseID, Double.valueOf(note));
            update();
        }
    }

    /*Cette fonction permet de supprimer de la classe student un étudiant de la liste*/
    public void deleteStudent(String identifier){
        Student student = xmlReader.getMapStudent().get(identifier);
        if (student !=  null){
            String.format("L'étudiant %s de n°INE :%s sera supprimé de la liste ",student.getName(), student.getIdentifier());
            xmlReader.getMapStudent().remove(identifier);
            listStudentParameter.remove(student);
        }else {
            JOptionPane.showMessageDialog(this,"Aucun étudiant réfèrencé à cette Identifiant !","Numéro étudiant INCORRECT"
                    ,JOptionPane.ERROR_MESSAGE);
        }
        update();
    }


    public void removeNote(String studentID, String courseID){
        Student student = verif(studentID, courseID);
        if (student != null) {
            student.getNotesMap().remove(courseID);
            update();
        }
    }

    private Student verif(String studentID, String courseID, String note) {
        Student student = verif(studentID, courseID);
        if ((student != null) && !(note.equals(""))) {
            return student;
        } else if (student != null && (note.equals("")))
            JOptionPane.showMessageDialog(this, "Veuillez rentrer une note.", "ERROR"
                    , JOptionPane.ERROR_MESSAGE);
        return null;
    }

    private Student verif(String studentID, String courseID) {
        Student student = xmlReader.getMapStudent().get(studentID);
        if(studentID.equals("") || courseID.equals("")) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir les informations.", "ERROR"
                    , JOptionPane.ERROR_MESSAGE);
        }
        else if (student != null && !(courseID.equals("")) && !(xmlReader.getMapCourse().containsKey(courseID))) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un cours existant.", "ERROR"
                    , JOptionPane.ERROR_MESSAGE);
        }
        else if (student != null && !(studentID.equals("") || courseID.equals("")) && xmlReader.getMapCourse().containsKey(courseID)) {
            return student;
        }
        else if (student == null){
            JOptionPane.showMessageDialog(this, "Veuillez saisir un numéro étudiant existant.", "ERROR"
                    , JOptionPane.ERROR_MESSAGE);}
        return null;
    }

    public String[][] getData() {
        return data;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public XMLReader getXmlReader() {
        return xmlReader;
    }

    public void setDisplayStudent(Boolean displayStudent) {
        isDisplayStudent = displayStudent;
    }

    public Boolean getDisplayStudent() {
        return isDisplayStudent;
    }
    public void setCourseN(String courseN) {
        this.courseN = courseN;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseC(String courseC) {
        this.courseC = courseC;
    }

    public static String getCourseN() {
        return courseN;
    }

    public static String getCourseID() {
        return courseID;
    }

    public static String getCourseC() {
        return courseC;
    }
}
