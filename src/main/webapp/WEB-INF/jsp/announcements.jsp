<%-- JSP 頁面指令，設定 JSTL 標籤庫和頁面編碼 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>公告列表</title>
    <%-- 引入 Bootstrap CSS --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="container mt-4">
<div class="d-flex justify-content-between align-items-center mb-3">
    <h1>公告列表</h1>
    <%-- 新增公告按鈕 --%>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/announcements/new">新增公告</a>
</div>

<%-- 公告列表表格 --%>
<table class="table table-striped">
    <thead>
    <tr>
        <th>標題</th>
        <th>發佈日期</th>
        <th>截止日期</th>
        <th>附件</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <%-- 遍歷公告列表 --%>
    <c:forEach var="ann" items="${announcements}" varStatus="status">
        <%-- 公告標題列，點擊可展開/收合內容 --%>
        <tr>
            <td data-bs-toggle="collapse" data-bs-target="#collapse${status.index}" aria-expanded="false" aria-controls="collapse${status.index}" style="cursor: pointer;">${ann.title}</td>
            <td>${ann.formattedPublishDate}</td>
            <td>${ann.formattedDeadline}</td>
            <td>
                <%-- 如果有附件，則顯示附件檔名超連結 --%>
                <c:if test="${not empty ann.attachmentFilename}">
                    <a href="${pageContext.request.contextPath}/announcements/download/${ann.id}">${ann.originalAttachmentFilename}</a>
                </c:if>
            </td>
            <td>
                <%-- 修改按鈕 --%>
                <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/announcements/edit/${ann.id}">修改</a>
                <%-- 刪除按鈕 --%>
                <a class="btn btn-sm btn-danger" href="#" onclick="confirmDelete('${ann.id}');">刪除</a>
            </td>
        </tr>
        <%-- 公告內容列，預設為收合狀態 --%>
        <tr>
            <td colspan="5" class="p-0">
                <div id="collapse${status.index}" class="collapse">
                    <div class="card card-body" style="white-space: pre-wrap; overflow-wrap: break-word;">${ann.content}</div>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%-- 分頁導覽 --%>
<nav>
    <ul class="pagination">
        <!-- 上一頁按鈕 -->
        <li class="page-item ${page == 1 ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/announcements?page=${page - 1}">上一頁</a>
        </li>

        <!-- 頁碼 -->
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${i == page ? 'active' : ''}">
                <a class="page-link" href="${pageContext.request.contextPath}/announcements?page=${i}">${i}</a>
            </li>
        </c:forEach>

        <!-- 下一頁按鈕 -->
        <li class="page-item ${page == totalPages ? 'disabled' : ''}">
            <a class="page-link" href="${pageContext.request.contextPath}/announcements?page=${page + 1}">下一頁</a>
        </li>
    </ul>
 </nav>

<%-- 引入 Bootstrap JS --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    /**
     * 顯示刪除確認對話框，並在確認後導向到刪除頁面
     * @param id 要刪除的公告 ID
     */
    function confirmDelete(id) {
        if (confirm('確定刪除?')) {
            window.location.href = '${pageContext.request.contextPath}/announcements/delete/' + id;
        }
    }
</script>
</body>
</html>
