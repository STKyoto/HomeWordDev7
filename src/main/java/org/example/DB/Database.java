package org.example.DB;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
        public static final String SQL_PROJECT_PRICE_FILE_PATH = "src/sql/print_project_prices.sql";
        public static final String SQL_INIT_FILE_PATH = "src/sql/init_db.sql";
        public static final String SQL_POPULATE_FILE_PATH = "src/sql/populate_db.sql";
        public static final String SQL_LONGEST_PROJECT_FILE_PATH = "src/sql/find_longest_project.sql";
        public static final String SQL_MAX_PROJECTS_CLIENT_FILE_PATH = "src/sql/find_max_projects_client.sql";
        public static final String SQL_MAX_SALARY_WORKERS_FILE_PATH = "src/sql/find_max_salary_worker.sql";
        public static final String SQL_YOUNGEST_ELDEST_WORKERS_FILE_PATH = "src/sql/find_youngest_eldest_workers.sql";
        private static final String DB_URL = "jdbc:postgresql://localhost:5432/Dev6HomeWork";
        private static final String DB_USER = "postgres";
        private static final String DB_PASSWORD = "root";
        private static final HikariConfig config = new HikariConfig();
        private static final HikariDataSource ds;

        static {
                config.setJdbcUrl(DB_URL);
                config.setUsername(DB_USER);
                config.setPassword(DB_PASSWORD);
                ds = new HikariDataSource(config);
        }
        public static Connection getConnection(){
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}
