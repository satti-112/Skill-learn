import gui.LoginFrame;
import models.Course;
import models.User;
import persistence.DataManager;
import persistence.DataBundle;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        
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

            

            new LoginFrame(users, courses, dataManager);
        
    }

}
