package com.example.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 這個 Listener 用於在 Web 應用程式關閉時，優雅地關閉 MySQL JDBC 驅動程式的
 * AbandonedConnectionCleanupThread，以防止記憶體洩漏。
 */
@WebListener
public class MysqlDriverShutdownListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 應用程式啟動時無需執行任何操作
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 在應用程式被銷毀（關閉）時，調用 MySQL 驅動程式的靜態關閉方法
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}