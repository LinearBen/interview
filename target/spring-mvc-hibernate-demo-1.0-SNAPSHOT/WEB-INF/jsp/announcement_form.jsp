<%-- JSP 頁面指令，設定 JSTL 和 Spring 表單標籤庫，以及頁面編碼 --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Announcement Form</title>
    <%-- 引入 Bootstrap CSS --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <style>
        /* 錯誤訊息的樣式 */
        .error {
            color: #d9534f; /* 紅色 */
            font-size: 0.9em;
            font-weight: bold;
            display: block;
            margin-top: 5px;
        }
        /* 每個表單項的間距 */
        div {
            margin-bottom: 15px;
        }
    </style>
</head>
<body class="container mt-4">
    <h2>Announcement Form</h2>
    <%-- 公告表單，使用 Spring 的 form 標籤庫 --%>
    <form:form modelAttribute="announcement" action="${pageContext.request.contextPath}/announcements/save" method="post" enctype="multipart/form-data">
        <%-- 隱藏的 ID 欄位，用於更新操作 --%>
        <form:hidden path="id"/>
        <div class="mb-3">
            <label for="title" class="form-label">標題:</label><br/>
            <form:input path="title" id="title" cssClass="form-control"/>
            <%-- 顯示標題的錯誤訊息 --%>
            <form:errors path="title" cssClass="error" />
        </div>
        <div class="mb-3">
            <label for="publisher" class="form-label">發布者:</label><br/>
            <form:input path="publisher" id="publisher" cssClass="form-control"/>
            <%-- 顯示發布者的錯誤訊息 --%>
            <form:errors path="publisher" cssClass="error" />
        </div>
        <div class="mb-3">
            <label for="content" class="form-label">內容:</label><br/>
            <form:textarea path="content" id="content" cssClass="form-control" rows="5"/>
            <%-- 顯示內容的錯誤訊息 --%>
            <form:errors path="content" cssClass="error" />
        </div>
        <div class="mb-3">
            <label for="publishDate" class="form-label">發布日期:</label><br/>
            <form:input type="datetime-local" path="publishDate" id="publishDate" cssClass="form-control"/>
            <%-- 顯示發布日期的錯誤訊息 --%>
            <form:errors path="publishDate" cssClass="error" />
        </div>
        <div class="mb-3">
            <label for="deadline" class="form-label">截止日期 (選填):</label><br/>
            <form:input type="date" path="deadline" id="deadline" cssClass="form-control"/>
            <%-- 顯示截止日期的錯誤訊息 --%>
            <form:errors path="deadline" cssClass="error" />
        </div>
        <div class="mb-3">
            <label for="fileUpload" class="form-label">附件:</label><br/>
            <input type="file" name="fileUpload" id="fileUpload" class="form-control" />
            <%-- 如果有現有附件，則顯示其檔案名稱 --%>
            <c:if test="${not empty announcement.attachmentFilename}">
                <p class="mt-2">目前檔案: ${announcement.attachmentFilename.substring(announcement.attachmentFilename.indexOf('_') + 1)}</p>
            </c:if>
        </div>
        <div>
            <%-- 儲存按鈕 --%>
            <button type="submit" class="btn btn-primary">儲存</button>
        </div>
    </form:form>
</body>
</html>