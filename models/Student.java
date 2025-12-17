package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private ArrayList<Course> enrolledCourses;
    private Map<String, Progress> progressByCourse;
    private boolean admin;

    public Student(String username, String password, String email) {
        this(username, password, email, false);
    }

    public Student(String username, String password, String email, boolean admin) {
        super(username, password, email);
        this.enrolledCourses = new ArrayList<>();
        this.progressByCourse = new HashMap<>();
        this.admin = admin;
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void enrollInCourse(Course course) {
        if (course == null) {
            return;
        }
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            progressByCourse.put(course.getCourseCode(), new Progress());
        }
    }

    public Progress getProgress(String courseCode) {
        return progressByCourse.get(courseCode);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void updateProgress(String courseCode, int percent, boolean quizDone, boolean assignmentDone, boolean videoDone) {
        Progress progress = progressByCourse.get(courseCode);
        if (progress == null) {
            progress = new Progress();
            progressByCourse.put(courseCode, progress);
        }
        progress.setProgressPercent(Math.max(progress.getProgressPercent(), percent));
        if (quizDone) {
            progress.setQuizCompleted(true);
        }
        if (assignmentDone) {
            progress.setAssignmentSubmitted(true);
        }
        if (videoDone) {
            progress.setVideoCompleted(true);
        }
    }

    @Override
    public void showDashboard() {
        System.out.println("Welcome, " + username + "! Student dashboard coming up.");
    }

    public static class Progress implements Serializable {
        private static final long serialVersionUID = 1L;
        private int progressPercent;
        private boolean quizCompleted;
        private boolean assignmentSubmitted;
        private boolean videoCompleted;
        private String certificateId;

        public int getProgressPercent() {
            return progressPercent;
        }

        public void setProgressPercent(int progressPercent) {
            this.progressPercent = Math.max(0, Math.min(100, progressPercent));
        }

        public boolean isVideoCompleted() {
            return videoCompleted;
        }

        public void setVideoCompleted(boolean videoCompleted) {
            this.videoCompleted = videoCompleted;
        }

        public boolean isQuizCompleted() {
            return quizCompleted;
        }

        public void setQuizCompleted(boolean quizCompleted) {
            this.quizCompleted = quizCompleted;
        }

        public boolean isAssignmentSubmitted() {
            return assignmentSubmitted;
        }

        public void setAssignmentSubmitted(boolean assignmentSubmitted) {
            this.assignmentSubmitted = assignmentSubmitted;
        }

        public String getCertificateId() {
            return certificateId;
        }

        public void setCertificateId(String certificateId) {
            this.certificateId = certificateId;
        }
    }
}
