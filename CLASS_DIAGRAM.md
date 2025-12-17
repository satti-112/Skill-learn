# Skill Era - Class Diagram

## UML Class Diagram

```
┌────────────────────────────────────────────────────────────────────────────┐
│                                  <<abstract>>                              │
│                                    USER                                    │
│                         (implements Serializable)                          │
├────────────────────────────────────────────────────────────────────────────┤
│ Attributes:                                                                │
│  # username: String                                                        │
│  # password: String                                                        │
│  # email: String                                                           │
├────────────────────────────────────────────────────────────────────────────┤
│ Methods:                                                                   │
│  + User(username: String, password: String, email: String)                │
│  + getUsername(): String                                                   │
│  + setUsername(username: String): void                                     │
│  + getPassword(): String                                                   │
│  + setPassword(password: String): void                                     │
│  + getEmail(): String                                                      │
│  + setEmail(email: String): void                                           │
│  + showDashboard(): void <<abstract>>                                      │
│  + equals(Object): boolean                                                 │
│  + hashCode(): int                                                         │
│  + toString(): String                                                      │
└────────────────────────────────────────────────────────────────────────────┘
                                     △
                                     │
                     ┌───────────────┴───────────────┐
                     │                               │
        ┌────────────▼─────────────┐     ┌──────────▼──────────┐
        │      STUDENT             │     │ INSTRUCTOR          │
        │ (extends User)           │     │ (DEPRECATED)        │
        │ (implements Serializable)│     │                     │
        ├──────────────────────────┤     ├─────────────────────┤
        │ Attributes:              │     │ Status: Not used    │
        │  - admin: boolean        │     │ Replaced by:        │
        │  - enrolledCourses:      │     │ Student(admin=true) │
        │    ArrayList<Course>     │     │                     │
        │  - progressByCourse:     │     └─────────────────────┘
        │    Map<String,Progress>  │
        ├──────────────────────────┤
        │ Methods:                 │
        │  + Student(username,     │
        │    password, email)      │
        │  + Student(username,     │
        │    password, email,      │
        │    admin: boolean)       │
        │  + enrollInCourse(       │
        │    course: Course): void │
        │  + getEnrolledCourses(): │
        │    ArrayList<Course>     │
        │  + getProgress(          │
        │    courseCode): Progress │
        │  + isAdmin(): boolean    │
        │  + updateProgress(...):  │
        │    void                  │
        │  + showDashboard(): void │
        └──────────────────────────┘
                     │
                     │ contains
                     │
        ┌────────────▼──────────────────────────────────┐
        │            PROGRESS                          │
        │        (Inner Class - Static)                │
        │    (implements Serializable)                 │
        ├───────────────────────────────────────────────┤
        │ Attributes:                                   │
        │  - progressPercent: int [0-100]              │
        │  - quizCompleted: boolean                    │
        │  - assignmentSubmitted: boolean              │
        │  - videoCompleted: boolean                   │
        │  - certificateId: String (10-char)           │
        ├───────────────────────────────────────────────┤
        │ Methods:                                      │
        │  + getProgressPercent(): int                 │
        │  + setProgressPercent(int): void             │
        │  + isVideoCompleted(): boolean               │
        │  + setVideoCompleted(boolean): void          │
        │  + isQuizCompleted(): boolean                │
        │  + setQuizCompleted(boolean): void           │
        │  + isAssignmentSubmitted(): boolean          │
        │  + setAssignmentSubmitted(boolean): void     │
        │  + getCertificateId(): String                │
        │  + setCertificateId(String): void            │
        └───────────────────────────────────────────────┘


┌────────────────────────────────────────────────────────────────────────────┐
│                            COURSE                                          │
│                    (implements Serializable)                               │
├────────────────────────────────────────────────────────────────────────────┤
│ Attributes:                                                                │
│  - courseCode: String                                                      │
│  - courseTitle: String                                                     │
│  - instructorName: String                                                  │
│  - videoLinks: ArrayList<String>                                           │
│  - quizQuestions: ArrayList<QuizQuestion>                                  │
│  - assignmentPrompts: ArrayList<String>                                    │
│  - certificateNote: String                                                 │
├────────────────────────────────────────────────────────────────────────────┤
│ Methods:                                                                   │
│  + Course(courseCode, courseTitle, instructorName)                        │
│  + Course(courseCode, courseTitle, instructorName,                        │
│    videoLinks, quizQuestions, assignmentPrompts, certificateNote)         │
│  + getCourseCode(): String                                                 │
│  + setCourseCode(String): void                                             │
│  + getCourseTitle(): String                                                │
│  + setCourseTitle(String): void                                            │
│  + getInstructorName(): String                                             │
│  + setInstructorName(String): void                                         │
│  + getVideoLinks(): List<String>                                           │
│  + setVideoLinks(List<String>): void                                       │
│  + getQuizQuestions(): List<QuizQuestion>                                  │
│  + setQuizQuestions(List<QuizQuestion>): void                              │
│  + getAssignmentPrompts(): List<String>                                    │
│  + setAssignmentPrompts(List<String>): void                                │
│  + getCertificateNote(): String                                            │
│  + setCertificateNote(String): void                                        │
│  + toString(): String                                                      │
└────────────────────────────────────────────────────────────────────────────┘
                              │
                              │ contains
                              ▼
┌────────────────────────────────────────────────────────────────────────────┐
│                        QUIZ QUESTION                                       │
│                   (implements Serializable)                                │
├────────────────────────────────────────────────────────────────────────────┤
│ Attributes:                                                                │
│  - question: String                                                        │
│  - options: List<String>  [Fixed: 3 options]                              │
│  - correctIndex: int [0-2]                                                 │
├────────────────────────────────────────────────────────────────────────────┤
│ Methods:                                                                   │
│  + QuizQuestion(question, options, correctIndex)                          │
│  + getQuestion(): String                                                   │
│  + getOptions(): List<String>                                              │
│  + getCorrectIndex(): int                                                  │
│  + toString(): String                                                      │
└────────────────────────────────────────────────────────────────────────────┘


┌────────────────────────────────────────────────────────────────────────────┐
│                        DATA MANAGER                                        │
│                 (Handles I/O and Persistence)                              │
├────────────────────────────────────────────────────────────────────────────┤
│ Constants:                                                                 │
│  - DATA_FILE = "skillera_data.dat"                                         │
├────────────────────────────────────────────────────────────────────────────┤
│ Methods:                                                                   │
│  + saveData(users: ArrayList<User>,                                       │
│    courses: ArrayList<Course>): void                                       │
│    * Serializes to .dat file                                               │
│    * Writes text snapshot to .txt file                                     │
│                                                                            │
│  + loadData(): DataBundle                                                  │
│    * Deserializes from .dat file                                           │
│    * Handles EOFException, ClassNotFoundException                          │
│    * Returns empty bundle if file not found                                │
│                                                                            │
│  - writeTextSnapshot(users, courses): void                                 │
│    * Writes human-readable text file                                       │
│    * Lists all courses and users                                           │
│                                                                            │
│  Inner Class: DataBundle                                                   │
│    - users: ArrayList<User>                                                │
│    - courses: ArrayList<Course>                                            │
│    + getUsers(): ArrayList<User>                                           │
│    + getCourses(): ArrayList<Course>                                       │
└────────────────────────────────────────────────────────────────────────────┘


┌────────────────────────────────────────────────────────────────────────────┐
│                      GUI - LOGIN FRAME                                     │
│                  (Entry point - extends JFrame)                            │
├────────────────────────────────────────────────────────────────────────────┤
│ Components:                                                                │
│  - JTabbedPane: Login | Register tabs                                     │
│  - Login Tab: username, password fields + Login button                    │
│  - Register Tab: username, password, email, role selector                 │
├────────────────────────────────────────────────────────────────────────────┤
│ Methods:                                                                   │
│  + handleLogin(): void                                                     │
│  + handleRegister(): void                                                  │
│  + openDashboard(user): void                                               │
│  + findUser(username): User                                                │
│  + findUser(username, password): User                                      │
│  + hasAdmin(): boolean                                                     │
└────────────────────────────────────────────────────────────────────────────┘
                              │
                ┌─────────────┴──────────────┐
                │                            │
                ▼                            ▼
    ┌─────────────────────────┐   ┌───────────────────────────┐
    │  ADMIN DASHBOARD        │   │  STUDENT DASHBOARD        │
    │ (Admin only)            │   │ (Student only)            │
    ├─────────────────────────┤   ├───────────────────────────┤
    │ Features:               │   │ Features:                 │
    │  - Create courses       │   │  - Browse courses         │
    │  - List all courses     │   │  - Enroll in courses      │
    │  - Manage students      │   │  - Watch videos           │
    │  - Delete students      │   │  - Take quizzes           │
    │  - View enrollments     │   │  - Submit assignments     │
    │                         │   │  - Track progress         │
    │ Form Inputs:            │   │  - View certificates      │
    │  - Course code/title    │   │  - View completed courses │
    │  - Instructor name      │   │                           │
    │  - Video URLs           │   │ Dialogs:                  │
    │  - Quiz questions       │   │  - Browse all courses     │
    │  - Assignments          │   │  - Watch videos (buttons) │
    │  - Certificate note     │   │  - Quiz questions         │
    │                         │   │  - Certificate display    │
    └─────────────────────────┘   └───────────────────────────┘


┌────────────────────────────────────────────────────────────────────────────┐
│                           MAIN                                             │
│                   (Application Entry Point)                                │
├────────────────────────────────────────────────────────────────────────────┤
│ Responsibilities:                                                          │
│  1. Initialize DataManager                                                 │
│  2. Load existing data from file (if exists)                               │
│  3. Seed 6 demo courses (first time only)                                  │
│  4. Save data to file                                                      │
│  5. Launch LoginFrame                                                      │
├────────────────────────────────────────────────────────────────────────────┤
│ Methods:                                                                   │
│  + main(args: String[]): void                                              │
│  - seedSampleData(users, courses, dm): void                                │
│  - buildCourse(...): Course                                                │
│  - webVideos(), pfVideos(), aiVideos(), etc: List<String>                 │
│  - webQuiz(), pfQuiz(), aiQuiz(), etc: List<QuizQuestion>                  │
└────────────────────────────────────────────────────────────────────────────┘
```

