# Spring MVC Hibernate Demo

這是一個使用 JSP、Servlet、Spring MVC 與 Hibernate 的簡易範例。專案採用內建 H2 記憶體資料庫，並可打包成 WAR 檔部署在 Tomcat 上。

## 建置專案

```bash
mvn clean package
```

建置完成後，`target/spring-mvc-hibernate-demo.war` 即可放入本地端的 Tomcat `webapps` 目錄中。

## 使用說明

啟動 Tomcat 後，瀏覽 `http://localhost:8080/spring-mvc-hibernate-demo/`，即可新增使用者並查看清單。
