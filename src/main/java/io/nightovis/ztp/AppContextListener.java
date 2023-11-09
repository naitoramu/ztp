package io.nightovis.ztp;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Statement statement = Database.getConnection().createStatement()){
            String fileContent = new String(Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream("db/migration/create_tables.sql")
            ).readAllBytes());

            // Execute your SQL statements here
            statement.execute(fileContent);
            // Add more SQL statements as needed

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method will be called when the servlet context is destroyed
        // You can clean up resources here if needed
        try {
            Database.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
