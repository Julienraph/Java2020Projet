package data;

import java.util.Comparator;

public class StudentComparator {

    public StudentComparator() {}

    public static Comparator<Student> sortName(boolean ascending) {
        return toAscending(Comparator.comparing(Student::getName), ascending);
    }

    public static Comparator<Student> sortSurname(boolean ascending) {
        return toAscending(Comparator.comparing(Student::getSurname), ascending);
    }

    public static Comparator<Student> sortProgram(boolean ascending) {
        return toAscending(Comparator.comparing(Student::getProgramID), ascending);
    }

    public static Comparator<Student> sortIdentifier(boolean ascending) {
        return toAscending(Comparator.comparing(Student::getIdentifier), ascending);
    }

    public static Comparator<Student> sortMoyenneThenName(boolean ascending) {
        return toAscending(Comparator.comparing(Student::moyenne).thenComparing(Student::getName), ascending);
    }

    public static Comparator<Student> sortCoursNote(String coursID, boolean ascending) {
        Comparator<Student> studentComparator = (student1, student2) -> {
            double noteS1 = student1.getNoteCours(coursID);
            double noteS2 = student2.getNoteCours(coursID);
            return Double.compare(noteS1, noteS2);
        };
        return toAscending(studentComparator, ascending);
    }

    public static Comparator<Student> toAscending(Comparator<Student> comparator, boolean ascending) {
        return ascending ? comparator : comparator.reversed();
    }
}
