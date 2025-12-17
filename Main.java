import gui.LoginFrame;
import models.Course;
import models.QuizQuestion;
import models.Student;
import models.User;
import persistence.DataManager;
import persistence.DataBundle;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataManager dataManager = new DataManager();
            DataBundle bundle = dataManager.loadData();

            ArrayList<User> users = bundle.getUsers();
            ArrayList<Course> courses = bundle.getCourses();

            if (users == null) {
                users = new ArrayList<>();
            }
            if (courses == null) {
                courses = new ArrayList<>();
            }

            seedSampleData(users, courses, dataManager);

            new LoginFrame(users, courses, dataManager);
        });
    }

    private static void seedSampleData(ArrayList<User> users, ArrayList<Course> courses, DataManager dataManager) {
        if (users.isEmpty()) {
            Student demoStudent = new Student("student", "1234", "student@skillera.com");
            Student admin = new Student("admin", "admin", "admin@skillera.com", true);
            users.add(demoStudent);
            users.add(admin);
        }

        // Only seed demo courses if no courses exist yet
        // This preserves admin-created courses!
        if (courses.isEmpty()) {
            courses.add(buildCourse("WD101", "Web Development", "Alice Smith", webVideos(), webQuiz(), Arrays.asList("Build a landing page", "Deploy static site")));
            courses.add(buildCourse("PF102", "Programming Fundamentals", "Bob Jones", pfVideos(), pfQuiz(), Arrays.asList("FizzBuzz in Java", "Arrays practice")));
            courses.add(buildCourse("AI201", "Artificial Intelligence", "Carol White", aiVideos(), aiQuiz(), Arrays.asList("Search problem write-up", "Heuristic design")));
            courses.add(buildCourse("BI210", "Power BI Essentials", "David Lee", biVideos(), biQuiz(), Arrays.asList("Create a dashboard", "Publish a report")));
            courses.add(buildCourse("ENG150", "IELTS Preparation", "Emma Brown", ieltsVideos(), ieltsQuiz(), Arrays.asList("Writing task practice", "Speaking mock")));
            courses.add(buildCourse("JV301", "Java Programming", "Frank Green", javaVideos(), javaQuiz(), Arrays.asList("Console banking app", "Collections exercise")));
            dataManager.saveData(users, courses);
            System.out.println("Seeded 6 demo courses");
        } else {
            System.out.println("Loaded " + courses.size() + " courses from file (including admin-created courses)");
        }
    }

    private static Course buildCourse(String code, String title, String instructor, List<String> videos, List<QuizQuestion> quiz, List<String> assignments) {
        return new Course(code, title, instructor, videos, quiz, assignments, "Certificate issued on completion");
    }

    private static List<String> webVideos() {
        return Arrays.asList(
                "https://www.youtube.com/watch?v=nu_pCVPKzTk",
                "https://www.youtube.com/watch?v=pQN-pnXPaVg",
                "https://www.youtube.com/watch?v=PlxWf493en4",
                "https://www.youtube.com/watch?v=FzG4uDgje3M",
                "https://www.youtube.com/watch?v=1Rs2ND1ryYc",
                "https://www.youtube.com/watch?v=3JluqTojuME"
        );
    }

    private static List<String> pfVideos() {
        return Arrays.asList(
                "https://www.youtube.com/watch?v=rfscVS0vtbw",
                "https://www.youtube.com/watch?v=kqtD5dpn9C8",
                "https://www.youtube.com/watch?v=HXV3zeQKqGY",
                "https://www.youtube.com/watch?v=8PopR3x-VMY",
                "https://www.youtube.com/watch?v=RBSGKlAvoiM",
                "https://www.youtube.com/watch?v=zg9ih6SVACc"
        );
    }

    private static List<String> aiVideos() {
        return Arrays.asList(
                "https://www.youtube.com/watch?v=JMUxmLyrhSk",
                "https://www.youtube.com/watch?v=aircAruvnKk",
                "https://www.youtube.com/watch?v=CS4cs9xVecg",
                "https://www.youtube.com/watch?v=oV3Tbw2zsvk",
                "https://www.youtube.com/watch?v=F1ka6a13S9I",
                "https://www.youtube.com/watch?v=tPYj3fFJGjk"
        );
    }

    private static List<String> biVideos() {
        return Arrays.asList(
                "https://www.youtube.com/watch?v=AGrl-H87pRU",
                "https://www.youtube.com/watch?v=0ANZi2uAa0I",
                "https://www.youtube.com/watch?v=t1oU9wSbsF8",
                "https://www.youtube.com/watch?v=0mYq5LrQN1s",
                "https://www.youtube.com/watch?v=yKTSLffVGbk",
                "https://www.youtube.com/watch?v=tRZGeaHPoaw"
        );
    }

    private static List<String> ieltsVideos() {
        return Arrays.asList(
                "https://www.youtube.com/watch?v=3lDd2Gbe8uc",
                "https://www.youtube.com/watch?v=18kWcIpeFN4",
                "https://www.youtube.com/watch?v=ic5xB4TTfSs",
                "https://www.youtube.com/watch?v=MFxomvZBkZY",
                "https://www.youtube.com/watch?v=VdjFThd1a7Y",
                "https://www.youtube.com/watch?v=7a8np0L1j94"
        );
    }

    private static List<String> javaVideos() {
        return Arrays.asList(
                "https://www.youtube.com/watch?v=GoXwIVyNvX0",
                "https://www.youtube.com/watch?v=eIrMbAQSU34",
                "https://www.youtube.com/watch?v=grEKMHGYyns",
                "https://www.youtube.com/watch?v=ghb1HwM3Xb8",
                "https://www.youtube.com/watch?v=hBh_CC5y8-s",
                "https://www.youtube.com/watch?v=tK6C1zZqKc4"
        );
    }

    private static List<QuizQuestion> webQuiz() {
        return Arrays.asList(
                new QuizQuestion("What does HTML stand for?", Arrays.asList("HyperText Markup Language", "Hyperlink Markup Language", "Home Tool Markup Language"), 0),
                new QuizQuestion("CSS controls?", Arrays.asList("Structure", "Style", "Database"), 1),
                new QuizQuestion("JS runs in?", Arrays.asList("Browser", "Server only", "Compiler"), 0),
                new QuizQuestion("Flexbox is for?", Arrays.asList("Layout", "Database", "Images"), 0),
                new QuizQuestion("DOM stands for?", Arrays.asList("Document Object Model", "Data Object Mapper", "Dynamic Object Model"), 0)
        );
    }

    private static List<QuizQuestion> pfQuiz() {
        return Arrays.asList(
                new QuizQuestion("What is a variable?", Arrays.asList("Storage", "Loop", "Condition"), 0),
                new QuizQuestion("for-loop repeats?", Arrays.asList("Until condition", "Only once", "Never"), 0),
                new QuizQuestion("Array index starts at?", Arrays.asList("0", "1", "-1"), 0),
                new QuizQuestion("Which is boolean?", Arrays.asList("true", "42", "\"hi\""), 0),
                new QuizQuestion("== compares?", Arrays.asList("Values", "Files", "Projects"), 0)
        );
    }

    private static List<QuizQuestion> aiQuiz() {
        return Arrays.asList(
                new QuizQuestion("AI stands for?", Arrays.asList("Artificial Intelligence", "Actual Intuition", "Applied Integration"), 0),
                new QuizQuestion("Heuristic guides?", Arrays.asList("Search", "Sorting", "Compiling"), 0),
                new QuizQuestion("Neural nets use?", Arrays.asList("Weights", "Wheels", "Wires"), 0),
                new QuizQuestion("Supervised learning uses?", Arrays.asList("Labeled data", "Random clicks", "No data"), 0),
                new QuizQuestion("RL optimizes?", Arrays.asList("Reward", "HTML", "Ink"), 0)
        );
    }

    private static List<QuizQuestion> biQuiz() {
        return Arrays.asList(
                new QuizQuestion("Power BI is for?", Arrays.asList("Visualization", "Compilers", "Games"), 0),
                new QuizQuestion("DAX is?", Arrays.asList("Expression language", "Database", "OS"), 0),
                new QuizQuestion("A dashboard shows?", Arrays.asList("Insights", "Source code", "Audio"), 0),
                new QuizQuestion("Data source example?", Arrays.asList("Excel", "Steering wheel", "Hammer"), 0),
                new QuizQuestion("Publish to?", Arrays.asList("Power BI Service", "Google Drive", "DVD"), 0)
        );
    }

    private static List<QuizQuestion> ieltsQuiz() {
        return Arrays.asList(
                new QuizQuestion("IELTS tests?", Arrays.asList("English", "Math", "Physics"), 0),
                new QuizQuestion("Speaking lasts?", Arrays.asList("11-14 min", "1 hour", "5 min"), 0),
                new QuizQuestion("Writing has?", Arrays.asList("2 tasks", "5 tasks", "1 task"), 0),
                new QuizQuestion("Band score max?", Arrays.asList("9", "5", "12"), 0),
                new QuizQuestion("Listening sections?", Arrays.asList("4", "2", "8"), 0)
        );
    }

    private static List<QuizQuestion> javaQuiz() {
        return Arrays.asList(
                new QuizQuestion("JVM means?", Arrays.asList("Java Version Manager", "Java Virtual Machine", "Joint Virtual Model"), 2),
                new QuizQuestion("Keyword for inheritance?", Arrays.asList("extends", "inherits", "derives"), 0),
                new QuizQuestion("Interface uses?", Arrays.asList("implements", "extends", "imports"), 0),
                new QuizQuestion("ArrayList is?", Arrays.asList("Resizable", "Fixed", "Immutable"), 0),
                new QuizQuestion("Package keyword?", Arrays.asList("package", "import", "module"), 0)
        );
    }
}
