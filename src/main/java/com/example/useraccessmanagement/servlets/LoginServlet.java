package com.example.useraccessmanagement.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.getConnection()) { // Use DatabaseConnection.getConnection()
            // Check user credentials and get both hashed password and role
            String query = "SELECT id, password, role FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Retrieve hashed password from the database
                String storedHashedPassword = resultSet.getString("password");

                // Check if the password matches
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    // Valid credentials, get the user's ID and role
                    int userId = resultSet.getInt("id");
                    String role = resultSet.getString("role");

                    // Start a session and store user information
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId); // Store user ID in session
                    session.setAttribute("username", username);
                    session.setAttribute("role", role);

                    // Redirect based on role
                    if ("Employee".equals(role)) {
                        response.sendRedirect("requestAccess.jsp");
                    } else if ("Manager".equals(role)) {
                        response.sendRedirect("pendingRequests.jsp");
                    } else if ("Admin".equals(role)) {
                        response.sendRedirect("createSoftware.jsp");
                    }
                } else {
                    // Password does not match
                    response.getWriter().println("Invalid username or password.");
                }
            } else {
                // User not found
                response.getWriter().println("Invalid username or password.");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: Unable to authenticate user.");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }
}
