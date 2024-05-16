package test01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        List<Menu> menuList = new ArrayList<>();

        // Customer 객체 리스트 생성
        customers.add(new Customer("Customer1"));
        customers.add(new Customer("Customer2"));
        customers.add(new Customer("Customer3"));

        // Owner 객체 리스트 생성
        owners.add(new Owner("Owner1"));
        owners.add(new Owner("Owner2"));
        owners.add(new Owner("Owner3"));

        customers.get(0).introduce();

        owners.get(0).createRestaurant("광운이네");
        owners.get(0).createRestaurant("광운광운이");
        owners.get(0).addMenuToRestaurant("광운이네", "맛있는 음식1", 10000, menuList);
        owners.get(0).addMenuToRestaurant("광운광운이", "떡볶이", 110000, menuList);

        owners.get(1).createRestaurant("광운이네");
        owners.get(1).createRestaurant("광운광운이");

        // 비동기적으로 고객들이 행동하게 하기 위한 스케줄러 설정
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(customers.size());
        for (Customer customer : customers) {
            scheduler.scheduleAtFixedRate(() -> customer.autoSelectAndOrder(menuList), 0, 30, TimeUnit.SECONDS);
        }

        // 키보드 입력을 통해 프로그램 종료
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'exit' to stop the simulation.");

        while (true) {
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
        }

        // 프로그램 종료시 스케줄러 종료
        System.out.println("Shutting down...");
        scheduler.shutdown();
        for (Customer customer : customers) {
            customer.shutdownScheduler();
        }

        System.out.println("Simulation stopped.");
        scanner.close();
    }
}
