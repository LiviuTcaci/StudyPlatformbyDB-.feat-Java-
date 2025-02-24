# Study Platform Management System

This project is a comprehensive study management platform developed for the "Introduction to Databases" course. It features a normalized MySQL database with stored procedures, triggers, and role-based access control (RBAC), along with a JavaFX graphical interface that adapts to the roles of students, professors, administrators, and super administrators.

## Overview

The application allows users to:
- **Authenticate:** Log in/out and view personal data (read-only).
- **Manage Users:** Administrators can add, modify, and delete users.
- **Academic Operations:** 
  - Students can enroll in courses, view grades, and manage study group memberships.
  - Professors can schedule activities, assign grades, and manage course rosters.
  - Administrators can search/filter users, assign professors to courses, and manage courses and study groups.
- **Data Integrity:**  
  The system uses a fully normalized database schema and enforces integrity via foreign keys, stored procedures, and triggers.

## Technologies Used

- **Java & JavaFX:** For building a role-specific GUI.
- **MySQL & JDBC:** For managing and connecting to the relational database.
- **SQL (Procedures & Triggers):** For business logic and ensuring data consistency.

## Project Structure

```
StudyPlatformbyDB-.feat-Java
├── JavaFX
│   └── src/main/java/com/example/javafx
│       ├── AdminInterface.java
│       ├── DatabaseConnection.java
│       ├── HelloController.java
│       ├── InterfataAutentificare.java
│       ├── Launcher.java
│       ├── ProfesorInterface.java
│       ├── StudentInterface.java
│       ├── SuperAdminInterface.java
│       ├── TestareConexiune.java
│       └── UserInterface.java
│   └── module-info.java
├── PlatformaDeStudiu
│   ├── creare_tabele.sql
│   ├── inserari.sql
│   ├── interogari.sql
│   ├── procedura_adaugare_note.sql
│   ├── procedura_adaugare_student.sql
│   ├── procedura_adaugare_utilizator.sql
│   ├── procedura_autentificare_utilizatori.sql
│   ├── procedura_deautentificare_utilizatori.sql
│   ├── procedura_modificare_info_utilizatori.sql
│   ├── trigger_absente_studenti.sql
│   ├── trigger_generate_log.sql
│   ├── trigger_generate_log_nota.sql
│   ├── trigger_inscriere_activitate.sql
│   ├── trigger_inscriere_grup_studiu.sql
│   ├── trigger_modificare_activitati.sql
│   ├── trigger_nota_finala.sql
│   ├── trigger_programare_activitati.sql
│   ├── trigger_renuntare_curs.sql
│   └── trigger_stergere_utilizator.sql
├── PDEALL.sql
├── diagrama.png
├── inserari.sql
├── main.sql
├── main_final.sql
└── roluri.sql
```

## Setup & Installation

1. **Database Setup:**
   - Execute the SQL scripts in the `PlatformaDeStudiu` directory (or the consolidated `PDEALL.sql`) to create and populate the database.
2. **Application Configuration:**
   - Update the database connection details in `DatabaseConnection.java`.
3. **Running the Application:**
   - Open the project in your IDE (e.g., IntelliJ IDEA).
   - Run the `Launcher.java` class to start the JavaFX application.

## Functionality

- **Authentication & RBAC:**  
  The system supports secure login and displays role-specific interfaces:
  - **Students:** Enroll in courses, view grades, manage study group participation.
  - **Professors:** Schedule academic activities, assign grades, view class rosters.
  - **Administrators & Super Administrators:** Manage user records, search and filter data, assign professors to courses, and more.
- **Academic Management:**  
  Features include course enrollment with conflict checking, grade management with weighted averages, and comprehensive scheduling for teaching activities.
- **Data Integrity & Automation:**  
  The database layer utilizes stored procedures and triggers to maintain data consistency and automate business logic.

## Future Improvements

- Enhanced conflict resolution for scheduling.
- Automated suggestions for study group memberships.
- Further refinement of the user interface for improved usability.
