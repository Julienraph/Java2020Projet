package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Program {
    private final String programName;
    private final String programID;
    private final ArrayList<Bloc> blocs;

    public Program(String programName, String programID) {
        this.programName = programName;
        this.programID = programID;
        this.blocs = new ArrayList<>();
    }

    public void addBloc(Bloc bloc) {
        blocs.add(bloc);
    }

    public String getProgramName() {
        return programName;
    }

    public String getProgramID() {
        return programID;
    }

    public ArrayList<Bloc> getBlocs() {
        return blocs;
    }

    public ArrayList<Course> getCourse() {
        ArrayList<Course> listCourse = new ArrayList<>();
        for (Bloc bloc : blocs) {
            listCourse.addAll(bloc.getBlocCourses());
        }
        return listCourse;
    }

    public double getMoyenne(Student student) {
        double moyenne = 0;
        for(Bloc bloc : blocs) {
            double temp = bloc.getMoyenne(student);
            if(temp >= 0) {
                moyenne += temp;
            }
        }
        return moyenne / blocs.size();
    }

    public double getNoteMoyenne(List<Student> studentList) {
        int nbNotes = 0;
        double sommeNotes = 0;
        for(Student student : studentList) {
            if(student.getProgram().getProgramID().equals(this.getProgramID())) {
                double note = this.getMoyenne(student);
                if (note != -1) {
                    sommeNotes += note;
                    nbNotes += 1;
                }
            }
        }
        return sommeNotes / nbNotes;
    }
    /*duplication de code , cherher une méthode pour remédier à ce problème*/
    public double getNoteMax(List<Student> studentList) {
        double max = 0;
        for (int i = 0, studentListSize = studentList.size(); i < studentListSize; i++) {
            Student student = studentList.get(i);
            if (student.getProgram().getProgramID().equals(this.getProgramID())) {
                double moyenne = this.getMoyenne(student);
                if (moyenne > max && moyenne >= 0) {
                    max = moyenne;
                }
            }
        }
        return max;
    }

    public double getNoteMin(List<Student> studentList) {
        double min = 20;
        for(Student student : studentList) {
            if(student.getProgram().getProgramID().equals(this.getProgramID())) {
                double moyenne = this.getMoyenne(student);
                if (moyenne < min && moyenne >= 0) {
                    min = moyenne;
                }
            }
        }
        return min;
    }

    public double getEcartType(List<Student> studentList) {
        List<Double> listNote = new ArrayList<>();
        double moyenne = this.getNoteMoyenne(studentList);
        for(Student student : studentList) {
            if(student.getProgram().getProgramID().equals(this.getProgramID())) {
                double moyenneStudent = this.getMoyenne(student);
                if (moyenneStudent >= 0) {
                    listNote.add(moyenneStudent);
                }
            }
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
        if (obj instanceof Program) {
            areEqual = Objects.equals(this.programID,this.programName);
        }
        return areEqual;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.programID,this.programName);
    }

    @Override
    public String toString() {
        return programID + " " + programName +
                "\n" +
                blocs.toString() +
                "\n";
    }
}
