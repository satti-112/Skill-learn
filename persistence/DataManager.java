package persistence;

import models.Course;
import models.QuizQuestion;
import models.User;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;

public class DataManager {
    private static final String DATA_FILE = "skillera_data.dat";

    // public void saveData(ArrayList<User> users, ArrayList<Course> courses) {
    //     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
    //         oos.writeObject(new DataBundle(users, courses));
    //         writeTextSnapshot(users, courses);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    //using an other saving data method to avoid EOFException
    public void saveData(ArrayList<User> users, ArrayList<Course> courses) {
    System.out.println("Saving " + users.size() + " users and " + courses.size() + " courses");
    
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
        DataBundle bundle = new DataBundle(users, courses);
        oos.writeObject(bundle);
        System.out.println("Successfully saved to: " + DATA_FILE);
        
        writeTextSnapshot(users, courses);
        System.out.println("Created text snapshot");
    } catch (IOException e) {
        System.err.println("ERROR: Failed to save data!");
        e.printStackTrace();
    }
}

    // public DataBundle loadData() {
    //     File file = new File(DATA_FILE);
    //     if (!file.exists()) {
    //         return new DataBundle(new ArrayList<>(), new ArrayList<>());
    //     }

    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
    //         Object obj = ois.readObject();
    //         if (obj instanceof DataBundle) {
    //             return (DataBundle) obj;
    //         }
    //     } catch (EOFException eof) {
    //         return new DataBundle(new ArrayList<>(), new ArrayList<>());
    //     } catch (ClassNotFoundException cnfe) {
    //         cnfe.printStackTrace();
    //     } catch (IOException ioe) {
    //         ioe.printStackTrace();
    //     }
    //     return new DataBundle(new ArrayList<>(), new ArrayList<>());
    // }
       // another loading data method to load correclty
       
       public DataBundle loadData() {
    File file = new File(DATA_FILE);
    System.out.println("Loading data from: " + file.getAbsolutePath());
    System.out.println("File exists: " + file.exists());
    
    if (!file.exists()) {
        System.out.println("File doesn't exist, returning empty bundle");
        return new DataBundle(new ArrayList<>(), new ArrayList<>());
    }
    
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
        Object obj = ois.readObject();
        System.out.println("Loaded object type: " + obj.getClass().getName());
        
        if (obj instanceof DataBundle) {
            DataBundle bundle = (DataBundle) obj;
            System.out.println("Loaded " + bundle.getUsers().size() + " users and " + 
                             bundle.getCourses().size() + " courses");
            return bundle;
        } else {
            System.err.println("ERROR: File contains wrong object type: " + obj.getClass());
        }
    } catch (EOFException eof) {
        System.err.println("ERROR: EOFException - File might be corrupted or empty!");
        eof.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
        System.err.println("ERROR: ClassNotFoundException!");
        cnfe.printStackTrace();
    } catch (IOException ioe) {
        System.err.println("ERROR: IOException during loading!");
        ioe.printStackTrace();
    }
    
    System.out.println("Returning empty bundle due to error");
    return new DataBundle(new ArrayList<>(), new ArrayList<>());
}

    // private void writeTextSnapshot(ArrayList<User> users, ArrayList<Course> courses) {
    //     try (PrintWriter pw = new PrintWriter(new FileWriter("skillera_data.txt"))) {
    //         pw.println("Courses:");
    //         for (Course c : courses) {
    //             pw.println(c.getCourseCode() + " | " + c.getCourseTitle() + " | Instructor: " + c.getInstructorName());
    //             pw.println("Videos: " + c.getVideoLinks());
    //             pw.println("Quiz: " + c.getQuizQuestions().size() + " questions");
    //             pw.println("Assignments: " + c.getAssignmentPrompts());
    //         }
    //         pw.println();
    //         pw.println("Users:");
    //         for (User u : users) {
    //             pw.println(u.getUsername() + " | " + u.getEmail());
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }

    // }
    // }
    private void writeTextSnapshot(ArrayList<User> users, ArrayList<Course> courses) {
    try (PrintWriter pw = new PrintWriter(new FileWriter("skillera_data.txt"))) {
        pw.println("Courses:");
        for (Course c : courses) {
            pw.println(c.getCourseCode() + " | " + c.getCourseTitle() + " | Instructor: " + c.getInstructorName());
            pw.println("Videos: " + c.getVideoLinks());
            pw.println("Quiz Questions (" + c.getQuizQuestions().size() + "):");
            for (QuizQuestion q : c.getQuizQuestions()) {
                pw.println("  Q: " + q.getQuestion());
                pw.println("  Options: " + q.getOptions());
                pw.println("  Correct Answer Index: " + q.getCorrectIndex());
            }
            pw.println("Assignments: " + c.getAssignmentPrompts());
            pw.println("---");
        }
        pw.println();
        pw.println("Users:");
        for (User u : users) {
            pw.println(u.getUsername() + " | " + u.getEmail());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}

   
