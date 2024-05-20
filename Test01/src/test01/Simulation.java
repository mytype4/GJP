package test01;

import java.util.Scanner;
import java.time.LocalDateTime;

public class Simulation {
    public static void main(String[] args) {
        SimulationManager manager = SimulationManager.getInstance();

        // 데이터베이스 초기화 (테이블만 생성, 기존 데이터는 유지)
        manager.getOrderDB().initializeDatabase();
        manager.getOrderDB().clearOrderTableUsingTruncate();

        // Customer 객체 리스트 생성
        manager.addCustomer(new Customer("아름이"));
        manager.addCustomer(new Customer("민석이"));
        manager.addCustomer(new Customer("주영이"));
        manager.addCustomer(new Customer("하랑이"));

        // Owner 객체 리스트 생성
        manager.addOwner(new Owner("피까츄"));
        manager.addOwner(new Owner("라이츄"));
        manager.addOwner(new Owner("파이리"));

        // 도전하라! 식당 메뉴 추가
        manager.owner("피까츄").createRestaurant("도전하라! 식당");
        manager.owner("피까츄").addMenuToRestaurant("도전하라! 식당", "붕어빵 맛 초코파이", 10000);
        manager.owner("피까츄").addMenuToRestaurant("도전하라! 식당", "치즈 맛 사과", 11000);
        manager.owner("피까츄").addMenuToRestaurant("도전하라! 식당", "핫도그 맛 아이스크림", 12000);
        manager.owner("피까츄").addMenuToRestaurant("도전하라! 식당", "닭가슴살 맛 솜사탕", 13000);

        manager.owner("피까츄").createRestaurant("마법의 빵집 빵팡");
        manager.owner("피까츄").addMenuToRestaurant("마법의 빵집 빵팡", "꿈을 이루는 빵", 15000);
        manager.owner("피까츄").addMenuToRestaurant("마법의 빵집 빵팡", "사랑을 찾는 빵", 16000);
        manager.owner("피까츄").addMenuToRestaurant("마법의 빵집 빵팡", "복권에 당첨되는 빵", 17000);
        manager.owner("피까츄").addMenuToRestaurant("마법의 빵집 빵팡", "건강을 되찾는 빵", 18000);

        // 미래 식당 2042 메뉴 추가
        manager.owner("라이츄").createRestaurant("미래 식당 2042");
        manager.owner("라이츄").addMenuToRestaurant("미래 식당 2042", "곤충 단백질 샌드위치", 20000);
        manager.owner("라이츄").addMenuToRestaurant("미래 식당 2042", "3D 프린팅 피자", 21000);
        manager.owner("라이츄").addMenuToRestaurant("미래 식당 2042", "해조류 샐러드", 22000);
        manager.owner("라이츄").addMenuToRestaurant("미래 식당 2042", "로봇이 만든 칵테일", 23000);
        manager.owner("라이츄").addMenuToRestaurant("미래 식당 2042", "드론배달 컵라면", 24000);

        // 시간 여행자의 숨겨진 맛 메뉴 추가
        manager.owner("파이리").createRestaurant("시간 여행자의 숨겨진 맛");
        manager.owner("파이리").addMenuToRestaurant("시간 여행자의 숨겨진 맛", "공룡 뼈 화석 스테이크", 25000);
        manager.owner("파이리").addMenuToRestaurant("시간 여행자의 숨겨진 맛", "미래형 영양 캡슐", 26000);
        manager.owner("파이리").addMenuToRestaurant("시간 여행자의 숨겨진 맛", "중세 시대 왕족 만찬", 27000);

        manager.owner("파이리").createRestaurant("악취의 향연 식당");
        manager.owner("파이리").addMenuToRestaurant("악취의 향연 식당", "취두부 냄새 샌드위치", 28000);
        manager.owner("파이리").addMenuToRestaurant("악취의 향연 식당", "썩은 생선 꼬치구이", 29000);
        manager.owner("파이리").addMenuToRestaurant("악취의 향연 식당", "땀 냄새 치즈 샐러드", 30000);

        manager.owner("파이리").createRestaurant("불안정한 맛 전문점");
        manager.owner("파이리").addMenuToRestaurant("불안정한 맛 전문점", "딱딱한 샌드위치", 31000);
        manager.owner("파이리").addMenuToRestaurant("불안정한 맛 전문점", "끈적끈적한 롤빵", 32000);
        manager.owner("파이리").addMenuToRestaurant("불안정한 맛 전문점", "거친 아이스크림", 33000);
        manager.owner("파이리").addMenuToRestaurant("불안정한 맛 전문점", "미끄러운 샐러드", 34000);

        // 비동기적으로 고객들이 행동하게 하기 위한 스케줄러 설정
        for (Customer customer : manager.getCustomers()) {
            customer.startScheduler(); // 각 고객의 스케줄러 시작
        }

        // 키보드 입력을 통해 프로그램 종료
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'exit' to stop the simulation.");

        while (true) {
            String input = scanner.nextLine();
            LocalDateTime currentDateTime = LocalDateTime.now();
            System.out.println("현재 시간: " + currentDateTime);
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
        }

        // 프로그램 종료시 스케줄러 종료
        System.out.println("Shutting down...");
        for (Customer customer : manager.getCustomers()) {
            customer.shutdownScheduler(); // 각 고객의 스케줄러 종료
        }

        System.out.println("Simulation stopped.");
        scanner.close();
    }
}