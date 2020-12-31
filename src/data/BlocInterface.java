package data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface BlocInterface {
    public void addCourses(Course course);
    public void calculCredits();
    public HashSet<Course> getBlocCourses();
    public double getMoyenne(Student student);

}
