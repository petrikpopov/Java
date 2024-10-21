package jdbcSqlHome;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Program {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	public static void main(String[] args) {
	    createTable();
	    insertData();
	    updateData();
	    queryData();
	}
	
	private static Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(DB_URL, USER, PASSWORD);
	}
	
	private static void createTable() {
	    String checkTableSql = "CREATE TABLE IF NOT EXISTS Users " +
	                           "(id INT PRIMARY KEY AUTO_INCREMENT, " +
	                           " name VARCHAR(50), " +
	                           " age INT)";
	    executeUpdate(checkTableSql, "Таблица успешно создана (либо уже существует)...");
	}
	
	private static void insertData() {
	    String sql = "INSERT INTO Users (name, age) " +
	                 "VALUES ('Василиус Пупкинс', 30)";
	    executeUpdate(sql, "Данные успешно добавлены в таблицу.");
	}
	
	private static void updateData() {
	    String sql = "UPDATE Users " +
	                 "SET age = 31 " +
	                 "WHERE name = 'Василиус Пупкинс'";
	    executeUpdate(sql, "Данные успешно обновлены.");
	}
	
	private static void queryData() {
	    String sql = "SELECT id, name, age FROM Users";
	
	    try (Connection conn = getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            int age = rs.getInt("age");
	
	            System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void executeUpdate(String sql, String successMessage) {
	    try (Connection conn = getConnection();
	        Statement stmt = conn.createStatement()) {
	
	        stmt.executeUpdate(sql);
	        System.out.println(successMessage);
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
