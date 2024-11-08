package com.example.useraccessmanagement.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Hash the password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection connection = DatabaseConnection.getConnection()) { // Use DatabaseConnection.getConnection()
            // Insert user into the database with hashed password
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Employee')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();

            // Redirect to login page after successful registration
            response.sendRedirect("login.jsp");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: Unable to register user.");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
