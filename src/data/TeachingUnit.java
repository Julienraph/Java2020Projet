package data;

import java.util.List;

public interface TeachingUnit{
    public double getNoteMoyenne(List<Student> list);
    public double getNoteMax(List<Student> studentList);
    public double getNoteMin(List<Student> studentList);
    public double getEcartType(List<Student> studentList);
    public int getCredit();
    public String getName();
    public String getID();
    public double getMoyenne(Student student);
}
