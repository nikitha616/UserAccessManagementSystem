<%
    String role = (String) session.getAttribute("role");
    if (role == null || !role.equals("Admin")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Software</title>
</head>
<body>
    <h2>Add New Software Application</h2>
    <form action="SoftwareServlet" method="post">
        <label for="name">Software Name:</label>
        <input type="text" id="name" name="name" required>
        <br><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea>
        <br><br>

        <label>Access Levels:</label>
        <input type="checkbox" name="accessLevels" value="Read"> Read
        <input type="checkbox" name="accessLevels" value="Write"> Write
        <input type="checkbox" name="accessLevels" value="Admin"> Admin
        <br><br>

        <input type="submit" value="Create Software">
    </form>
</body>
</html>
