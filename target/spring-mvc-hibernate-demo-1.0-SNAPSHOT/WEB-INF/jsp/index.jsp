<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User List</title>
</head>
<body>
    <h2>Add User</h2>
    <form:form modelAttribute="user" method="post" action="add">
        Name: <form:input path="name" />
        <input type="submit" value="Add" />
    </form:form>

    <h2>Users</h2>
    <ul>
        <c:forEach var="u" items="${users}">
            <li>${u.name}</li>
        </c:forEach>
    </ul>
</body>
</html>
