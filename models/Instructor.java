// package models;

// public class Instructor extends User {
//     private static final long serialVersionUID = 1L;

//     private String qualification;

//     public Instructor(String username, String password, String email, String qualification) {
//         super(username, password, email);
//         this.qualification = qualification;
//     }

//     public String getQualification() {
//         return qualification;
//     }

//     public void setQualification(String qualification) {
//         this.qualification = qualification;
//     }

//     public boolean isAdmin() {
//         return qualification != null && qualification.equalsIgnoreCase("Admin");
//     }

//     @Override
//     public void showDashboard() {
//         System.out.println("Welcome, " + username + "! Instructor dashboard coming up.");
//     }
// }
