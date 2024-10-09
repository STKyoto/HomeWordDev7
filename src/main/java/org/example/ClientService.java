package org.example;

import lombok.SneakyThrows;
import org.example.annotation.Id;
import org.example.db.Database;
import org.example.models.Client;
import org.example.util.SqlGenerator;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientService {
    @SneakyThrows
    public long create(String name){
        Client client = new Client();
        client.setName(name);
        int returnedId = -1;
        String insertQuery = SqlGenerator.createInsertQuery(Client.class);
        Connection connection = Database.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)){
            connection.setAutoCommit(false);
            List<Field> insertableFields = getInsertableFields(client);
            setValuesIntoPreparedStatement(preparedStatement, client, insertableFields);
            preparedStatement.executeUpdate();

            ResultSet generatedKey = preparedStatement.getGeneratedKeys();
            while (generatedKey.next()){
                insertIdValue(client, generatedKey);
                returnedId = generatedKey.getInt(1);
            }
            connection.commit();
        }catch (Exception ex){
            connection.rollback();
            ex.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return returnedId;
    }
    @SneakyThrows
    public String getById(long id){
        String selectByIdQuery = SqlGenerator.createSelectByIdQuery(Client.class);
        Connection connection = Database.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery)){
            preparedStatement.setInt(1, (int) id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        }
        return null;
    }
    @SneakyThrows
    public void setName(long id, String name){
        String updateClientQuery = SqlGenerator.createUpdateQuery(Client.class);
        Connection connection = Database.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(updateClientQuery)){
            connection.setAutoCommit(false);
            preparedStatement.setInt(2, (int) id);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (Exception exception){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }
    @SneakyThrows
    public void deleteById(long id){
        String deleteClientQuery = SqlGenerator.createDeleteQuery(Client.class);
        Connection connection = Database.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement(deleteClientQuery)){
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, (int)id);
            preparedStatement.executeUpdate();
            connection.commit();
        }catch (Exception ex){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
    }
    @SneakyThrows
    public List<Client> listAll(){
        String selectAllQuery = SqlGenerator.createSelectAllQuery(Client.class);
        Connection connection = Database.getConnection();
        List<Client> clientList = new ArrayList<>();
            try(PreparedStatement preparedStatement = connection.prepareStatement(selectAllQuery)){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Client client = mapResultSetToObject(resultSet, Client.class);
                    clientList.add(client);
                }

            }
            return clientList;
    }
    @SneakyThrows
    private <T> void setValuesIntoPreparedStatement(PreparedStatement preparedStatement, T object, List<Field> insertableFields) {
        for (int i = 0; i < insertableFields.size(); i++) {
            Field field = insertableFields.get(i);
            field.setAccessible(true);
            preparedStatement.setObject(i + 1, field.get(object));
            field.setAccessible(false);
        }
    }
    private static <T> List<Field> getInsertableFields(T object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
    @SneakyThrows
    private static <T> void insertIdValue(T object, ResultSet generatedKeys){
        Field idField = getIdField(object);
        idField.setAccessible(true);
        idField.set(object, generatedKeys.getObject(1, Integer.class));
        idField.setAccessible(false);
    }
    @SneakyThrows
    private <T> T mapResultSetToObject(ResultSet resultSet, Class<T> type){
        T emptyObject = type.getConstructor().newInstance();
        for (Field declaredField : type.getDeclaredFields()) {
            declaredField.setAccessible(true);
            declaredField.set(emptyObject, resultSet.getObject(
                    SqlGenerator.resolveColumnName(declaredField)
            ));
            declaredField.setAccessible(false);
        }
        return emptyObject;
    }

    private static <T> Field getIdField(T object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findAny()
                .orElseThrow();
    }
}
