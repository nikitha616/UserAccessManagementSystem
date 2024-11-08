<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Restrict access to Employees only
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("Employee")) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Database connection to fetch software applications
    java.sql.Connection connection = null;
    java.sql.Statement statement = null;
    java.sql.ResultSet resultSet = null;
    boolean dbError = false;

    try {
        Class.forName("org.postgresql.Driver");
        connection = java.sql.DriverManager.getConnection("jdbc:postgresql://localhost:5432/user_access_management", "postgres", "0616");
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT id, name FROM software");
    } catch (Exception e) {
        e.printStackTrace();
        dbError = true; // Flag for database error
    }
%>

<html>
<head>
    <title>Request Access</title>
</head>
<body>
    <h2>Request Access to Software</h2>

    <% if (dbError) { %>
        <p style="color: red;">Error: Unable to load software list. Please try again later.</p>
    <% } else { %>
        <form action="<%= request.getContextPath() %>/RequestServlet" method="post">
            <label for="softwareId">Software:</label>
            <select id="softwareId" name="softwareId" required>
                <%
                    // Populate dropdown with software options
                    while (resultSet != null && resultSet.next()) {
                %>
                    <option value="<%= resultSet.getInt("id") %>"><%= resultSet.getString("name") %></option>
                <%
                    }
                %>
            </select>
            <br><br>

            <label for="accessType">Access Type:</label>
            <select id="accessType" name="accessType" required>
                <option value="Read">Read</option>
                <option value="Write">Write</option>
                <option value="Admin">Admin</option>
            </select>
            <br><br>

            <label for="reason">Reason for Request:</label>
            <textarea id="reason" name="reason" required></textarea>
            <br><br>

            <input type="submit" value="Submit Request">
        </form>
    <% } %>
</body>
</html>

<%
    // Close database resources in a finally-like structure
    if (resultSet != null) try { resultSet.close(); } catch (Exception ignore) {}
    if (statement != null) try { statement.close(); } catch (Exception ignore) {}
    if (connection != null) try { connection.close(); } catch (Exception ignore) {}
%>
