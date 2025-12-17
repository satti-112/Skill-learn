# 🎓 Skill Era - E-Learning Platform

A comprehensive **Java-based E-Learning Management System** built with Swing GUI, featuring course management, student enrollment, progress tracking, and certificate generation.

---

## 📋 Table of Contents
- [Features](#features)
- [System Architecture](#system-architecture)
- [Tech Stack](#tech-stack)
- [Installation & Setup](#installation--setup)
- [Usage Guide](#usage-guide)
- [File Structure](#file-structure)
- [Data Persistence](#data-persistence)
- [OOP Concepts Implemented](#oop-concepts-implemented)

---

## ✨ Features

### For Administrators
- ✅ **Single Admin Registration** - Only one admin account can be created
- ✅ **Course Creation** - Create unlimited courses with videos, quizzes, and assignments
- ✅ **Quiz Management** - Add 5 multiple-choice questions (3 options each) per course
- ✅ **Student Management** - View enrolled students and delete accounts
- ✅ **Course Analytics** - See which students are enrolled in which courses

### For Students
- ✅ **User Registration** - Unlimited student account creation
- ✅ **Course Browsing** - Browse and preview all available courses
- ✅ **Course Enrollment** - Enroll in multiple courses
- ✅ **Video Lectures** - Access 6-7 YouTube videos per course
- ✅ **Interactive Quizzes** - Take MCQ-based quizzes with instant scoring
- ✅ **Assignments** - Submit 2 assignments per course
- ✅ **Progress Tracking** - Monitor learning progress (33%/66%/100%)
- ✅ **Certificate Generation** - Automatic certificates with 10-char verification IDs
- ✅ **Completed Courses View** - See all completed courses and certificates

### Core Features
- 📚 **6 Pre-loaded Courses** with real YouTube video links
- 📊 **Progress Calculation** - Video (33%) → Quiz (66%) → Assignment (100%)
- 💾 **Data Persistence** - Binary serialization (.dat) + text snapshot (.txt)
- 🔐 **User Authentication** - Secure login/registration system
- 🎫 **Certificate with Email** - Professional certificates showing student email and verification ID

---

## 🏗️ System Architecture

### Class Diagram Structure

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER (Abstract)                          │
│  ─────────────────────────────────────────────────────────────   │
│  - username: String                                              │
│  - password: String                                              │
│  - email: String                                                 │
│  + showDashboard(): void (abstract)                              │
└────────────────────────────┬────────────────────────────────────┘
                             │
                ┌────────────┴────────────┐
                │                         │
         ┌──────▼─────────┐      ┌───────▼──────────┐
         │  STUDENT       │      │ INSTRUCTOR       │
         │ ────────────   │      │ ──────────────   │
         │ - admin: bool  │      │ (Deprecated)     │
         │ - enrolled:    │      │                  │
         │   ArrayList    │      │ Replaced by      │
         │ - progress:    │      │ Student(admin)   │
         │   Map          │      │                  │
         └────────────────┘      └──────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                        COURSE                                    │
│  ─────────────────────────────────────────────────────────────   │
│  - courseCode: String                                            │
│  - courseTitle: String                                           │
│  - instructorName: String                                        │
│  - videoLinks: ArrayList<String>                                 │
│  - quizQuestions: ArrayList<QuizQuestion>                        │
│  - assignmentPrompts: ArrayList<String>                          │
│  - certificateNote: String                                       │
└─────────────────────────────────────────────────────────────────┘
                             │
                             │ contains
                             │
         ┌───────────────────▼──────────────────┐
         │     QUIZ QUESTION                    │
         │  ──────────────────────────────────  │
         │  - question: String                  │
         │  - options: List<String> (3 options) │
         │  - correctIndex: int (0-2)           │
         └──────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    PROGRESS (Inner Class)                        │
│  ─────────────────────────────────────────────────────────────   │
│  - progressPercent: int (0-100)                                  │
│  - videoCompleted: boolean                                       │
│  - quizCompleted: boolean                                        │
│  - assignmentSubmitted: boolean                                  │
│  - certificateId: String (10-char alphanumeric)                  │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    DATA MANAGER                                  │
│  ─────────────────────────────────────────────────────────────   │
│  + saveData(users, courses): void                                │
│  + loadData(): DataBundle                                        │
│  - writeTextSnapshot(users, courses): void                       │
│                                                                  │
│  * Handles ObjectInputStream/ObjectOutputStream                  │
│  * Manages skillera_data.dat (binary)                            │
│  * Manages skillera_data.txt (text snapshot)                     │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                      GUI LAYER                                   │
│  ─────────────────────────────────────────────────────────────   │
│  1. LoginFrame                                                   │
│     - Tabbed: Login / Register                                   │
│     - Routes to StudentDashboard or AdminDashboard               │
│                                                                  │
│  2. AdminDashboard                                               │
│     - Course creation form                                       │
│     - Course list                                                │
│     - Student management                                         │
│                                                                  │
│  3. StudentDashboard                                             │
│     - Course list (enrolled only)                                │
│     - Browse/enroll dialog                                       │
│     - Video buttons                                              │
│     - Quiz interface                                             │
│     - Progress tracker                                           │
│     - Certificate viewer                                         │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Java 23 |
| **GUI Framework** | Swing (JFrame, JPanel, BorderLayout, GridLayout) |
| **Data Persistence** | Object Serialization (ObjectInputStream/ObjectOutputStream) |
| **File Format** | Binary (.dat), Text (.txt) |
| **OOP Paradigm** | Abstraction, Inheritance, Encapsulation, Polymorphism |
| **Design Pattern** | MVC (Model-View-Controller) |

---

## 📦 Installation & Setup

### Prerequisites
- Java JDK 23 or higher
- Git (for version control)

### Steps

1. **Clone the Repository**
```bash
git clone https://github.com/sadaqat1851/Skill-Era.git
cd Skill-Era
```

2. **Compile the Project**
```bash
javac -d out models/*.java persistence/DataManager.java gui/*.java Main.java
```

3. **Run the Application**
```bash
java -cp out Main
```

The application will launch with a login screen.

---

## 🎮 Usage Guide

### First Time Setup
1. **Register Admin** (one-time only)
   - Click **Register** tab
   - Create account with any username/password
   - Select **Admin** role (only option if no admin exists)

2. **Create Demo Student Account** (optional)
   - Click **Register** tab again
   - Create account with username: `student`, password: `1234`
   - Select **Student** role

### Admin Workflow
1. **Login** as admin
2. **Create Courses**:
   - Enter Course Code (e.g., WD101)
   - Enter Course Title (e.g., Web Development)
   - Enter Instructor Name
   - Add Video URLs (one per line, 6-7 videos)
   - Add Quiz Questions in format:
     ```
     Question|Option1|Option2|Option3|CorrectAnswer[1-3]
     ```
   - Add 2 Assignment Prompts
   - Click **Create Course**

3. **Manage Students**
   - View students enrolled in courses
   - Delete student accounts

### Student Workflow
1. **Login** with your credentials
2. **Browse Courses** - Click "Browse All Courses"
3. **Preview Course** - Select to see details
4. **Enroll** - Click OK to enroll
5. **Learn**:
   - Click **Watch Videos** → Select individual video → Opens YouTube
   - Click **Take Quiz** → Answer MCQ questions → See score
   - Click **Submit Assignment** → Enter response
6. **Track Progress** - Click **View Progress** to see 33%/66%/100%
7. **Get Certificate** - After 100% completion, click **View Certificate**
8. **View Completed** - See all completed courses and certificates

---

## 📁 File Structure

```
Skill-Era/
│
├── models/
│   ├── User.java                 # Abstract base class
│   ├── Student.java              # Extends User + Progress inner class
│   ├── Course.java               # Course details
│   └── QuizQuestion.java         # Quiz question data
│
├── persistence/
│   └── DataManager.java          # Serialization & file I/O
│
├── gui/
│   ├── LoginFrame.java           # Login/Register UI
│   ├── AdminDashboard.java       # Admin interface
│   ├── StudentDashboard.java     # Student interface
│   └── InstructorDashboard.java  # (Deprecated)
│
├── Main.java                     # Application entry point
│
├── out/                          # Compiled .class files
│
├── skillera_data.dat             # Binary data (auto-generated)
├── skillera_data.txt             # Text snapshot (auto-generated)
│
├── README.md                     # This file
└── CLASS_DIAGRAM.md              # UML class diagram
```

---

## 💾 Data Persistence

### Storage Files
- **skillera_data.dat** - Binary serialized object (users + courses)
- **skillera_data.txt** - Human-readable text summary

### What Gets Saved
✅ All user accounts (username, password, email, admin status)
✅ All courses (code, title, instructor, videos, quizzes, assignments)
✅ Student progress (enrolled courses, progress %, quiz scores)
✅ Certificates (with 10-char verification IDs)

### Data Recovery
If files are corrupted:
1. Delete `skillera_data.dat` and `skillera_data.txt`
2. Restart application
3. 6 demo courses will be auto-seeded
4. Create your accounts again

---

## 🧬 OOP Concepts Implemented

### 1. **Abstraction**
```java
abstract class User {
    abstract void showDashboard();
}
```
- User class hides implementation details
- `showDashboard()` is abstract - implemented differently by Student

### 2. **Inheritance**
```java
class Student extends User {
    // Inherits username, password, email
    // Adds enrolledCourses, progress tracking
}
```
- Student inherits common user properties
- Adds student-specific functionality

### 3. **Encapsulation**
```java
private ArrayList<Course> enrolledCourses;
public ArrayList<Course> getEnrolledCourses() { ... }
```
- Data hidden with `private` modifier
- Controlled access via getters/setters
- Data validation in methods

### 4. **Polymorphism**
```java
ArrayList<User> users = new ArrayList<>();
users.add(new Student("admin", "pwd", "email", true));
users.add(new Student("student", "pwd", "email", false));

for (User u : users) {
    u.showDashboard(); // Calls overridden method
}
```
- Same interface, different implementations
- Admin and Student use different dashboards

### 5. **Serialization**
```java
class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;
}
```
- Objects saved to file and restored
- State persists across app restarts

---

## 📊 Sample Data

### Demo Accounts
| Username | Password | Role |
|----------|----------|------|
| admin | admin | Administrator |
| student | 1234 | Student |

### Pre-loaded Courses
| Code | Title | Instructor | Videos | Quiz | Assignments |
|------|-------|-----------|--------|------|-------------|
| WD101 | Web Development | Alice Smith | 6 | 5 | 2 |
| PF102 | Programming Fundamentals | Bob Jones | 6 | 5 | 2 |
| AI201 | Artificial Intelligence | Carol White | 6 | 5 | 2 |
| BI210 | Power BI Essentials | David Lee | 6 | 5 | 2 |
| ENG150 | IELTS Preparation | Emma Brown | 6 | 5 | 2 |
| JV301 | Java Programming | Frank Green | 6 | 5 | 2 |

---

## 🔒 Security Notes

- Passwords stored in plain text (for educational purposes)
- Production: Use hashing (BCrypt, Argon2)
- Production: Add role-based access control (RBAC)
- Production: Implement JWT or session tokens

---

## 📝 Quiz Format Reference

**Format:** `Question|Option1|Option2|Option3|CorrectAnswer[1-3]`

**Example:**
```
What is Java?|A language|A coffee|An island|1
What is OOP?|Procedural|Object Oriented|Functional|2
Java is?|Compiled|Interpreted|Both|3
```

- **Position 1-3:** Options (1-indexed)
- **Position 4:** Correct answer (1, 2, or 3)

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| App won't start | Check Java 23 installed: `java -version` |
| Courses not showing | Click "Browse All Courses" to reload from file |
| Data lost | Delete .dat/.txt files, restart, re-enter data |
| Quiz questions missing | Check format: `Q\|Opt1\|Opt2\|Opt3\|Answer[1-3]` |
| Certificate shows "Not generated" | Complete all videos, quiz, and assignment (100%) |

---

## 🚀 Future Enhancements

- [ ] Database integration (MySQL/PostgreSQL)
- [ ] Web-based version (Spring Boot + React)
- [ ] Email notifications
- [ ] Discussion forums
- [ ] Instructor course analytics
- [ ] Student learning paths
- [ ] Video streaming (not just links)
- [ ] Mobile app
- [ ] Payment integration for premium courses

---

## 📄 License

This project is open-source and available under the MIT License.

---

## 👨‍💼 Author

**Sadaqat Hussain**
- GitHub: [@sadaqat1851](https://github.com/sadaqat1851)

---

## 📞 Support

For issues or feature requests, please open an issue on GitHub.

---

## 🙏 Acknowledgments

Built as an educational project following **Java OOP Lab Manual** concepts:
- Object-Oriented Programming principles
- File I/O and Serialization (Lab 11 patterns)
- GUI design with Swing
- Data persistence and management

---

**Happy Learning! 🎓**
#   S k i l l - l e a r n  
 