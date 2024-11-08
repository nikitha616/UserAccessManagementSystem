<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<html>
<head>
    <title>Pending Access Requests</title>
</head>
<body>
    <h2>Pending Access Requests</h2>

    <%
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_access_management", "postgres", "0616");

            // Define the SQL query to fetch pending requests
            String query = "SELECT requests.id, users.username AS employee_name, software.name AS software_name, " +
                           "requests.access_type, requests.reason " +
                           "FROM requests " +
                           "JOIN users ON requests.user_id = users.id " +
                           "JOIN software ON requests.software_id = software.id " +
                           "WHERE requests.status = 'Pending'";

            // Execute the query
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            // Check if there are any results
            if (resultSet != null && resultSet.isBeforeFirst()) {
                out.println("<p>Pending requests found.</p>");
            } else {
                out.println("<p>No pending requests in the database.</p>");
            }
    %>

    <table border="1">
        <tr>
            <th>Employee Name</th>
            <th>Software Name</th>
            <th>Access Type</th>
            <th>Reason</th>
            <th>Action</th>
        </tr>

        <%
            // Iterate over the result set and display each pending request
            while (resultSet.next()) {
        %>
        <tr>
            <td><%= resultSet.getString("employee_name") %></td>
            <td><%= resultSet.getString("software_name") %></td>
            <td><%= resultSet.getString("access_type") %></td>
            <td><%= resultSet.getString("reason") %></td>
            <td>
                <form action="ApprovalServlet" method="post" style="display: inline;">
                    <input type="hidden" name="requestId" value="<%= resultSet.getInt("id") %>" />
                    <button type="submit" name="action" value="approve">Approve</button>
                    <button type="submit" name="action" value="deny">Deny</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <%
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error fetching data from database.");
        } finally {
            // Close all resources
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    %>

</body>
</html>
