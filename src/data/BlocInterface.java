package data;


import java.util.HashSet;

public interface BlocInterface {
    void addCourses(Course course);
    void calculCredits();
    HashSet<Course> getBlocCourses();
    double getMoyenne(Student student);
}
