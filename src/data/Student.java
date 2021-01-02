package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student  {
    private final Map<String, Double> notesMap;
    private final String identifier;
    private final String name;
    private final String surname;
    private final Program program;

    public Student(String identifier, String name, String surname, Program program) {
        this.identifier = identifier;
        this.name = name;
        this.surname = surname;
        this.program = program;
        this.notesMap = new HashMap<>();
    }

    public boolean equals(Object obj){
        boolean areEqual = false;
        if (obj instanceof Student student) {
            areEqual = identifier.equals(student.identifier);
        }
        return areEqual;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    public double moyenne() {
        double res = 0;
        int div = 0;
        for (BlocInterface bloc : program.getBlocs()) {
            for (Course course : bloc.getBlocCourses()) {
                if (!(notesMap.get(course.getID()) == null)) {
                    res += notesMap.get(course.getID()) * course.getCredit();
                    div += course.getCredit();
                }
            }
        }
        return res / div;
    }

    public double getNoteCours(String cours) {
        return notesMap.get(cours);
    }

    public Map<String, Double> getNotesMap() {
        return notesMap;
    }

    public Program getProgram() {
        return program;
    }

    public String getProgramID() {
        return program.getProgramID();
    }

   /* public void setProgram(Program newProgram) {
        program = newProgram;
    }

   /* public Program ajouterProgram(Program newProgram){
        for (Program program : programArrayList){
            if (!program.equals(newProgram)){
                programArrayList.add(newProgram);
            }
        }
        return newProgram;
    }*/

    public String getIdentifier() {
        return identifier;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }

    public String toString()
    {
        return String.format("\"%s\",\"%s\",\"%s\" ", identifier, name, surname);
    }

}
