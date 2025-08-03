<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>公告列表</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1>公告列表</h1>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/announcements/new">新增公告</a>
</div>

<table class="table table-striped">
    <thead>
    <tr>
        <th>標題</th>
        <th>發佈日期</th>
        <th>截止日期</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ann" items="${announcements}">
        <tr>
            <td>${ann.title}</td>
            <td>${ann.publishDate}</td>
            <td>${ann.deadline}</td>
            <td>
                <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/announcements/edit/${ann.id}">修改</a>
                <a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/announcements/delete/${ann.id}" onclick="return confirm('確定刪除?');">刪除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<nav>
    <ul class="pagination">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${i == page ? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/announcements?page=${i}">${i}</a>
            </li>
        </c:forEach>
    </ul>
 </nav>

</body>
</html>

