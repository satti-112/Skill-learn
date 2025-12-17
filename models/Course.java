package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String courseCode;
    private String courseTitle;
    private String instructorName;
    private ArrayList<String> videoLinks;
    private ArrayList<QuizQuestion> quizQuestions;
    private ArrayList<String> assignmentPrompts;
    private String certificateNote;

    public Course(String courseCode, String courseTitle, String instructorName) {
        this(courseCode, courseTitle, instructorName, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    }

    public Course(String courseCode, String courseTitle, String instructorName,
                  List<String> videoLinks, List<QuizQuestion> quizQuestions,
                  List<String> assignmentPrompts, String certificateNote) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;
        this.videoLinks = videoLinks == null ? new ArrayList<>() : new ArrayList<>(videoLinks);
        this.quizQuestions = quizQuestions == null ? new ArrayList<>() : new ArrayList<>(quizQuestions);
        this.assignmentPrompts = assignmentPrompts == null ? new ArrayList<>() : new ArrayList<>(assignmentPrompts);
        this.certificateNote = certificateNote;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public List<String> getVideoLinks() {
        return videoLinks == null ? new ArrayList<>() : new ArrayList<>(videoLinks);
    }

    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks == null ? new ArrayList<>() : new ArrayList<>(videoLinks);
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions == null ? new ArrayList<>() : new ArrayList<>(quizQuestions);
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions == null ? new ArrayList<>() : new ArrayList<>(quizQuestions);
    }

    public List<String> getAssignmentPrompts() {
        return assignmentPrompts == null ? new ArrayList<>() : new ArrayList<>(assignmentPrompts);
    }

    public void setAssignmentPrompts(List<String> assignmentPrompts) {
        this.assignmentPrompts = assignmentPrompts == null ? new ArrayList<>() : new ArrayList<>(assignmentPrompts);
    }

    public String getCertificateNote() {
        return certificateNote;
    }

    public void setCertificateNote(String certificateNote) {
        this.certificateNote = certificateNote;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseTitle + " (" + instructorName + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Course other = (Course) obj;
        return courseCode != null && courseCode.equalsIgnoreCase(other.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode == null ? 0 : courseCode.toLowerCase().hashCode();
    }
}
