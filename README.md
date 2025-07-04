# Smart UU Manager

**Smart UU Manager** is a Java-based web application designed to streamline university event announcements and student registrations.

## 🔧 Technologies Used
- Java (JSP + Servlets)
- HTML/CSS/JavaScript
- MySQL
- JDBC
- Apache Tomcat

## ✨ Features
- Admin and Student Login
- Event Creation & Management (Admin)
- Student Event Registration
- QR Code Generation for Attendance
- Bookmark Events
- Feedback & Rating System
- Email Reminders for Events

## 📂 Project Structure
- `src/`: Java Servlets
- `WebContent/`: JSP pages, styles, scripts
- `database/`: SQL file to create required tables

## 🏁 How to Run
1. Import the project in **Eclipse** or **VS Code with Java Extension Pack**
2. Add `mysql-connector.jar` to your build path
3. Set up MySQL database using `smartuu_manager.sql`
4. Deploy on **Apache Tomcat Server**
5. Access via `http://localhost:8080/Smart-UU-Manager/`

## 📁 Database Tables
- `users`: stores admin/student credentials
- `events`: stores event details
- `bookmarks`: maps student to bookmarked events
- `feedback`: stores event feedback
- `attendance`: logs event attendance via QR code

> Developed by Ishant.
