package com.example.useraccessmanagement.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String softwareId = request.getParameter("softwareId");
        String accessType = request.getParameter("accessType");
        String reason = request.getParameter("reason");

        // Check if user is logged in
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) { // Use DatabaseConnection.getConnection()
            // Insert request into the database with status "Pending"
            String query = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, Integer.parseInt(softwareId));
            preparedStatement.setString(3, accessType);
            preparedStatement.setString(4, reason);
            preparedStatement.executeUpdate();

            // Redirect to requestAccess.jsp with a success message
            response.sendRedirect("requestAccess.jsp?status=success");
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: Unable to submit request. Details: " + e.getMessage());
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
