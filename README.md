# Academic Management System
A comprehensive Java-based academic management system for educational institutions, featuring multiple user roles, course management, grade tracking, scheduling, and study group functionality.

## Overview

This application provides a complete solution for managing academic activities in an educational setting. It utilizes JavaFX for the user interface and MySQL for database operations, implementing role-based access control with distinct interfaces for students, professors, administrators, and super administrators.

## Features

### Authentication
- Secure login system with role-based access
- User session management

### Student Interface
- View personal grades and academic performance
- Enroll in or drop courses
- View scheduled activities and calendar
- Join study groups
- Communicate with peers through group messaging
- Register for group activities

### Professor Interface
- Manage courses and teaching activities
- Record and calculate student grades
- Schedule course activities (lectures, seminars, labs, exams)
- View student information and performance
- Access calendar for scheduled activities

### Administrator Interface
- Manage user accounts (create, modify, delete)
- Assign professors to courses
- View and filter user information
- Manage course information

### Super Administrator Interface
- All administrator capabilities
- Advanced system management options
- User role management

### Study Groups
- Creation and management of study groups
- Group activity scheduling
- Group messaging system
- Member management

## Technology Stack

- **Frontend**: JavaFX
- **Backend**: Java
- **Database**: MySQL
- **Additional Libraries**: Apache POI (for Excel file operations)

## Database Structure

The system uses a relational database with tables for:
- Users (with different types: students, professors, administrators)
- Courses/Disciplines
- Activities (lectures, seminars, labs, exams)
- Schedules
- Grades
- Study groups
- Messages
- User authentication

## Setup

1. **Database Configuration**:
   - Create a MySQL database
   - Run the SQL scripts in the following order:
     - `main_final.sql` (creates tables, stored procedures, and triggers)
     - `inserari.sql` (adds initial data if needed)

2. **Application Configuration**:
   - Update database connection settings in `DatabaseConnection.java` with your database credentials
   - Compile and run the application using the `Launcher.java` file

3. **Dependencies**:
   - Java JDK 11+
   - JavaFX SDK
   - MySQL Connector/J
   - Apache POI

## Usage

1. **Login**:
   - Enter your username and password
   - The system automatically directs you to the appropriate interface based on your role

2. **Navigation**:
   - Each interface provides a dashboard with buttons for different functionalities
   - All user types can view and modify personal information

## System Architecture

The application follows an object-oriented approach with:
- Interface separation by user role
- Database connection management
- SQL stored procedures for complex operations
- Triggers for maintaining data integrity
