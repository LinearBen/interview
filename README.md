# Spring MVC Hibernate Demo

這是一個使用 JSP、Servlet、Spring MVC、JPA (Hibernate) 和 MySQL 建立的簡易公告欄 Web 應用程式。

## 環境要求 (Prerequisites)

在開始之前，請確保您的開發環境已安裝並配置好以下軟體：

- **JDK**: 17 或更高版本
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Apache Tomcat**: 10.1+

## 技術棧

- **後端**: Spring MVC, Spring ORM, JPA (Hibernate)
- **前端**: JSP, JSTL, Bootstrap 5
- **資料庫**: MySQL
- **資料庫連接池**: HikariCP
- **日誌**: SLF4J, Logback
- **建置工具**: Maven
- **伺服器**: Tomcat 10.1+

## 安裝與設定步驟

1.  **Clone 專案**
    ```bash
    git clone <your-repository-url>
    cd spring-mvc-hibernate-demo
    ```

2.  **設定資料庫**
    *   登入您的 MySQL 伺服器。
    *   建立一個名為 `bulletin` 的資料庫。
    *   建立一個專用的資料庫使用者並授權（建議，更安全）。
    ```sql
    CREATE DATABASE bulletin CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    CREATE USER 'your_user'@'localhost' IDENTIFIED BY 'your_password';
    GRANT ALL PRIVILEGES ON bulletin.* TO 'your_user'@'localhost';
    FLUSH PRIVILEGES;
    ```

3.  **設定應用程式**
    *   **資料庫連線**: 開啟 `src/main/resources/app.properties` 檔案，並根據上一步的設定修改 `db.username` 和 `db.password`。
    *   **檔案上傳目錄**:
        *   `app.properties` 中的 `file.upload-dir` 預設為 `/opt/tomcat/uploads/bulletin-board`。
        *   請確保此目錄存在，並且執行 Tomcat 的使用者（通常是 `tomcat`）有權限寫入。
        ```bash
        # 在 Linux 伺服器上執行
        sudo mkdir -p /opt/tomcat/uploads/bulletin-board
        sudo chown -R tomcat:tomcat /opt/tomcat/uploads/bulletin-board
        ```

4.  **建置專案**
    在專案根目錄下執行 Maven 指令：
    ```bash
    mvn clean package
    ```
    建置成功後，會在 `target/` 目錄下產生 `bulletin-board.war` 檔案。

5.  **部署與執行**
    *   將 `target/bulletin-board.war` 複製到 Tomcat 的 `webapps` 目錄下。
    *   啟動 Tomcat 伺服器。
    *   開啟瀏覽器，訪問 `http://localhost:8080/bulletin-board/`。

現在您可以開始查看、新增、修改、刪除公告，並測試檔案上傳功能。
