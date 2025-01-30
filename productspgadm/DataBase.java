package productspgadm;

import java.sql.*;

public class DataBase {
    private final String host = "localhost";
    private final String port = "5432";
    private final String dbName = "09.12";
    private final String login = "kolesov";
    private final String password = "123";

    private Connection dbCon; // подключение к бд

    private Connection getDBConnection()  {
        String str = "jdbc:postgresql://" + host + ":"
                + port + "/" + dbName;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Соединение установлено");
        } catch (ClassNotFoundException e) {
            System.out.println("Соединение не установлено");
        }
        try {
            dbCon = DriverManager.getConnection(str, login, password);
        } catch (SQLException e) {
            System.out.println("Неверный путь (логин и пароль)");
        }
        return dbCon;
    }

    public void isConnection() throws SQLException, ClassNotFoundException {
        dbCon = getDBConnection();
        System.out.println(dbCon.isValid(1000));
    }

    public void createTable(String tableName)  {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName +
                " (id SERIAL PRIMARY KEY , name VARCHAR(50), job VARCHAR(50), city VARCHAR(50), password VARCHAR(15))";

        try {
            Statement statement = getDBConnection().createStatement();
            statement.executeUpdate(sql);
            System.out.println("Таблица создалась");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTable(String table, String name, String job, String city, String password) {
        try {
            Statement statement = getDBConnection().createStatement();
            int rows = statement.executeUpdate("INSERT INTO " + table + " (name, job, city, password) VALUES ('" + name + "', '" + job + "', '" + city + "', '" + password + "');");
            System.out.printf("Добавлено %d строк ", rows);
        } catch (SQLException e) {
            System.out.println("Не удалось добавить");
        }
    }


    public void selectTable(String tableName) throws SQLException {
        Statement statement = getDBConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from " + tableName);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String job = resultSet.getString(3);  // Получаем значение job
            String city = resultSet.getString(4); // Получаем значение city
            String password = resultSet.getString(5); // Получаем значение password
            System.out.printf("%d. %s. %s. %s. %s.\n", id, name, job, city, password);
        }
    }


    public int countRows(String tableName) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try {
            Statement statement = getDBConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подсчете строк: " + e.getMessage());
        }
        return count;
    }


}