---

## Data Flow Diagram

```
                          LOGIN FRAME
                              │
                ┌─────────────┴──────────────┐
                │                            │
                ▼                            ▼
          Admin Login                  Student Login
                │                            │
                ▼                            ▼
        ADMIN DASHBOARD              STUDENT DASHBOARD
                │                            │
         ┌──────┼──────┐              ┌──────┼──────────┐
         │              │              │      │   │      │
         ▼              ▼              ▼      ▼   ▼      ▼
    Create Course  Manage Students Browse Courses Watch Quiz Submit
    (Save to file)  (Save to file)  (Load from)   Video Questions Assignment
         │              │               │                │
         └──────────────┼───────────────┴────────────────┘
                        │
                        ▼
                  DATA MANAGER
                        │
         ┌──────────────┼──────────────┐
         │                             │
         ▼                             ▼
    skillera_data.dat          skillera_data.txt
   (Binary Serialized)       (Human-Readable)
```

---

## Data Model

```
FILE STRUCTURE (.dat):
┌─────────────────────────────────────────────────────┐
│            DataBundle (Serialized)                  │
├─────────────────────────────────────────────────────┤
│ - ArrayList<User>                                   │
│   └─ Student 1 (admin=true)                         │
│      └─ enrolledCourses: [Course1, Course2]         │
│      └─ progressByCourse:                           │
│         └─ "WD101" → Progress(100%, ✓, ✓, ✓)       │
│   └─ Student 2 (admin=false)                        │
│      └─ enrolledCourses: [Course3]                  │
│      └─ progressByCourse:                           │
│         └─ "AI201" → Progress(66%, ✓, ✓, ✗)        │
│                                                     │
│ - ArrayList<Course>                                 │
│   └─ Course (WD101)                                 │
│      └─ videoLinks: [url1, url2, ...]              │
│      └─ quizQuestions: [Q1, Q2, Q3, Q4, Q5]        │
│      └─ assignmentPrompts: [prompt1, prompt2]      │
│   └─ Course (AI201)                                 │
│      └─ ...                                         │
└─────────────────────────────────────────────────────┘

FILE STRUCTURE (.txt):
┌─────────────────────────────────────────────────────┐
│ Courses:                                            │
│  WD101 | Web Development | Instructor: Alice Smith │
│  Videos: [6 URLs]                                   │
│  Quiz: 5 questions                                  │
│  Assignments: [2 prompts]                           │
│  ...                                                │
│                                                     │
│ Users:                                              │
│  student | student@skillera.com                    │
│  admin | admin@skillera.com                         │
│  ...                                                │
└─────────────────────────────────────────────────────┘
```

