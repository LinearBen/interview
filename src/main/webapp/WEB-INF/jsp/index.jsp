<%-- JSP 頁面指令，設定 Spring 表單標籤庫和 JSTL 核心標籤庫 --%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User List</title>
</head>
<body>
    <h2>Add User</h2>
    <%-- 新增使用者表單 --%>
    <form:form modelAttribute="user" method="post" action="add">
        Name: <form:input path="name" />
        <input type="submit" value="Add" />
    </form:form>

    <h2>Users</h2>
    <%-- 使用者列表 --%>
    <ul>
        <c:forEach var="u" items="${users}">
            <li>${u.name}</li>
        </c:forEach>
    </ul>
</body>
</html>
