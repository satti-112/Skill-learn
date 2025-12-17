package gui;

import models.Course;
import models.QuizQuestion;
import models.Student;
import models.User;
import persistence.DataBundle;
import persistence.DataManager;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class StudentDashboard extends JFrame implements ActionListener {
    private final Student student;
    private final ArrayList<Course> courses;
    private final ArrayList<User> users;
    private final DataManager dataManager;

    private final JList<Course> courseList;
    private final DefaultListModel<Course> courseListModel;
    private final JTextArea courseDetails;
   
    private final JButton watchVideosButton;
    private final JButton takeQuizButton;
    private final JButton submitAssignmentButton;
    private final JButton viewProgressButton;
    private final JButton viewCertificateButton;
    private final JButton viewCompletedButton;
    private final JButton browseCoursesButton;

    public StudentDashboard(Student student, ArrayList<Course> courses, ArrayList<User> users, DataManager dataManager) {
        super("Skill Era - Student");
        this.student = student;
        this.courses = courses;
        this.users = users;
        this.dataManager = dataManager;

        courseListModel = new DefaultListModel<>();
        for (Course c : student.getEnrolledCourses()) {
            courseListModel.addElement(c);
        }
        courseList = new JList<>(courseListModel);
        courseDetails = new JTextArea(12, 40);
        courseDetails.setEditable(false);

        watchVideosButton = new JButton("Watch Videos");
        takeQuizButton = new JButton("Take Quiz");
        submitAssignmentButton = new JButton("Submit Assignment");
        viewProgressButton = new JButton("View Progress");
        viewCertificateButton = new JButton("View Certificate");
        viewCompletedButton = new JButton("My Completed Courses");
        browseCoursesButton = new JButton("Browse All Courses");

        buildUi();
        attachEvents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(840, 520);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUi() {
        setLayout(new BorderLayout(10, 10));
        add(new JLabel("Welcome, " + student.getUsername() + "!"), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.add(new JScrollPane(courseList), BorderLayout.WEST);
        center.add(new JScrollPane(courseDetails), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        actions.add(browseCoursesButton);
        actions.add(watchVideosButton);
        actions.add(takeQuizButton);
        actions.add(submitAssignmentButton);
        actions.add(viewProgressButton);
        actions.add(viewCertificateButton);
        actions.add(viewCompletedButton);
        add(actions, BorderLayout.SOUTH);
    }

    private void attachEvents() {
        browseCoursesButton.addActionListener(this);
        watchVideosButton.addActionListener(this);
        takeQuizButton.addActionListener(this);
        submitAssignmentButton.addActionListener(this);
        viewProgressButton.addActionListener(this);
        viewCertificateButton.addActionListener(this);
        viewCompletedButton.addActionListener(this);
        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showCourse(courseList.getSelectedValue());
            }
        });
    }

    private void showCourse(Course c) {
        if (c == null) {
            courseDetails.setText("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("═════════════════════════════════════════\n");
        sb.append(c.getCourseCode()).append(" - ").append(c.getCourseTitle()).append("\n");
        sb.append("Instructor: ").append(c.getInstructorName()).append("\n");
        sb.append("═════════════════════════════════════════\n\n");
        
        sb.append("📹 VIDEO LECTURES (" + c.getVideoLinks().size() + "):\n");
        sb.append("─────────────────────────────────────────\n");
        int idx = 1;
        for (String v : c.getVideoLinks()) {
            sb.append("  Video ").append(idx++).append(": ").append(v).append("\n");
        }
        
        sb.append("\n📝 QUIZ QUESTIONS (" + c.getQuizQuestions().size() + "):\n");
        sb.append("─────────────────────────────────────────\n");
        int qn = 1;
        for (QuizQuestion q : c.getQuizQuestions()) {
            sb.append("  Q").append(qn++).append(". ").append(q.getQuestion()).append("\n");
            List<String> opts = q.getOptions();
            for (int i = 0; i < opts.size(); i++) {
                sb.append("      ").append(i + 1).append(") ").append(opts.get(i)).append("\n");
            }
        }
        
        sb.append("\n📄 ASSIGNMENTS (" + c.getAssignmentPrompts().size() + "):\n");
        sb.append("─────────────────────────────────────────\n");
        idx = 1;
        for (String a : c.getAssignmentPrompts()) {
            sb.append("  Assignment ").append(idx++).append(": ").append(a).append("\n");
        }
        sb.append("═════════════════════════════════════════\n");
        courseDetails.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        Course selected = courseList.getSelectedValue();
        if (src == browseCoursesButton) {
            browseCourses();
        } else if (src == watchVideosButton) {
            watchVideos(selected);
        } else if (src == takeQuizButton) {
            takeQuiz(selected);
        } else if (src == submitAssignmentButton) {
            submitAssignment(selected);
        } else if (src == viewProgressButton) {
            viewProgress(selected);
        } else if (src == viewCertificateButton) {
            viewCertificate(selected);
        } else if (src == viewCompletedButton) {
            viewCompletedCourses();
        }
    }

    private void browseCourses() {
    // Reload latest data from file to show newly created courses
    DataBundle latestData = dataManager.loadData();
    if (latestData != null && latestData.getCourses() != null) {
        courses.clear();
        courses.addAll(latestData.getCourses());
    }
    
    // Create custom dialog
    JDialog dialog = new JDialog(this, "Browse & Enroll in Courses", true);
    dialog.setLayout(new BorderLayout(10, 10));
    
    DefaultListModel<Course> allCoursesModel = new DefaultListModel<>();
    for (Course c : courses) {
        if (!student.getEnrolledCourses().contains(c)) {
            allCoursesModel.addElement(c);
        }
    }
    
    if (allCoursesModel.isEmpty()) {
        JOptionPane.showMessageDialog(this, "You are already enrolled in all available courses!");
        return;
    }
    
    JList<Course> allCoursesList = new JList<>(allCoursesModel);
    
    // Main panel
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(new JLabel("Select a course to enroll:"), BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(allCoursesList), BorderLayout.CENTER);
    
    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton enrollButton = new JButton("Enroll");
    JButton cancelButton = new JButton("Cancel");
    
    // Initially disable enroll button
    enrollButton.setEnabled(false);
    
    // Enable enroll button when a course is selected
    allCoursesList.addListSelectionListener(e -> {
        enrollButton.setEnabled(allCoursesList.getSelectedValue() != null);
    });
    
    // Enroll button action
    enrollButton.addActionListener(e -> {
        Course selected = allCoursesList.getSelectedValue();
        if (selected != null) {
            // Enroll student
            student.enrollInCourse(selected);
            courseListModel.addElement(selected);
            dataManager.saveData(users, courses);
            
            // Show success message
            JOptionPane.showMessageDialog(dialog, 
                "✅ Successfully enrolled in: " + selected.getCourseTitle(),
                "Enrollment Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Close the dialog
            dialog.dispose();
            
            // Auto-select the newly enrolled course
            courseList.setSelectedValue(selected, true);
        }
    });
    
    // Cancel button action
    cancelButton.addActionListener(e -> {
        dialog.dispose();
    });
    
    buttonPanel.add(enrollButton);
    buttonPanel.add(cancelButton);
    
    // Add components to dialog
    dialog.add(mainPanel, BorderLayout.CENTER);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    
    // Set dialog properties
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

    private void watchVideos(Course c) {
        if (!ensureEnrolled(c)) return;
        if (c.getVideoLinks().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No videos available.");
            return;
        }
        
        // Create panel with individual video buttons
        JPanel videoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        videoPanel.add(new JLabel("Select a video to watch:"));
        
        List<String> videos = c.getVideoLinks();
        for (int i = 0; i < videos.size(); i++) {
            final int index = i;
            final String url = videos.get(i);
            JButton videoBtn = new JButton("Video " + (i + 1));
            videoBtn.addActionListener(e -> {
                openLink(url);
                JOptionPane.showMessageDialog(this, "Opening Video " + (index + 1) + " in browser");
            });
            videoPanel.add(videoBtn);
        }
        
        JButton markDoneBtn = new JButton("Mark All Videos as Watched (33% Progress)");
        markDoneBtn.addActionListener(e -> {
            student.updateProgress(c.getCourseCode(), 33, false, false, true);
            dataManager.saveData(users, courses);
            JOptionPane.showMessageDialog(this, "All videos marked as watched! Progress: 33%");
        });
        videoPanel.add(markDoneBtn);
        
        JOptionPane.showMessageDialog(this, videoPanel, "Watch Videos - " + c.getCourseTitle(), JOptionPane.PLAIN_MESSAGE);
    }

    private void takeQuiz(Course c) {
        if (!ensureEnrolled(c)) return;
        if (c.getQuizQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No quiz for this course.");
            return;
        }
        int correct = 0;
        for (QuizQuestion q : c.getQuizQuestions()) {
            StringBuilder prompt = new StringBuilder(q.getQuestion()).append("\n");
            List<String> opts = q.getOptions();
            for (int i = 0; i < opts.size(); i++) {
                prompt.append(i + 1).append(") ").append(opts.get(i)).append("\n");
            }
            String answer = JOptionPane.showInputDialog(this, prompt.toString(), "1");
            int chosen = -1;
            try {
                chosen = Integer.parseInt(answer.trim()) - 1;
            } catch (Exception ignored) {}
            if (chosen == q.getCorrectIndex()) {
                correct++;
            }
        }
        JOptionPane.showMessageDialog(this, "Quiz complete. Score: " + correct + "/" + c.getQuizQuestions().size());
        student.updateProgress(c.getCourseCode(), 66, true, false, false);
        dataManager.saveData(users, courses);
    }


private void submitAssignment(Course c) {
    if (!ensureEnrolled(c)) return;
    if (c.getAssignmentPrompts().isEmpty()) {
        JOptionPane.showMessageDialog(this, "No assignments posted.");
        return;
    }
    
    // Create a better UI for assignment submission
    JPanel panel = new JPanel(new BorderLayout());
    
    // Show assignment prompts
    JTextArea promptsArea = new JTextArea(6, 40);
    promptsArea.setEditable(false);
    promptsArea.setText("Assignment Prompts:\n");
    for (int i = 0; i < c.getAssignmentPrompts().size(); i++) {
        promptsArea.append("\n" + (i+1) + ". " + c.getAssignmentPrompts().get(i) + "\n");
    }
    
    // Text area for student's answer
    JTextArea answerArea = new JTextArea(10, 40);
    answerArea.setLineWrap(true);
    answerArea.setWrapStyleWord(true);
    
    panel.add(new JScrollPane(promptsArea), BorderLayout.NORTH);
    panel.add(new JLabel("Your Answer:"), BorderLayout.CENTER);
    panel.add(new JScrollPane(answerArea), BorderLayout.SOUTH);
    
    int res = JOptionPane.showConfirmDialog(this, panel, "Submit Assignment - " + c.getCourseTitle(), 
                                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (res == JOptionPane.OK_OPTION) {
        String answer = answerArea.getText().trim();
        if (answer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Answer cannot be empty!");
            return;
        }
        
        // Save assignment to file
        saveAssignmentToFile(student, c, answer);
        
        // Update progress
        student.updateProgress(c.getCourseCode(), 100, false, true, false);
        dataManager.saveData(users, courses);
        
        JOptionPane.showMessageDialog(this, "Assignment submitted successfully!\nProgress updated to 100%.");
    }
}

private void saveAssignmentToFile(Student student, Course course, String answer) {
    try {
        // Create a unique filename for each assignment submission
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String filename = "assignments/" + student.getUsername() + "_" + 
                         course.getCourseCode() + "_" + timestamp + ".txt";
        
        // Create assignments directory if it doesn't exist
        File dir = new File("assignments");
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("╔══════════════════════════════════════════════════╗");
            writer.println("║              ASSIGNMENT SUBMISSION               ║");
            writer.println("╚══════════════════════════════════════════════════╝\n");
            
            writer.println("STUDENT INFORMATION:");
            writer.println("────────────────────");
            writer.println("Name: " + student.getUsername());
            writer.println("Email: " + student.getEmail());
            writer.println("\n");
            
            writer.println("COURSE INFORMATION:");
            writer.println("───────────────────");
            writer.println("Course Code: " + course.getCourseCode());
            writer.println("Course Title: " + course.getCourseTitle());
            writer.println("Instructor: " + course.getInstructorName());
            writer.println("\n");
            
            writer.println("ASSIGNMENT PROMPTS:");
            writer.println("────────────────────");
            for (int i = 0; i < course.getAssignmentPrompts().size(); i++) {
                writer.println((i+1) + ". " + course.getAssignmentPrompts().get(i));
            }
            writer.println("\n");
            
            writer.println("STUDENT'S ANSWER:");
            writer.println("─────────────────");
            writer.println(answer);
            writer.println("\n");
            
            writer.println("SUBMISSION DETAILS:");
            writer.println("───────────────────");
            writer.println("Submission Date: " + new java.util.Date());
            writer.println("Submission ID: " + System.currentTimeMillis());
            writer.println("File: " + filename);
            
            writer.println("\n╔══════════════════════════════════════════════════╗");
            writer.println("║                 END OF SUBMISSION                 ║");
            writer.println("╚══════════════════════════════════════════════════╝");
            
            System.out.println("Assignment saved to: " + filename);
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving assignment file!");
    }
}
    private void viewProgress(Course c) {
        if (!ensureEnrolled(c)) return;
        Student.Progress p = student.getProgress(c.getCourseCode());
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No progress yet.");
            return;
        }
        JOptionPane.showMessageDialog(this,
                "Video done: " + p.isVideoCompleted() + "\n" +
                        "Quiz done: " + p.isQuizCompleted() + "\n" +
                        "Assignment done: " + p.isAssignmentSubmitted() + "\n" +
                        "Progress: " + p.getProgressPercent() + "%");
    }

    private void viewCertificate(Course c) {
        if (!ensureEnrolled(c)) return;
        Student.Progress p = student.getProgress(c.getCourseCode());
        if (p == null || p.getProgressPercent() < 100) {
            JOptionPane.showMessageDialog(this, "Complete all parts to view certificate.");
            return;
        }
        if (p.getCertificateId() == null) {
            p.setCertificateId(generateCertificateId());
            dataManager.saveData(users, courses);
        }
        String certificate = "═══════════════════════════════════════════════════\n" +
                             "           CERTIFICATE OF COMPLETION\n" +
                             "═══════════════════════════════════════════════════\n\n" +
                             "  This certifies that\n\n" +
                             "  " + student.getUsername().toUpperCase() + "\n" +
                             "  Email: " + student.getEmail() + "\n\n" +
                             "  has successfully completed the course\n\n" +
                             "  " + c.getCourseTitle() + "\n" +
                             "  (" + c.getCourseCode() + ")\n\n" +
                             "  Instructor: " + c.getInstructorName() + "\n\n" +
                             "  Verification ID: " + p.getCertificateId() + "\n\n" +
                             "═══════════════════════════════════════════════════";
        JOptionPane.showMessageDialog(this, certificate, "Certificate - " + c.getCourseTitle(), JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String generateCertificateId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * chars.length());
            id.append(chars.charAt(index));
        }
        return id.toString();
    }
    
    private void viewCompletedCourses() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════╗\n");
        sb.append("║     MY COMPLETED COURSES (100%)      ║\n");
        sb.append("╚══════════════════════════════════════╝\n\n");
        
        int count = 0;
        for (Course c : student.getEnrolledCourses()) {
            Student.Progress p = student.getProgress(c.getCourseCode());
            if (p != null && p.getProgressPercent() == 100) {
                count++;
                sb.append("✓ ").append(c.getCourseCode()).append(" - ").append(c.getCourseTitle()).append("\n");
                sb.append("  Certificate ID: ").append(p.getCertificateId() != null ? p.getCertificateId() : "Not generated").append("\n\n");
            }
        }
        
        if (count == 0) {
            sb.append("No completed courses yet.\n");
            sb.append("Complete videos, quiz, and assignment to finish a course.");
        } else {
            sb.append("\nTotal Completed: ").append(count).append(" course(s)");
        }
        
        JOptionPane.showMessageDialog(this, sb.toString(), "Completed Courses", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean ensureEnrolled(Course c) {
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Select a course first.");
            return false;
        }
        if (!student.getEnrolledCourses().contains(c)) {
            JOptionPane.showMessageDialog(this, "Please enroll first.");
            return false;
        }
        return true;
    }

    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception ignored) {
            JOptionPane.showMessageDialog(this, "Could not open link.");
        }
    }
}
