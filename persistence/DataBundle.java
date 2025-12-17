package persistence;


import models.Course;
import models.User;


import java.io.Serializable;
import java.util.ArrayList;

 public class DataBundle implements Serializable {
        private static final long serialVersionUID = 1L;
        private ArrayList<User> users;
        private ArrayList<Course> courses;

        public DataBundle(ArrayList<User> users, ArrayList<Course> courses) {
            this.users = users;
            this.courses = courses;
        }

        public ArrayList<User> getUsers() {
            return users;
        }

        public ArrayList<Course> getCourses() {
            return courses;
        }
    }
