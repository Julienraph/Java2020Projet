package data;

import gui.Table;

import java.io.*;
import java.util.Locale;
import java.util.Map;

public class ExportXML {
    private Table table;

    public ExportXML(Table table) {
        this.table = table;
    }

    public void exportXML() {
        try {
            FileWriter fw = new FileWriter(new File("C:\\Users\\rjuli\\Documents\\GitHub\\Projet2020\\src", "data.xml"));
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            XMLReader xmlReader = table.getXmlReader();
            pw.println("<?xml version=\"1.0\"?>");
            pw.println("<data>");
            for (Map.Entry<String, Course> entry : xmlReader.getMapCourse().entrySet()) {
                String courseID = entry.getKey();
                Course course = entry.getValue();
                pw.println("	<course>");
                pw.println(String.format("		<identifier>%s</identifier>", courseID));
                pw.println(String.format("		<name>%s</name>", course.getName()));
                pw.println(String.format("		<credits>%d</credits>", course.getCredit()));
                pw.println("	</course>");
            }
            for (Map.Entry<String, Program> programEntry : xmlReader.getMapProgram().entrySet()) {
                String programID = programEntry.getKey();
                Program program = programEntry.getValue();
                pw.println("	<program>");
                pw.println(String.format("		<identifier>%s</identifier>", programID));
                pw.println(String.format("		<name>%s</name>", program.getProgramName()));
                for (Bloc bloc : program.getBlocs()) {
                    if(!(bloc.getClass() == BlocSimple.class)) {
                        if (bloc.getClass() == BlocOption.class) {
                            pw.println("        <option>");
                        } else if (bloc.getClass() == BlocComposite.class) {
                            pw.println("        <composite>");
                        }
                        pw.println(String.format("          <identifier>%s</identifier>", bloc.getID()));
                        pw.println(String.format("          <name>%s</name>", bloc.getName()));
                        for(Course course : bloc.getBlocCourses()) {
                            pw.println(String.format("          <item>%s</item>", course.getID()));
                        }
                        if (bloc.getClass() == BlocOption.class) {
                            pw.println("        </option>");
                        } else if (bloc.getClass() == BlocComposite.class) {
                            pw.println("        </composite>");
                        }
                    }
                    else {
                        pw.println(String.format("      <item>%s</item>", bloc.getID()));
                    }
                }
                pw.println("	</program>");
                for (Map.Entry<String, Student> studentEntry : xmlReader.getMapStudent().entrySet()) {
                    String studentID = studentEntry.getKey();
                    Student student = studentEntry.getValue();
                    pw.println("    <student>");
                    pw.println(String.format("      <identifier>%s</identifier>", studentID));
                    pw.println(String.format("      <name>%s</name>", student.getName()));
                    pw.println(String.format("      <surname>%s</surname>", student.getSurname()));
                    pw.println(String.format("      <program>%s</program>", student.getProgram().getProgramID()));
                    for (Map.Entry<String, Double> noteCourseEntry : student.getNotesMap().entrySet()) {
                        String courseID = noteCourseEntry.getKey();
                        Double note = noteCourseEntry.getValue();
                        pw.println("        <grade>");
                        pw.println(String.format("          <item>%s</item>", courseID));
                        System.out.println(note);
                        pw.println(String.format(Locale.ROOT,"          <value>%.2f</value>", note));
                        pw.println("        </grade>");
                    }
                    pw.println("    </student>");
                }
            }
            pw.println("</data>");
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur sur le fichier %");
        }
    }
}
