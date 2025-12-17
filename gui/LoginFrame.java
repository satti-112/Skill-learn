package gui;

import models.Course;
import models.Student;
import models.User;

import persistence.DataManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginFrame extends JFrame implements ActionListener {
    private final JTextField loginUsernameField;
    private final JPasswordField loginPasswordField;

    private final JTextField registerUsernameField;
    private final JPasswordField registerPasswordField;
    private final JTextField registerEmailField;
    private final JComboBox<String> roleBox;
    private final JButton loginButton;
    private final JButton registerButton;

    private final ArrayList<User> users;
    private final ArrayList<Course> courses;
    private final DataManager dataManager;

    public LoginFrame(ArrayList<User> users, ArrayList<Course> courses, DataManager dataManager) {
        super("Skill Era - Login");
        this.users = users;
        this.courses = courses;
        this.dataManager = dataManager;

        loginUsernameField = new JTextField(15);
        loginPasswordField = new JPasswordField(15);

        registerUsernameField = new JTextField(15);
        registerPasswordField = new JPasswordField(15);
        registerEmailField = new JTextField(15);
        roleBox = new JComboBox<>(hasAdmin() ? new String[]{"Student"} : new String[]{"Student", "Admin"});
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        buildUi();
        attachEvents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 260);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildUi() {
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Login", buildLoginPanel());
        tabs.addTab("Register", buildRegisterPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        fieldsPanel.add(new JLabel("Username:"));
        fieldsPanel.add(loginUsernameField);
        fieldsPanel.add(new JLabel("Password:"));
        fieldsPanel.add(loginPasswordField);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(loginButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        fieldsPanel.add(new JLabel("Username:"));
        fieldsPanel.add(registerUsernameField);
        fieldsPanel.add(new JLabel("Password:"));
        fieldsPanel.add(registerPasswordField);
        fieldsPanel.add(new JLabel("Email:"));
        fieldsPanel.add(registerEmailField);
        fieldsPanel.add(new JLabel("Role:"));
        fieldsPanel.add(roleBox);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.add(registerButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void attachEvents() {
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == loginButton) {
            handleLogin();
        } else if (source == registerButton) {
            handleRegister();
        }
    }

    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        User user = findUser(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials or user not found.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Welcome, " + user.getUsername() + "!");
        openDashboard(user);
    }

    private void handleRegister() {
        String username = registerUsernameField.getText().trim();
        String password = new String(registerPasswordField.getPassword());
        String email = registerEmailField.getText().trim();
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill username, password, and email.");
            return;
        }

        if (findUser(username) != null) {
            JOptionPane.showMessageDialog(this, "Username already exists. Choose another.");
            return;
        }

        if ("Admin".equals(role)) {
            if (hasAdmin()) {
                JOptionPane.showMessageDialog(this, "An admin already exists. Choose Student role.");
                return;
            }
            users.add(new Student(username, password, email, true));
        } else {
            users.add(new Student(username, password, email));
        }

        dataManager.saveData(users, courses);
        JOptionPane.showMessageDialog(this, "Registration successful. You can now log in.");
        clearFields();
    }

    private User findUser(String username) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    private User findUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    private void openDashboard(User user) {
        dispose();
        if (user instanceof Student) {
            Student s = (Student) user;
            if (s.isAdmin()) {
                new AdminDashboard(s, users, courses, dataManager);
            } else {
                new StudentDashboard(s, courses, users, dataManager);
            }
        }
    }

    private void clearFields() {
        loginUsernameField.setText("");
        loginPasswordField.setText("");
        registerUsernameField.setText("");
        registerPasswordField.setText("");
        registerEmailField.setText("");
        roleBox.setSelectedIndex(0);
    }

    private boolean hasAdmin() {
        for (User u : users) {
            if (u instanceof Student && ((Student) u).isAdmin()) {
                return true;
            }
        }
        return false;
    }
}
