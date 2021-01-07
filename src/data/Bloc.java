package data;
import java.util.*;

public abstract class Bloc extends AbstractTeachingUnit implements BlocInterface, TeachingUnit {
    private final String blocName;
    private final String blocID;
    private int blocCredits = 0;
    private final HashSet<Course> blocCourses;

    public Bloc(String blocName, String blocID) {
        super(blocName, blocID);
        this.blocCourses = new HashSet<>();
        this.blocName = blocName;
        this.blocID = blocID;
    }

    @Override
    public void calculCredits() {
        int res = 0;
        if (this.getClass() == BlocSimple.class || this.getClass() == BlocComposite.class) {
            for (Course course : blocCourses) {
                res += course.getCredit();
            }
        } else {
            List<Course> listCourse = new ArrayList<>(blocCourses);
            if (!listCourse.isEmpty()) {
                res = listCourse.get(0).getCredit();
            }
        }
        blocCredits = res;
    }

    @Override
    public void addCourses(Course course) {
        if (this.getClass() == BlocSimple.class && this.blocCourses.size() == 1) {
            blocCourses.add(course);
        } else {
            blocCourses.add(course);
        }
        calculCredits();
    }

    public HashSet<Course> getBlocCourses() {
        return blocCourses;
    }

    /*Calcul de la moyenne de chaque étudiant pour un cours donné */
    public double getMoyenne(Student student) {
        double res = 0;
        double absent = -1;
        double hasStudent = 0;
        for (Course course : blocCourses) {
            double note = course.getMoyenne(student);
            if (note >= 0) {
                res += note * course.getCredit();
                absent = 1;
            }
            hasStudent += note;
        }
        if(hasStudent == (-2 * blocCourses.size())) {
            return -2;
        }
        if(absent == -1) {
            return absent;
        }
        return res / this.blocCredits;
    }
    /*Calcul de la moyenne générale d'une liste d'étudiant */
    public double getNoteMoyenne(List<Student> studentList) {
        int nbNotes = 0;
        double sommeNotes = 0;
        for (Student student : studentList) {
            double note = this.getMoyenne(student);
            if(note != -1) {
                sommeNotes += note;
                nbNotes += 1;
            }
        }
        return sommeNotes / nbNotes;
    }
    /*Calcul de la note maximal pour une liste  d"étudiant  */
    public double getNoteMax(List<Student> studentList) {
        double max = 0;
        for (Student student : studentList) {
            double moyenne = this.getMoyenne(student);
            if(moyenne > max) {
                max = moyenne;
            }
        }
        return max;
    }
    /*Calcul de la note minimal pour une liste  d"étudiant  */
    public double getNoteMin(List<Student> studentList) {
        double min = 20;
        for (Student student : studentList) {
            double moyenne = this.getMoyenne(student);
            if(moyenne < min && moyenne >= 0) {
                min = moyenne;
            }
        }
        return min;
    }
    /*Calcul de l'écart-type */
    public double getEcartType(List<Student> studentList) {
        List<Double> listNote = new ArrayList<>();
        double moyenne = this.getNoteMoyenne(studentList);
        for(Student student : studentList) {
            double moyenneStudent = this.getMoyenne(student);
            if(moyenneStudent >= 0)
            {listNote.add(moyenneStudent);}
        }
        double acc = 0;
        for (Double note : listNote)
        {
            if(note >= 0) {
                double squrDiffToMean = Math.pow(note - moyenne, 2);
                acc += squrDiffToMean;
            }
        }
        double meanOfDiffs = acc / listNote.size();
        return Math.sqrt(meanOfDiffs);
    }
    @Override
    public boolean equals(Object obj) {
        boolean areEqual = false;
        if (obj instanceof Bloc bloc){
            areEqual = blocID.equals(bloc.blocID) && blocName.equals(bloc.blocName);
        }
        return areEqual;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), blocName, blocID);
    }

    @Override
    public String toString() {
        return "blocName='" + blocName + '\'' +
                ", blocID='" + blocID + '\'' +
                ", blocCredits=" + blocCredits +
                ", blocCourses=" + blocCourses + "\n";
    }
}
