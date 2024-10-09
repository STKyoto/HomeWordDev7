package org.example.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
        private static final String DB_URL = "jdbc:postgresql://localhost:5432/HomeWorkDev";
        private static final String DB_USER = "postgres";
        private static final String DB_PASSWORD = "root";
        private static final HikariConfig config = new HikariConfig();
        private static final HikariDataSource ds;

        static {
                config.setJdbcUrl(DB_URL);
                config.setUsername(DB_USER);
                config.setPassword(DB_PASSWORD);
                ds = new HikariDataSource(config);
            Flyway.configure()
                    .dataSource(ds)
                    .locations("db/migrations")
                    .load()
                    .migrate();
        }
        public static Connection getConnection(){
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}
