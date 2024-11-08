package com.example.useraccessmanagement.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringJoiner;

public class SoftwareServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Get selected access levels
        String[] accessLevels = request.getParameterValues("accessLevels");
        StringJoiner accessLevelsJoiner = new StringJoiner(", ");
        if (accessLevels != null) {
            for (String accessLevel : accessLevels) {
                accessLevelsJoiner.add(accessLevel);
            }
        }
        String accessLevelsString = accessLevelsJoiner.toString();

        try (Connection connection = DatabaseConnection.getConnection()) { // Use DatabaseConnection.getConnection()
            // Insert software into the database
            String query = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, accessLevelsString);
            preparedStatement.executeUpdate();

            // Redirect to createSoftware.jsp with a success message
            response.sendRedirect("createSoftware.jsp?status=success");

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: Unable to add software.");
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
