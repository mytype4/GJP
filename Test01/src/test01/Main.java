package test01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // OrderDB 객체 생성
        OrderDB orderDB = new OrderDB();

        // 데이터베이스에서 모든 주문 정보 가져오기
        List<Order> orders = orderDB.getOrderInfo();

        // 모든 주문 정보 출력
        printOrders(orders);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("SQL 문을 입력하세요 (예: SELECT * FROM order_info WHERE customerName = '아름이'):");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            if (input.startsWith("SELECT * FROM order_info WHERE customerName =")) {
                String customerName = input.split("=")[1].trim().replace("'", "").replace(";", "");
                orders = orderDB.getOrdersByCustomer(customerName);
                printOrders(orders);
            } else if (input.startsWith("SELECT * FROM order_info WHERE orderTime >")) {
                String dateStr = input.split(">")[1].trim().replace("'", "").replace(";", "");
                LocalDateTime date = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                orders = orderDB.getOrdersAfterDate(date);
                printOrders(orders);
            } else if (input.startsWith("SELECT * FROM order_info WHERE restaurantName =")) {
                String restaurantName = input.split("=")[1].trim().replace("'", "").replace(";", "");
                orders = orderDB.getOrdersByRestaurant(restaurantName);
                printOrders(orders);
            } else {
                System.out.println("지원하지 않는 쿼리입니다.");
            }
        }

        scanner.close();
    }

    private static void printOrders(List<Order> orders) {
        System.out.printf("%-20s %-20s %-20s %-20s %-10s\n", "Order Time", "Customer Name", "Restaurant Name", "Menu Name", "Price");
        System.out.println("-------------------------------------------------------------------------------");

        for (Order order : orders) {
            System.out.printf("%-20s %-20s %-20s %-20s %-10d\n", order.getOrderTime(), order.getCustomerName(), order.getRestaurantName(), order.getMenuName(), order.getPrice());
        }
    }
}