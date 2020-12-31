package data;
import java.util.*;

public class Data {
    static XMLReader xmlReader = new XMLReader();

    public static String[][] initData(List<TeachingUnit> blocs, List<Student> listStudent, Comparator comparator, boolean isProgram, boolean isDisplayStudent, List<Program> programList) {
        Collections.sort(listStudent, comparator);
        List<TeachingUnit> listCoursBlocs = blocs;
        String[][] data;

        /* Si j'affiche les étudiants, j'ajoute 4 lignes (pour Note Max, Note Min, Note Moyenne et Ecart-Type)
        Et j'ajoute 3 colonne pour l'ID, le nom et le prénom des étudiants */
        if(isDisplayStudent) {data = new String[listStudent.size()+4][listCoursBlocs.size()+3+programList.size()];}
        else {data = new String[listStudent.size()][listCoursBlocs.size()+programList.size()];}


        for (int i = 0; i < listStudent.size(); i++) {
            Student student = listStudent.get(i);
            int column = 0;
            if (isDisplayStudent) {
                data[i][0] = (String.format("%s", student.getIdentifier()));
                data[i][1] = (String.format("%s", student.getName()));
                data[i][2] = (String.format("%s", student.getSurname()));
                column += 3;
            }
            if(isProgram) {
                for (int k = 0; k < programList.size(); k++) {
                    if (programList.get(k).getProgramID().equals(getStudentProgram(student).getProgramID())) {
                        data[i][column+k] = (String.format("%.3f", getStudentProgram(student).getMoyenne(student)));
                    } else {data[i][column+k] = "";}
                }
                column += programList.size();
            }

            for (int j = 0; j < listCoursBlocs.size(); j++) {
                TeachingUnit teachingUnit = listCoursBlocs.get(j);
                double note = teachingUnit.getMoyenne(listStudent.get(i));
                if(note >= 0) {data[i][j+column] = String.format("%.3f", note);}
                if (note == -2) {data[i][j+column] = String.format("");}
                if (note == -1) {data[i][j+column] = String.format("ABI");}
            }
        }

        if (isDisplayStudent) {
            int i = listStudent.size();
            data[i][0] = "Note Maximum";
            data[i + 1][0] = "Note Minimum";
            data[i + 2][0] = "Note Moyenne";
            data[i + 3][0] = "Ecart-Type";

            // Enleve les valeurs null dans les cases vides :
            for (int k = 1; k < 3; k++) {
                data[i][k] = "";
                data[i + 1][k] = "";
                data[i + 2][k] = "";
                data[i + 3][k] = "";
            }
            int column = 3;
            if (isProgram) {
                for (int k = 0; k < programList.size(); k++) {
                    data[i][column + k] = String.format("%.3f", programList.get(k).getNoteMax(listStudent));
                    data[i + 1][column + k] = String.format("%.3f", programList.get(k).getNoteMin(listStudent));
                    data[i + 2][column + k] = String.format("%.3f", programList.get(k).getNoteMoyenne(listStudent));
                    data[i + 3][column + k] = String.format("%.3f", programList.get(k).getEcartType(listStudent));
                }
            }
            column = programList.size() + 3;
            for (int j = 0; j < listCoursBlocs.size(); j++) {
                TeachingUnit teachingUnit = listCoursBlocs.get(j);
                data[i][j + column] = String.format("%.3f", teachingUnit.getNoteMax(listStudent));
                data[i + 1][j + column] = String.format("%.3f", teachingUnit.getNoteMin(listStudent));
                data[i + 2][j + column] = String.format("%.3f", teachingUnit.getNoteMoyenne(listStudent));
                data[i + 3][j + column] = String.format("%.3f", teachingUnit.getEcartType(listStudent));
            }
        }
        return data;
    }


    /* Créer une liste de cours/blocs des programmes passé en paramètre
    L'objectif est ainsi d'avoir une liste qui contient les notes des cours/blocs à afficher de tous les programmes
     qu'on souhaite afficher
      */
    public static List<TeachingUnit> programToBlocs(List<Program> programList) {
        HashSet<AbstractTeachingUnit> listBlocCours = new HashSet<>();
        for(Program program : programList) {
            listBlocCours.addAll(initCoursProgram(program));
        }
        List<TeachingUnit> listBloc = new ArrayList<>(listBlocCours);
        return listBloc;
    }

