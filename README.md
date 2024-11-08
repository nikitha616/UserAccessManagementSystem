User Access Management System

Overview

The User Access Management System is a web-based application that enables organizations to manage user access to various software applications. The system includes three primary user roles: Employee, Manager, and Admin.

Employees can sign up, log in, and request access to software applications.
Managers can review and approve or reject access requests.
Admins can create new software applications and manage system settings.

Features
User Registration: New users can sign up with a default role of Employee.
User Authentication: Existing users can log in and are redirected based on their role.
Software Management: Admins can add new software applications to the system.
Access Requests: Employees can request access to specific software applications.
Approval System: Managers can approve or reject access requests from Employees.

Technologies Used
Backend: Java Servlets
Frontend: JavaServer Pages (JSP)
Database: PostgreSQL
Build Tool: Maven

Prerequisites
Java Development Kit (JDK): Ensure JDK 11 or later is installed.
Apache Tomcat: Install and configure Tomcat server.
PostgreSQL: Set up a PostgreSQL database for data storage.
Maven: Make sure Maven is installed to manage dependencies.

Setup Instructions

1. Clone the Repositor
   git clone <repository-URL>
   cd UserAccessManagementSystem

2. Configure the Database
   Create a new PostgreSQL database. For example:

CREATE DATABASE user_access_management;
Set up the tables using the following schema:

CREATE TABLE users (
id SERIAL PRIMARY KEY,
username TEXT UNIQUE,
password TEXT,
role TEXT
);

CREATE TABLE software (
id SERIAL PRIMARY KEY,
name TEXT,
description TEXT,
access_levels TEXT
);

CREATE TABLE requests (
id SERIAL PRIMARY KEY,
user_id INTEGER REFERENCES users(id),
software_id INTEGER REFERENCES software(id),
access_type TEXT,
reason TEXT,
status TEXT
);

Insert initial roles (e.g., Employee, Manager, Admin) if required by your setup.
Update the database credentials in the DatabaseConnection class or in the .env file.

3. Configure Environment Variables (Optional)
   If you’re using environment variables for database credentials, create a .env file in the root of your project with the following variables:
   DB_URL=jdbc:postgresql://localhost:5432/user_access_management
   DB_USER=your_database_username
   DB_PASSWORD=your_database_password

4. Build the Project with Maven
   Run the following command to build the project and resolve dependencies:
   mvn clean install

5. Deploy to Tomcat
   Copy the generated .war file (located in target/UserAccessManagementSystem.war) to the Tomcat webapps directory.
   Start your Tomcat server.

6. Access the Application
   Once the server is running, access the application in your browser:
   http://localhost:8080/UserAccessManagementSystem

Usage
Sign-Up: New users can sign up and are assigned the Employee role by default.
Login: Use the login page to authenticate as an Employee, Manager, or Admin.
Employee Actions: Employees can request access to software applications.
Manager Actions: Managers can approve or deny access requests.
Admin Actions: Admins can add new software applications to the system.

Security
Passwords are hashed using BCrypt for secure storage.
Input validation is implemented to prevent SQL injection and other common security threats.

Future Enhancements
Some potential future improvements include:
Email notifications for access request approvals/rejections.
Enhanced role-based access control.
Detailed logging and auditing.

License
This project is open-source and available under the MIT License.
