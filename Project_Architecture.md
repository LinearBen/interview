# 專案架構說明

這個專案是一個基於 Spring MVC 的 Web 應用程式，遵循經典的 MVC (Model-View-Controller) 設計模式，並進一步劃分為多個層次，以實現職責分離和模組化。

## 核心層次與關係

專案主要包含以下幾個核心層次：

1.  **Model (模型層)**：
    *   **職責**：負責應用程式的數據和業務邏輯。它包含了數據結構（實體類）以及對這些數據進行操作的規則。
    *   **主要類**：
        *   `com.example.model.Announcement`：代表公告的數據實體，對應數據庫中的 `announcements` 表。包含了公告的各項屬性（如標題、內容、發布日期、附件等）以及驗證規則。
        *   `com.example.model.User`：代表使用者的數據實體，對應數據庫中的 `users` 表。
    *   **關係**：Model 層是其他層次的基礎，Controller 和 Service 層會操作 Model 層的數據。

2.  **DAO (Data Access Object - 數據訪問層)**：
    *   **職責**：負責與數據庫進行直接交互，執行數據的增、刪、改、查 (CRUD) 操作。它將業務邏輯與數據庫操作細節分離開來。
    *   **主要接口與實現**：
        *   `com.example.dao.AnnouncementDao` (接口) 和 `com.example.dao.AnnouncementDaoImpl` (實現)：定義並實現了公告數據的持久化操作，例如儲存、獲取、列表、計數和刪除公告。
        *   `com.example.dao.UserDao` (接口) 和 `com.example.dao.UserDaoImpl` (實現)：定義並實現了使用者數據的持久化操作。
    *   **關係**：Service 層依賴於 DAO 層來執行數據庫操作。DAO 層直接與數據庫（透過 JPA/Hibernate）交互。

3.  **Service (服務層)**：
    *   **職責**：封裝了應用程式的業務邏輯。它協調 Controller 和 DAO 層，處理更複雜的業務流程，例如在儲存公告時同時處理檔案上傳和刪除舊檔案的邏輯。
    *   **主要接口與實現**：
        *   `com.example.service.AnnouncementService` (接口) 和 `com.example.service.AnnouncementServiceImpl` (實現)：提供了公告相關的業務方法，如儲存公告、獲取公告、分頁列表、刪除公告以及載入附件資源等。
        *   `com.example.service.UserService` (接口) 和 `com.example.service.UserServiceImpl` (實現)：提供了使用者相關的業務方法。
        *   `com.example.service.StorageService` (接口) 和 `com.example.service.FileSystemStorageService` (實現)：專門處理檔案儲存的業務邏輯，與公告服務分離，提高了模組化。
    *   **關係**：Controller 層調用 Service 層的方法來執行業務操作。Service 層則調用 DAO 層的方法來進行數據持久化。

4.  **Controller (控制器層)**：
    *   **職責**：接收來自使用者的請求，處理請求參數，調用 Service 層的業務邏輯，然後選擇合適的視圖 (View) 來響應使用者。它充當了 Model 和 View 之間的協調者。
    *   **主要類**：
        *   `com.example.controller.AnnouncementController`：處理所有與公告相關的 Web 請求（例如顯示列表、新增、編輯、刪除、下載附件等），並將數據傳遞給 JSP 視圖。
        *   `com.example.controller.UserController`：處理使用者相關的 Web 請求。
    *   **關係**：Controller 層是使用者請求的入口點，它依賴於 Service 層來執行業務邏輯，並將結果傳遞給 View 層。

5.  **View (視圖層)**：
    *   **職責**：負責呈現數據給使用者。在這個專案中，JSP (JavaServer Pages) 被用作視圖技術。
    *   **主要文件**：
        *   `src/main/webapp/WEB-INF/jsp/announcements.jsp`：顯示公告列表。
        *   `src/main/webapp/WEB-INF/jsp/announcement_form.jsp`：用於新增或編輯公告的表單。
        *   `src/main/webapp/WEB-INF/jsp/index.jsp`：顯示使用者列表和新增使用者表單。
        *   `src/main/webapp/index.jsp`：簡單地將請求轉發到公告列表頁面。
    *   **關係**：View 層從 Controller 層接收數據，並將其渲染成 HTML 頁面供使用者瀏覽。

## 運作流程

當使用者在瀏覽器中發出一個請求時，例如訪問公告列表頁面：

1.  **請求到達 Controller**：瀏覽器發送請求到 Spring MVC 的 DispatcherServlet，然後由 `AnnouncementController` 中的 `@GetMapping` 方法接收。
2.  **Controller 調用 Service**：`AnnouncementController` 會調用 `AnnouncementService` 中的 `list()` 方法來獲取公告數據。
3.  **Service 調用 DAO**：`AnnouncementService` 會調用 `AnnouncementDao` 中的 `list()` 和 `count()` 方法來從數據庫中獲取分頁的公告數據和總數。
4.  **DAO 與數據庫交互**：`AnnouncementDaoImpl` 使用 JPA/Hibernate 與數據庫進行實際的交互，執行 SQL 查詢並返回結果。
5.  **數據返回 Service**：數據從 DAO 返回到 Service 層。
6.  **Service 返回 Controller**：Service 層將處理後的數據（例如分頁資訊）返回給 Controller 層。
7.  **Controller 準備 Model 和 View**：`AnnouncementController` 將獲取的公告列表和分頁資訊添加到 `Model` 物件中，並指定要渲染的視圖名稱（例如 `announcements.jsp`）。
8.  **View 渲染**：Spring MVC 的視圖解析器會找到對應的 `announcements.jsp` 文件，並將 Controller 傳遞過來的數據填充到 JSP 頁面中，最終生成 HTML 響應。
9.  **響應返回瀏覽器**：生成的 HTML 響應被發送回使用者的瀏覽器，顯示公告列表頁面。

## 總結

這種分層架構使得專案的代碼組織清晰，各個模組職責明確，易於開發、測試和維護。當需求變化時，只需修改相關層次的代碼，而不會影響到其他層次，從而提高了應用程式的彈性和可擴展性。