    /* Créer une liste des cours/blocs des blocs passé en paramètre.
    Cette fonction à principalement comme objectif de dissocier les cours des bloc simples
    pour ne pas à avoir à afficher deux fois les mêmes cours (Un Bloc Simple est en quelque sorte un cours)
    Cette fonction est utilisé dans le cas où on veut afficher des blocs ("Choisir Blocs" dans l'interface)
     */
    public static List<TeachingUnit> BlocToList(List<Bloc> blocList) {
        HashSet<AbstractTeachingUnit> listBlocCours = new HashSet<>();
        for(Bloc bloc : blocList) {
            for(Course course : bloc.getBlocCourses()) {
                listBlocCours.add(course);
            }
            if(!(bloc.getClass() == BlocSimple.class)) {
                listBlocCours.add(bloc);
            }
        }
        List<TeachingUnit> listBloc = new ArrayList<>(listBlocCours);
        return listBloc;
    }



    /* Créer une liste de String qui sont le nom des colonnes
    Cette liste est créer selon ce qu'on veut afficher
     */
    public static String[] initColumnName(List<Program> programList, List<TeachingUnit> listCoursBlocs, boolean isDisplayStudent) {
        String[] columnName;
        int column = 0;
        if (isDisplayStudent) {
            columnName = new String[listCoursBlocs.size() + 3 + programList.size()];
            columnName[0] = "ID";
            columnName[1] = "Name";
            columnName[2] = "Surname";
            column += 3;
        } else {columnName = new String[listCoursBlocs.size() + programList.size()];}
        for(int k = 0; k < programList.size(); k++) {
            columnName[column] = (String.format("%s - %s", programList.get(k).getProgramID(), programList.get(k).getProgramName()));
            column += 1;
        }
        for (int i = 0; i < listCoursBlocs.size(); i++) {
            if (!(listCoursBlocs.get(i).getClass() == BlocSimple.class)) {
                columnName[i + column] = (String.format("%s - %s", listCoursBlocs.get(i).getName(), listCoursBlocs.get(i).getID()));
            }
        }
        for(int i = 0; i < columnName.length; i++) {
            System.out.println(columnName[i]);
        }
        return columnName;
    }


    /* Fonction intermédiaire de ProgramToBlocs
    Créer une liste de cours/blocs DU programme passé en paramètre
     */
    public static List<AbstractTeachingUnit> initCoursProgram(Program program) {
        List<AbstractTeachingUnit> listBlocCours = new ArrayList<>();
        for(Bloc bloc : program.getBlocs()) {
            for(Course course : bloc.getBlocCourses()) {
                listBlocCours.add(course);
            }
            if(!(bloc.getClass() == BlocSimple.class)) {
                listBlocCours.add(bloc);
            }
        }
        return listBlocCours;
    }

    /* Créer une liste des cours suivis par les étudiants passé en paramètre.
    Cette fonction est utilisé dans le cas où on veut afficher les notes d'un/plusieurs étudiants
    ("Choisir Student" dans l'interface)
     */
    public static List<TeachingUnit> initCoursStudent(List<Student> listStudent) {
        HashSet<AbstractTeachingUnit> listBlocCours = new HashSet<>();
        for(Student student : listStudent) {
            for (Map.Entry<String, Double> entry : student.getNotesMap().entrySet()) {
                String courseID = entry.getKey();
                listBlocCours.add(xmlReader.getMapCourse().get(courseID));
            }
        }
        List<TeachingUnit> listBloc = new ArrayList<>(listBlocCours);
        return listBloc;
    }

    /* Prend la liste complète des étudiants de l'Université et filtre cette liste pour avoir uniquement
     les étudiants qui suivent un des programmes de la liste passé en paramètre
   */
    public static List<Student> initListStudents(List<Student> listStudent, List<Program> listProgram) {
        List<Student> listStudentProgram = new ArrayList<>();
        for(Program program : listProgram) {
            for (Student student : listStudent) {
                if (student.getProgramID().equals(program.getProgramID())) {
                    listStudentProgram.add(student);
                }
            }
        }
        return listStudentProgram;
    }

    /* Prend la liste complète des étudiants de l'Université et filtre cette liste pour avoir uniquement
      les étudiants qui suivent au moins un cours de la liste passé en paramètre.
  */
    public static List<Student> initListStudentsBlocs(List<Student> listStudent, List<TeachingUnit> ListUnit) {
        List<Student> listStudentUnit = new ArrayList<>();
            for (Student student : listStudent) {
                boolean hasNote = false;
                for(TeachingUnit teachingUnit : ListUnit) {
                    if (student.getNotesMap().containsKey(teachingUnit.getID())) {
                        hasNote = true;
                    }
                }
                if(hasNote) {
                    listStudentUnit.add(student);
                }
        }
        return listStudentUnit;
    }

    public static Program getStudentProgram(Student student) {
        return xmlReader.getMapProgram().get(student.getProgramID());
    }
}
