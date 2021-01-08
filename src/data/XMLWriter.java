package data;

import gui.Table;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLWriter {
    public XMLWriter() {
        String filePath = "C:\\Users\\Adams Pierre David\\IdeaProjects\\JavaProjectDatabase\\src\\data2.xml";
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();

            // parse xml file and load into document
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            addCourse(doc);
            writeXMLFile(doc);

        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
            e1.printStackTrace();
        }
    }

    private static void writeXMLFile(Document doc)
            throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
        doc.getDocumentElement().normalize();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("C:\\Users\\Adams Pierre David\\IdeaProjects\\JavaProjectDatabase\\src\\data2.xml"));
        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("XML file updated successfully");
    }

    public static void addCourse(Document doc) {
        NodeList course = doc.getElementsByTagName("data");
        Element emp = null;

        emp = (Element) course.item(0);
        Element cours = doc.createElement("Course");
        emp.appendChild(cours);

        Element identifier = doc.createElement("identifier");
        identifier.appendChild(doc.createTextNode(Table.getCourseID()));
        cours.appendChild(identifier);

        //lastname elements
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(Table.getCourseN()));
        cours.appendChild(name);

        //nickname elements
        Element credits = doc.createElement("credits");
        credits.appendChild(doc.createTextNode(Table.getCourseC()));
        cours.appendChild(credits);
    }
}