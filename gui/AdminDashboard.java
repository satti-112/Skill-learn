package gui;

import models.Course;
import models.QuizQuestion;
import models.Student;
import models.User;
import persistence.DataManager;

import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminDashboard extends JFrame implements ActionListener {
    private final ArrayList<User> users;
    private final ArrayList<Course> courses;
    private final DataManager dataManager;

    private final JTextField codeField;
    private final JTextField titleField;
    private final JTextField instructorField;
    private final JTextArea videosArea;
    private final JTextArea quizArea;
    private final JTextArea assignmentsArea;
    private final JTextField certificateField;
    private final JButton addCourseButton;

    private final DefaultListModel<Course> courseListModel;
    private final JList<Course> courseList;
    private final DefaultListModel<Student> studentListModel;
    private final JList<Student> studentList;
    private final JButton viewEnrolledButton;
    private final JButton deleteStudentButton;

    public AdminDashboard(Student admin, ArrayList<User> users, ArrayList<Course> courses, DataManager dataManager) {
        super("Skill Era - Admin");
        this.users = users;
        this.courses = courses;
        this.dataManager = dataManager;

        codeField = new JTextField();
        titleField = new JTextField();
        instructorField = new JTextField("Instructor Name");
        videosArea = new JTextArea("https://youtu.be/\nhttps://youtu.be/", 5, 24);
        quizArea = new JTextArea("What is Java?|A language|A coffee|An island|1\nWhat is OOP?|Procedural|Object Oriented|Functional|2\nJava is?|Compiled|Interpreted|Both|3", 8, 24);
        assignmentsArea = new JTextArea("Assignment prompt 1\nAssignment prompt 2", 4, 24);
        certificateField = new JTextField("Certificate issued on completion");
        addCourseButton = new JButton("Create Course");

        courseListModel = new DefaultListModel<>();
        for (Course c : courses) {
            courseListModel.addElement(c);
        }
        courseList = new JList<>(courseListModel);

        studentListModel = new DefaultListModel<>();
        for (User u : users) {
            if (u instanceof Student && !((Student) u).isAdmin()) {
                studentListModel.addElement((Student) u);
            }
        }
        studentList = new JList<>(studentListModel);
        viewEnrolledButton = new JButton("View Enrolled In Course");
        deleteStudentButton = new JButton("Delete Selected Student");

        buildUi();
        attachEvents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 560);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(6, 1, 6, 6));
        form.add(labeled("Course Code", codeField));
        form.add(labeled("Course Title", titleField));
        form.add(labeled("Instructor", instructorField));
        form.add(labeledArea("Videos (one URL per line)", videosArea));
        form.add(labeledArea("Quiz (Format: Question|Option1|Option2|Option3|CorrectAnswer[1-3])", quizArea));
        form.add(labeledArea("Assignments (two lines)", assignmentsArea));

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.add(new JLabel("Certificate Note:"));
        south.add(certificateField);
        south.add(addCourseButton);

        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(form, BorderLayout.CENTER);
        formWrapper.add(south, BorderLayout.SOUTH);

        JPanel right = new JPanel(new BorderLayout(6, 6));
        right.add(new JLabel("Courses"), BorderLayout.NORTH);
        right.add(new JScrollPane(courseList), BorderLayout.CENTER);

        JPanel studentsPanel = new JPanel(new BorderLayout(6, 6));
        studentsPanel.add(new JLabel("Students"), BorderLayout.NORTH);
        studentsPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);
        JPanel studentButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        studentButtons.add(viewEnrolledButton);
        studentButtons.add(deleteStudentButton);
        studentsPanel.add(studentButtons, BorderLayout.SOUTH);
        right.add(studentsPanel, BorderLayout.SOUTH);

        add(formWrapper, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);
    }

    private JPanel labeled(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label + ":"), BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JPanel labeledArea(String label, JTextArea area) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(new JLabel(label + ":"), BorderLayout.NORTH);
        p.add(new JScrollPane(area), BorderLayout.CENTER);
        return p;
    }

    private void attachEvents() {
        addCourseButton.addActionListener(this);
        viewEnrolledButton.addActionListener(this);
        deleteStudentButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == addCourseButton) {
            handleAddCourse();
        } else if (src == viewEnrolledButton) {
            handleViewEnrolled();
        } else if (src == deleteStudentButton) {
            handleDeleteStudent();
        }
    }

    private void handleAddCourse() {
        String code = codeField.getText().trim();
        String title = titleField.getText().trim();
        String instructor = instructorField.getText().trim();
        List<String> videos = parseLines(videosArea.getText());
        List<QuizQuestion> quiz = parseQuiz(quizArea.getText());
        List<String> assignments = parseLines(assignmentsArea.getText());
        String cert = certificateField.getText().trim();

        if (code.isEmpty() || title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Code and title are required.");
            return;
        }
        
        // Validate at least one video and one quiz
        if (videos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one video URL.");
            return;
        }
        if (quiz.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one quiz question.");
            return;
        }
        
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                JOptionPane.showMessageDialog(this, "Course code already exists.");
                return;
            }
        }

        Course course = new Course(code, title, instructor.isEmpty() ? "Instructor" : instructor,
                videos, quiz, assignments, cert.isEmpty() ? "Certificate on completion" : cert);
        courses.add(course);
        courseListModel.addElement(course);
        dataManager.saveData(users, courses);
        System.out.println("✓ Course saved: " + code + " with " + videos.size() + " videos, " + quiz.size() + " questions");
        JOptionPane.showMessageDialog(this, "Course created successfully!\n" + 
                                     "Videos: " + videos.size() + "\n" +
                                     "Quiz: " + quiz.size() + "\n" +
                                     "Assignments: " + assignments.size());
        clearForm();
    }

    private void handleDeleteStudent() {
        Student s = studentList.getSelectedValue();
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Select a student to delete.");
            return;
        }
        users.remove(s);
        studentListModel.removeElement(s);
        dataManager.saveData(users, courses);
        JOptionPane.showMessageDialog(this, "Removed student " + s.getUsername());
    }

    private void handleViewEnrolled() {
        Student s = studentList.getSelectedValue();
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Select a student to view.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(s.getUsername()).append(" is enrolled in:\n");
        for (Course c : s.getEnrolledCourses()) {
            sb.append("- ").append(c.getCourseCode()).append(": ").append(c.getCourseTitle()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private List<String> parseLines(String text) {
        List<String> lines = new ArrayList<>();
        for (String line : text.split("\n")) {
            if (!line.trim().isEmpty()) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    private List<QuizQuestion> parseQuiz(String text) {
        List<QuizQuestion> list = new ArrayList<>();
        String[] rows = text.split("\n");
        for (String row : rows) {
            String[] parts = row.split("\\|");
            if (parts.length >= 5) {
                String q = parts[0].trim();
                List<String> opts = Arrays.asList(parts[1].trim(), parts[2].trim(), parts[3].trim());
                int correct = 0;
                try {
                    int userAnswer = Integer.parseInt(parts[4].trim());
                    if (userAnswer >= 1 && userAnswer <= 3) {
                        correct = userAnswer - 1; // convert 1-based to 0-based
                    } else {
                        System.err.println("Invalid answer " + userAnswer + " for question: " + q + ". Using 1 as default.");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse answer for question: " + q + ". Using 1 as default.");
                }
                list.add(new QuizQuestion(q, opts, correct));
            }
        }
        return list;
    }

    private void clearForm() {
        codeField.setText("");
        titleField.setText("");
        instructorField.setText("Instructor Name");
        videosArea.setText("https://youtu.be/\nhttps://youtu.be/");
        quizArea.setText("What is Java?|A language|A coffee|An island|1\nWhat is OOP?|Procedural|Object Oriented|Functional|2\nJava is?|Compiled|Interpreted|Both|3");
        assignmentsArea.setText("Assignment prompt 1\nAssignment prompt 2");
        certificateField.setText("Certificate issued on completion");
    }
}
