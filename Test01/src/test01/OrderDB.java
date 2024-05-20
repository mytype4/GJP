package test01;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb?createDatabaseIfNotExist=true";  // 연결할 데이터베이스 URL
    private static final String USER = "root";  // 데이터베이스 사용자 이름
    private static final String PASSWORD = "";  // 데이터베이스 암호

    public void initializeDatabase() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // MySQL 데이터베이스 연결
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            // 테이블이 존재하지 않으면 생성
            String query = "CREATE TABLE IF NOT EXISTS order_info (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "customerName VARCHAR(255) NOT NULL, " +
                    "restaurantName VARCHAR(255) NOT NULL, " +
                    "menuName VARCHAR(255) NOT NULL, " +
                    "price INT NOT NULL, " +
                    "orderTime DATETIME NOT NULL)";
            stmt.executeUpdate(query);

            // 리소스 해제
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(String customerName, String restaurantName, String menuName, int price, LocalDateTime orderTime) {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // MySQL 데이터베이스 연결
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "INSERT INTO order_info (customerName, restaurantName, menuName, price, orderTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, customerName);
            pstmt.setString(2, restaurantName);
            pstmt.setString(3, menuName);
            pstmt.setInt(4, price);
            pstmt.setTimestamp(5, Timestamp.valueOf(orderTime));

            // 데이터 삽입 실행
            pstmt.executeUpdate();

            // 리소스 해제
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrderInfo() {
        List<Order> orders = new ArrayList<>();

        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // MySQL 데이터베이스 연결
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();

            // 주문 정보 가져오기
            String query = "SELECT customerName, restaurantName, menuName, price, orderTime FROM order_info";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String customerName = rs.getString("customerName");
                String restaurantName = rs.getString("restaurantName");
                String menuName = rs.getString("menuName");
                int price = rs.getInt("price");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();

                orders.add(new Order(customerName, restaurantName, menuName, price, orderTime));
            }

            // 리소스 해제
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 특정 고객의 주문 조회
    public List<Order> getOrdersByCustomer(String customerName) {
        List<Order> orders = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "SELECT customerName, restaurantName, menuName, price, orderTime FROM order_info WHERE customerName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, customerName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String customer = rs.getString("customerName");
                String restaurantName = rs.getString("restaurantName");
                String menuName = rs.getString("menuName");
                int price = rs.getInt("price");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();

                orders.add(new Order(customer, restaurantName, menuName, price, orderTime));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 특정 날짜 이후의 주문 조회
    public List<Order> getOrdersAfterDate(LocalDateTime date) {
        List<Order> orders = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "SELECT customerName, restaurantName, menuName, price, orderTime FROM order_info WHERE orderTime > ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setTimestamp(1, Timestamp.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("customerName");
                String restaurantName = rs.getString("restaurantName");
                String menuName = rs.getString("menuName");
                int price = rs.getInt("price");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();

                orders.add(new Order(customerName, restaurantName, menuName, price, orderTime));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 특정 레스토랑의 주문 조회
    public List<Order> getOrdersByRestaurant(String restaurantName) {
        List<Order> orders = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "SELECT customerName, restaurantName, menuName, price, orderTime FROM order_info WHERE restaurantName = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, restaurantName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("customerName");
                String restaurant = rs.getString("restaurantName");
                String menuName = rs.getString("menuName");
                int price = rs.getInt("price");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();

                orders.add(new Order(customerName, restaurant, menuName, price, orderTime));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 테이블 데이터 삭제
    public void clearOrderTable() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "DELETE FROM order_info";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 테이블 데이터 삭제 (TRUNCATE 사용)
    public void clearOrderTableUsingTruncate() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            String query = "TRUNCATE TABLE order_info";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
