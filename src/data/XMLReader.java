package data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLReader {
    private final HashMap<String, Student> mapStudent;
    private final HashMap<String, Course> mapCourse;
    private final HashMap<String, Program> mapProgram;
    private final HashMap<String, Bloc> mapBlocs;

    public XMLReader() {
        this.mapStudent = new HashMap<>();
        this.mapCourse = new HashMap<>();
        this.mapProgram = new HashMap<>();
        this.mapBlocs = new HashMap<>();
        initMap();
    }

    public void initMap() {
        try {
            File dir = new File("/Users/ibra-kane/Desktop/L3/S5/Programmation & Conception en Java/Java2020Projet/src");
            dir.mkdirs();
            File file = new File (dir, "data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);; // ouverture et lecture du fichier XML
            doc.getDocumentElement().normalize(); // normalise le contenu du fichier, opération très conseillée
            Element root = doc.getDocumentElement(); // la racine de l'arbre XML

            List<Element> courseList = getChildren(root, "course");
            for (Element courseElement : courseList) {
                String id = courseElement.getElementsByTagName("identifier").item(0).getTextContent();
                String name = courseElement.getElementsByTagName("name").item(0).getTextContent();
                int credit = Integer.parseInt(courseElement.getElementsByTagName("credits").item(0).getTextContent());
                mapCourse.put(id, new Course(name, id, credit));
            }

            List<Element> programList = getChildren(root, "program");
            for (Element programElement : programList) {
                String identifier = programElement.getElementsByTagName("identifier").item(0).getTextContent();
                String name = programElement.getElementsByTagName("name").item(0).getTextContent();
                Program program = new Program(name, identifier);
                List<Element> blocList = getChildren(programElement);
                for (Element blocElement : blocList) {
                    if (blocElement.getTagName().equals("option") || blocElement.getTagName().equals("composite")) {
                        String blocName = blocElement.getElementsByTagName("name").item(0).getTextContent();
                        String blocID = blocElement.getElementsByTagName("identifier").item(0).getTextContent();
                        BlocComposite blocComposite = new BlocComposite(blocName, blocID);
                        BlocOption blocOption = new BlocOption(blocName, blocID);
                        List<Element> itemList = getChildren(blocElement, "item");
                        for (Element item : itemList) {
                            String coursID = item.getTextContent();
                            if (blocElement.getTagName().equals("option")) {
                                blocOption.addCourses(mapCourse.get(coursID));
                            } else { blocComposite.addCourses(mapCourse.get(coursID)); }
                        }
                        if (blocElement.getTagName().equals("option")) {
                            program.addBloc(blocOption);
                        } else { program.addBloc(blocComposite); }
                    }
                    if (blocElement.getTagName().equals("item")) {
                        String blocSimpleID = blocElement.getTextContent();
                        BlocSimple blocSimple = new BlocSimple(mapCourse.get(blocSimpleID).getName(), blocSimpleID);
                        blocSimple.addCourses(mapCourse.get(blocSimpleID));
                        program.addBloc(blocSimple);
                    }
                }
                mapProgram.put(identifier, program);
            }

            List<Element> studentList = getChildren(root, "student");
            for (Element studentElement : studentList) {
                String identifier = studentElement.getElementsByTagName("identifier").item(0).getTextContent();
                String name = studentElement.getElementsByTagName("name").item(0).getTextContent();
                String surname = studentElement.getElementsByTagName("surname").item(0).getTextContent();
                String program = studentElement.getElementsByTagName("program").item(0).getTextContent();
                Program programStudent = mapProgram.get(program);
                Student student = new Student(identifier, name, surname, programStudent);
                mapStudent.put(identifier, student);
                List<Element> gradeList = getChildren(studentElement, "grade");
                for (Element gradeElement : gradeList) {
                    String matiere = gradeElement.getElementsByTagName("item").item(0).getTextContent();
                    String noteString = gradeElement.getElementsByTagName("value").item(0).getTextContent();
                    double note = - 1;
                    if (!noteString.equals("ABI")) {
                        note = (Double) Double.parseDouble(noteString);
                    }
                    student.getNotesMap().put(matiere, note);
                }
            }

            for (Map.Entry<String,Program> entry : mapProgram.entrySet()) {
                Program program = entry.getValue();
                for(Bloc bloc : program.getBlocs()) {
                    mapBlocs.put(bloc.getID(), bloc);
                }
            }
        } catch (XMLReaderException | ParserConfigurationException readerException) {
            System.out.println("ERROR");
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }


    }

    public HashMap<String, Student> getMapStudent() {
        return mapStudent;
    }

    public HashMap<String, Course> getMapCourse() {
        return mapCourse;
    }

    public HashMap<String, Program> getMapProgram() {
        return mapProgram;
    }

    public HashMap<String, Bloc> getMapBlocs() {
        return mapBlocs;
    }

    /* Extrait de la liste des fils de l'élément item dont le tag est name */

    private static List<Element> getChildren(Element item, String name) {
        NodeList nodeList = item.getChildNodes();
        List<Element> children = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeList.item(i); // cas particulier pour nous où tous les noeuds sont des éléments
                if (element.getTagName().equals(name)) {
                    children.add(element);
                }
            }
        }
        return children;
    }

    private static List<Element> getChildren(Element item) {
        NodeList nodeList = item.getChildNodes();
        List<Element> children = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodeList.item(i); // cas particulier pour nous où tous les noeuds sont des éléments
                children.add(element);
            }
        }
        return children;
    }
}