package test01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    private static List<Menu> menuList = new ArrayList<>(); // Static menu list
    private static List<Owner> owners = new ArrayList<>();  // Static owners list

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();

        // Customer 객체 리스트 생성
        customers.add(new Customer("아름이"));
        customers.add(new Customer("민석이"));
        customers.add(new Customer("주영이"));
        customers.add(new Customer("하랑이"));

        // Owner 객체 리스트 생성
        owners.add(new Owner("피까츄"));
        owners.add(new Owner("라이츄"));
        owners.add(new Owner("파이리"));

        // 도전하라! 식당 메뉴 추가
        owners.get(0).createRestaurant("도전하라! 식당");
        owners.get(0).addMenuToRestaurant("도전하라! 식당", "붕어빵 맛 초코파이", 10000);
        owners.get(0).addMenuToRestaurant("도전하라! 식당", "치즈 맛 사과", 11000);
        owners.get(0).addMenuToRestaurant("도전하라! 식당", "핫도그 맛 아이스크림", 12000);
        owners.get(0).addMenuToRestaurant("도전하라! 식당", "닭가슴살 맛 솜사탕", 13000);

        // 마법의 빵집 빵팡 메뉴 추가
        owners.get(0).createRestaurant("마법의 빵집 빵팡");
        owners.get(0).addMenuToRestaurant("마법의 빵집 빵팡", "꿈을 이루는 빵", 15000);
        owners.get(0).addMenuToRestaurant("마법의 빵팡", "사랑을 찾는 빵", 16000);
        owners.get(0).addMenuToRestaurant("마법의 빵집 빵팡", "복권에 당첨되는 빵", 17000);
        owners.get(0).addMenuToRestaurant("마법의 빵집 빵팡", "건강을 되찾는 빵", 18000);

        // 미래 식당 2042 메뉴 추가
        owners.get(1).createRestaurant("미래 식당 2042");
        owners.get(1).addMenuToRestaurant("미래 식당 2042", "곤충 단백질 샌드위치", 20000);
        owners.get(1).addMenuToRestaurant("미래 식당 2042", "3D 프린팅 피자", 21000);
        owners.get(1).addMenuToRestaurant("미래 식당 2042", "해조류 샐러드", 22000);
        owners.get(1).addMenuToRestaurant("미래 식당 2042", "로봇이 만든 칵테일", 23000);
        owners.get(1).addMenuToRestaurant("미래 식당 2042", "드론배달 컵라면", 24000);

        // 시간 여행자의 숨겨진 맛 메뉴 추가
        owners.get(2).createRestaurant("시간 여행자의 숨겨진 맛");
        owners.get(2).addMenuToRestaurant("시간 여행자의 숨겨진 맛", "공룡 뼈 화석 스테이크", 25000);
        owners.get(2).addMenuToRestaurant("시간 여행자의 숨겨진 맛", "미래형 영양 캡슐", 26000);
        owners.get(2).addMenuToRestaurant("시간 여행자의 숨겨진 맛", "중세 시대 왕족 만찬", 27000);

        // 악취의 향연 식당 메뉴 추가
        owners.get(2).createRestaurant("악취의 향연 식당");
        owners.get(2).addMenuToRestaurant("악취의 향연 식당", "취두부 냄새 샌드위치", 28000);
        owners.get(2).addMenuToRestaurant("악취의 향연 식당", "썩은 생선 꼬치구이", 29000);
        owners.get(2).addMenuToRestaurant("악취의 향연 식당", "땀 냄새 치즈 샐러드", 30000);

        // 불안정한 맛 전문점 메뉴 추가
        owners.get(2).createRestaurant("불안정한 맛 전문점");
        owners.get(2).addMenuToRestaurant("불안정한 맛 전문점", "딱딱한 샌드위치", 31000);
        owners.get(2).addMenuToRestaurant("불안정한 맛 전문점", "끈적끈적한 롤빵", 32000);
        owners.get(2).addMenuToRestaurant("불안정한 맛 전문점", "거친 아이스크림", 33000);
        owners.get(2).addMenuToRestaurant("불안정한 맛 전문점", "미끄러운 샐러드", 34000);

        // 비동기적으로 고객들이 행동하게 하기 위한 스케줄러 설정
        for (Customer customer : customers) {
            customer.startScheduler(); // 각 고객의 스케줄러 시작
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
        for (Customer customer : customers) {
            customer.shutdownScheduler(); // 각 고객의 스케줄러 종료
        }

        System.out.println("Simulation stopped.");
        scanner.close();
    }

    // MenuList에 메뉴 추가하는 메서드
    public static void addMenu(Menu menu) {
        menuList.add(menu);
    }

    // MenuList를 반환하는 메서드
    public static List<Menu> getMenuList() {
        return menuList;
    }

    // Owners 리스트를 반환하는 메서드
    public static List<Owner> getOwners() {
        return owners;
    }
}
