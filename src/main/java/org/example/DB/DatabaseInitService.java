package org.example.DB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.DB.Database.SQL_INIT_FILE_PATH;

public class DatabaseInitService {
    public static void main(String[] args) {
        Connection connection = Database.getConnection();
        try (Statement statement = connection.createStatement()){
             String sqlInit = new String(Files.readAllBytes(Paths.get(SQL_INIT_FILE_PATH)));
             statement.execute(sqlInit);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
