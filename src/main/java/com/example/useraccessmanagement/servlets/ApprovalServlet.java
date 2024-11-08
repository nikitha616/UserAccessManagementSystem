package com.example.useraccessmanagement.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApprovalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestId = request.getParameter("requestId");
        String action = request.getParameter("action");

        // Check if requestId and action are not null
        if (requestId == null || action == null) {
            response.getWriter().println("Error: Invalid request parameters.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) { // Using DatabaseConnection.getConnection()
            // Determine the status based on the action
            String status = action.equals("approve") ? "Approved" : "Rejected";

            // Update the request status in the database
            String updateQuery = "UPDATE requests SET status = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, status);
                preparedStatement.setInt(2, Integer.parseInt(requestId));

                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if the update was successful
                if (rowsUpdated > 0) {
                    response.sendRedirect("pendingRequests.jsp?status=success");
                } else {
                    response.getWriter().println("Error: Request not found or already processed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error: Unable to process the request.");
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
}
