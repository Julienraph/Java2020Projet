package gui;

import data.Bloc;
import data.Program;
import data.Student;
import data.StudentComparator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class CheckBlocs extends JFrame implements ActionListener {
    private final HashMap<String, JCheckBox> checkBoxHash = new HashMap<>();
    private final List<String> selectedBox = new ArrayList<>();
    private final JButton okButton;
    private final JButton cancelButton;
    private final Table table;
    private final int checkBoxNumber;
    private final JPanel checkBoxPanel;

    CheckBlocs(Table table, int checkBoxNumber) {
        this.table = table;
        this.checkBoxNumber = checkBoxNumber;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        // theses panel Button not seen in the right Layout
        JPanel panelButton = new JPanel((new FlowLayout(FlowLayout.RIGHT)));
        cancelButton = new JButton("CANCEL");
        okButton = new JButton("OK");
        cancelButton.addActionListener(this);
        okButton.addActionListener(this);

        panelButton.add(cancelButton, RIGHT_ALIGNMENT);
        panelButton.add(okButton, RIGHT_ALIGNMENT);

        if (checkBoxNumber == 0) {
            initCheckBlocs(gbc);
        }
        if (checkBoxNumber == 1) {
            initCheckProgram(gbc);
        }
        if (checkBoxNumber == 2) {
            initCheckStudent(gbc);
        }
        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setPreferredSize(new Dimension(400,450));
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(panelButton, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            for(Map.Entry<String, JCheckBox> entry : checkBoxHash.entrySet()) {
                String blocID = entry.getKey();
                JCheckBox checkBox = entry.getValue(); {
                    if (checkBox.isSelected()) {
                        selectedBox.add(blocID);
                    }
                }
            }
            if(!selectedBox.isEmpty()) {
                if (checkBoxNumber == 0) {table.checkBoxBlocs(selectedBox);}
                if (checkBoxNumber == 1) {table.checkBoxProgram(selectedBox); }
                if (checkBoxNumber == 2) {table.checkBoxStudent(selectedBox);}
            }
            this.dispose();
        }
        if(e.getSource() == cancelButton) {
            this.dispose();
        }
    }

    public JCheckBox createCheckBox(Bloc bloc) {
        String text = bloc.getID() + " - " + bloc.getName();
        return createCheckBox(bloc.getID(), text);
    }

    public JCheckBox createCheckBox(Program program) {
        String text = program.getProgramID() + " - " + program.getProgramName();
        return createCheckBox(program.getProgramID(), text);
    }

    public JCheckBox createCheckBox(Student student) {
        String  text = student.getName().toUpperCase() + " - " + student.getSurname() + " - " + student.getIdentifier();
        return createCheckBox(student.getIdentifier(), text);
    }

    public JCheckBox createCheckBox(String id, String text) {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setText(text);
        checkBox.setFocusable(false);
        checkBox.setFont(new Font("Consolas", Font.PLAIN, 10));
        checkBoxHash.put(id, checkBox);
        return checkBox;
    }

    public void initCheckBlocs(GridBagConstraints gbc) {
        for (Map.Entry<String, Bloc> entry : table.getXmlReader().getMapBlocs().entrySet()) {
            Bloc bloc = entry.getValue();
            checkBoxPanel.add(createCheckBox(bloc), gbc);
        }
    }

    public void initCheckProgram(GridBagConstraints gbc) {
        for (Map.Entry<String, Program> entry : table.getXmlReader().getMapProgram().entrySet()) {
            Program program = entry.getValue();
            checkBoxPanel.add(createCheckBox(program), gbc);
        }
    }

    public void initCheckStudent(GridBagConstraints gbc) {
        List<Student> listStudent = new ArrayList<Student>(table.getXmlReader().getMapStudent().values());
        Comparator<Student> comparator = StudentComparator.sortName(true);
        listStudent.sort(comparator);
        for (Student student : listStudent) {
            checkBoxPanel.add(createCheckBox(student), gbc);
        }
    }

}