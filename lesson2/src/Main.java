import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/lesson1Database";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM driver");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name"));
        }

        Scanner scanner = new Scanner(System.in);
        StringBuilder sqlInsertUser = new StringBuilder("INSERT INTO driver(name, surname, age) VALUES ");

        for (int i = 0; i < 6; i++) {
            System.out.println("Введите имя, фамилию и возраст для пользователя " + (i + 1) + ":");
            String firstName = scanner.nextLine();
            String secondName = scanner.nextLine();
            String age = scanner.nextLine();

            sqlInsertUser.append("('") // INSERT INTO driver(name, surname, age) VALUES ('
                    .append(firstName).append("', '") // INSERT INTO driver(name, surname, age) VALUES ('firstName', '
                    .append(secondName).append("', ") // INSERT INTO driver(name, surname, age) VALUES ('firstName', 'secondName', '
                    .append(age).append(")"); // INSERT INTO driver(name, surname, age) VALUES ('firstName', 'secondName', 'age')

            if (i < 5) {
                sqlInsertUser.append(", ");
            }
        }
        sqlInsertUser.append(";");
        statement.executeUpdate(sqlInsertUser.toString()); // Я только так смог придумать, опять же, потому что, я iOS разработчик
        System.out.println("Добавлено 6 пользователей");
        printUsersUnder25(statement);
        statement.close();
        connection.close();
    }

    public static void printUsersUnder25(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM driver WHERE age < 25");
        System.out.println("Пользователи младше 25 лет:");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("id") + " " + resultSet.getString("name") + " " + resultSet.getString("surname") + " " + resultSet.getInt("age"));
        }
    }
}