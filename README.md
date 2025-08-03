# Spring MVC Hibernate Demo

這是一個使用 JSP、Servlet、Spring MVC、JPA (Hibernate) 和 MySQL 的簡易公告欄範例。

## 技術棧

- **後端**: Spring MVC, Spring ORM, JPA (Hibernate)
- **前端**: JSP, JSTL, Bootstrap 5
- **資料庫**: MySQL
- **建置工具**: Maven
- **伺服器**: Tomcat 10.1+

## 建置專案

```bash
mvn clean package
```

建置完成後，`target/spring-mvc-hibernate-demo.war` 即可放入本地端的 Tomcat `webapps` 目錄中。

## 使用說明

1.  請確認您的 MySQL 服務已啟動，並已建立名為 `bulletin` 的資料庫。
2.  修改 `src/main/resources/app.properties` 中的資料庫連線資訊。
3.  啟動 Tomcat 後，瀏覽 `http://localhost:8080/spring-mvc-hibernate-demo/`，即可查看、新增、修改、刪除公告，並支援檔案上傳。
