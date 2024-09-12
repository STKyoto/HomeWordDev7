package org.example.DB;

import org.example.Dto.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.example.DB.Database.*;

public class DatabaseQueryService {
    public List<ProjectPrices> fintProjectPrice(){
        return executeQuery(SQL_PROJECT_PRICE_FILE_PATH, resultSet -> {
            ProjectPrices projectPrices = new ProjectPrices();
            projectPrices.setProjectId(resultSet.getInt("project_id"));
            projectPrices.setProjectCost(resultSet.getBigDecimal("project_cost"));
            return projectPrices;
        });
    }
    public List<YoungestEldestWorkers> findYoungestEldestWorkers(){
        return executeQuery(SQL_YOUNGEST_ELDEST_WORKERS_FILE_PATH, resultSet -> {
            YoungestEldestWorkers youngestEldestWorkers = new YoungestEldestWorkers();
            youngestEldestWorkers.setName(resultSet.getString("name"));
            youngestEldestWorkers.setType(resultSet.getString("type"));
            youngestEldestWorkers.setBirthday(resultSet.getDate("birthday").toLocalDate());
            return youngestEldestWorkers;
        });
    }
    public List<MaxSalaryWorker> findMaxSalaryWorker(){
        return executeQuery(SQL_MAX_SALARY_WORKERS_FILE_PATH, resultSet -> {
            MaxSalaryWorker maxSalaryWorker = new MaxSalaryWorker();
            maxSalaryWorker.setSALARY(resultSet.getInt("SALARY"));
            maxSalaryWorker.setNAME(resultSet.getString("NAME"));
            return maxSalaryWorker;
        });
    }
    public List<LongestProject> findLongestProjects() {
        return executeQuery(SQL_LONGEST_PROJECT_FILE_PATH, resultSet -> {
            LongestProject longestProject = new LongestProject();
            longestProject.setProjectId(Math.toIntExact(resultSet.getObject("project_id", Long.class)));
            longestProject.setMonthCount(resultSet.getObject("MONTH_COUNT", Double.class));
            return longestProject;
        });
    }

    public List<MaxProjectClient> findMaxProjectClient() {
        return executeQuery(SQL_MAX_PROJECTS_CLIENT_FILE_PATH, resultSet -> {
            MaxProjectClient maxProjectClient = new MaxProjectClient();
            maxProjectClient.setCLIENT_ID(resultSet.getLong("CLIENT_ID"));
            maxProjectClient.setNAME(resultSet.getString("NAME"));
            maxProjectClient.setProjectcount(resultSet.getInt("project_count"));
            return maxProjectClient;
        });
    }

    public static <T> List<T> executeQuery(String sqlFilePath, ResultSetMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement()) {

            String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    T item = mapper.map(resultSet);
                    result.add(item);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    @FunctionalInterface
    public interface ResultSetMapper<T> {
        T map(ResultSet resultSet) throws SQLException;
    }
}
