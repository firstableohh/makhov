package productspgadm;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataBase db = new DataBase();
        db.isConnection();
        db.createTable("streaming_service");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите имя (name) или введите 'exit' для выхода: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Введите профессию (job): ");
            String job = scanner.nextLine();
            System.out.print("Введите город (city): ");
            String city = scanner.nextLine();
            System.out.print("Введите пароль (pw): ");
            String password = scanner.nextLine();

            db.addTable("streaming_service", name, job, city, password);
            int totalRows = db.countRows("streaming_service");
            System.out.println("Всего записей в таблице: " + totalRows);
        }

        db.selectTable("streaming_service");
        scanner.close();
    }
}