---

## Class Relationships

```
INHERITANCE:
    User (abstract)
      ├── Student
      └── Instructor (deprecated)

COMPOSITION:
    Student
      ├── contains ArrayList<Course> (enrolled)
      └── contains Map<String, Progress> (per-course tracking)

    Course
      └── contains ArrayList<QuizQuestion>

    Progress
      └── stores progressPercent, completion flags, certificateId

ASSOCIATION:
    DataManager ←→ ArrayList<User>
    DataManager ←→ ArrayList<Course>

DEPENDENCY:
    LoginFrame → DataManager
    AdminDashboard → DataManager
    StudentDashboard → DataManager
```

---

## Design Patterns Used

1. **Model-View-Controller (MVC)**
   - Models: User, Student, Course, QuizQuestion, Progress
   - Views: LoginFrame, AdminDashboard, StudentDashboard
   - Controller: DataManager

2. **Singleton-like Pattern**
   - DataManager is instantiated once at startup
   - Ensures single source of truth for data

3. **Factory Pattern**
   - Main.java creates Course objects via buildCourse()

4. **Observer Pattern** (implicit)
   - GUI components listen to user actions via ActionListener

---

## Key Algorithms

### Quiz Scoring
```
Score = (Correct Answers / Total Questions) × 100
Progress = 66%
Example: 4/5 correct = 80% score
```

### Certificate ID Generation
```
Algorithm: Random alphanumeric (10 characters)
Format: [A-Z0-9]{10}
Example: A7K3M9X2B1
Uniqueness: Per course per student
```

### Progress Calculation
```
Video Watched    → 33%
Quiz Completed   → 66% (only if video ≥ 33%)
Assignment Done  → 100% (only if quiz ≥ 66%)
```

---

**Generated: December 17, 2025**
