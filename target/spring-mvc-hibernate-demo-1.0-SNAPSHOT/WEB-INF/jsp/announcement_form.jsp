<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>${announcement.id == null ? '新增公告' : '修改公告'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
<h1>${announcement.id == null ? '新增公告' : '修改公告'}</h1>
<form method="post" action="${pageContext.request.contextPath}/announcements/save">
    <input type="hidden" name="id" value="${announcement.id}"/>

    <div class="mb-3">
        <label class="form-label">標題</label>
        <input class="form-control" type="text" name="title" value="${announcement.title}" required/>
    </div>

    <div class="mb-3">
        <label class="form-label">公布者</label>
        <input class="form-control" type="text" name="publisher" value="${announcement.publisher}" required/>
    </div>

    <div class="mb-3">
        <label class="form-label">發佈日期</label>
        <input class="form-control" type="date" name="publishDate" value="${announcement.publishDate}" required/>
    </div>

    <div class="mb-3">
        <label class="form-label">截止日期</label>
        <input class="form-control" type="date" name="deadline" value="${announcement.deadline}"/>
    </div>

    <div class="mb-3">
        <label class="form-label">公布內容</label>
        <textarea class="form-control" name="content" rows="5">${announcement.content}</textarea>
    </div>

    <button type="submit" class="btn btn-primary">儲存</button>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/announcements">取消</a>
</form>
</body>
</html>

