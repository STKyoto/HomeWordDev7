package org.example.util;


import lombok.experimental.UtilityClass;
import org.example.annotation.Column;
import org.example.annotation.Id;
import org.example.annotation.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class SqlGenerator {
    private static final String INSERT_INTO_TABLE =
            "INSERT INTO %s(%s) VALUES(%s)";
    private static final String SELECT_FROM_TABLE_BY_ID =
            "SELECT * FROM client WHERE id = ?";
    private static final String UPDATE_TABLE =
            "UPDATE client SET name = ? WHERE id = ?";
    private static final String DELETE_FROM_TABLE =
            "DELETE FROM client WHERE %s = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM ";

    public static String createSelectAllQuery(Class<?> type){
            String tableName = type.getSimpleName().toLowerCase();
            return SELECT_ALL + tableName;
    }
    public static String createSelectByIdQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        String id = resolveIdName(type);

        return SELECT_FROM_TABLE_BY_ID.formatted(tableName, id);
    }

    public static String createInsertQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        List<String> fieldNames = getInsertableFieldNames(type);
        List<String> placeholders = Collections.nCopies(fieldNames.size(), "?");

        return INSERT_INTO_TABLE.formatted(
                tableName,
                String.join(",", fieldNames),
                String.join(",", placeholders));
    }

    public static String createUpdateQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        List<String> fieldNames = getUpdatableFieldNamesForUpdate(type);
        String id = resolveIdName(type);
        return UPDATE_TABLE.formatted(
                tableName,
                String.join(",", fieldNames),
                id
        );
    }

    public static String createDeleteQuery(Class<?> type) {
        String tableName = resolveTableName(type);
        String id = resolveIdName(type);
        return DELETE_FROM_TABLE.formatted(
                tableName, id);
    }

    public static String resolveColumnName(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElseGet(field::getName);
    }

    private static String resolveTableName(Class<?> type) {
        return Optional.ofNullable(type.getAnnotation(Table.class))
                .map(Table::value)
                .orElseGet(() -> type.getSimpleName().toLowerCase());
    }

    private static String resolveIdName(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(field -> field.getName().toLowerCase())
                .findAny()
                .orElseThrow();
    }

    private static List<String> getUpdatableFieldNamesForUpdate(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(SqlGenerator::resolveColumnName)
                .map(name -> name + "=?")
                .toList();
    }

    private static List<String> getInsertableFieldNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(SqlGenerator::resolveColumnName)
                .toList();
    }
}
