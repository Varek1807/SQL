package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {
    private static Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }


    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }


    @SneakyThrows
    public static String getAuthCode(String login) {

        var runner = new QueryRunner();
        String select = "select code FROM users JOIN auth_codes on users.id = user_id WHERE login = '" + DataHelper.getAuthInfo().getLogin() + "';";

        try (
                var conn = DriverManager
                        .getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")
        ) {
            return runner.query(conn, select, new ScalarHandler<>());
        }
    }

    @SneakyThrows
    public static void setDown() {
        var runner = new QueryRunner();
        var sqlQuery = "DELETE FROM card_transactions;";
        var sqlQueryCode = "DELETE FROM auth_codes;";
        var sqlQueryCards = "DELETE FROM cards;";
        var sqlQueryUser = "DELETE FROM users;";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")
        ) {
            runner.update(conn, sqlQueryCode);
            runner.update(conn, sqlQuery);
            runner.update(conn, sqlQueryCards);
            runner.update(conn, sqlQueryUser);
        }
    }

    @SneakyThrows
    public static void deleteVerificationCode() {
        var runner = new QueryRunner();
        var sqlQuery = "DELETE FROM auth_codes;";
        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass")
        ) {
            runner.update(conn, sqlQuery);
        }
    }

    public static String getWrongPassword() {
        return faker.internet().password();
    }

}