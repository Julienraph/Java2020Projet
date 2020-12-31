package data;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        XMLReader xmlReader = new XMLReader();
        xmlReader.initMap();
        List<Student> list = new ArrayList<>();
        for(Student student : xmlReader.getMapStudent().values()) {
            if(student.getProgramID().equals("SLINF3 180")) {
                list.add(student);
            }
        }
        ExportCSV export = new ExportCSV();

        StudentComparator studentcompa = new StudentComparator();
        Comparator<Student> comparator = studentcompa.sortName(true);
        Student student = xmlReader.getMapStudent().get("21781843");
        Data dataclass = new Data();

    }
}
