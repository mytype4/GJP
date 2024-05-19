package test01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MovingCharacter extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private final int STEP = 5;  // 캐릭터 이동 속도
    private final int RECT_SIZE = 20;  // 빨간 네모의 크기
    private boolean leftPressed, rightPressed, upPressed, downPressed;
    private Timer timer;
    private JFrame frame; // JFrame 객체를 저장
    private List<Restaurant> restaurants;
    private Me me;

    public MovingCharacter(JFrame frame) {
        this.frame = frame;
        this.restaurants = SimulationManager.getInstance().getRestaurants();
        this.me = new Me("MyCharacter");
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        leftPressed = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed = true;
                        break;
                    case KeyEvent.VK_UP:
                        upPressed = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressed = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        leftPressed = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed = false;
                        break;
                    case KeyEvent.VK_UP:
                        upPressed = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        downPressed = false;
                        break;
                }
            }
        });

        timer = new Timer(10, this);  // 타이머 간격을 10ms로 설정
        timer.start();

        // 컴포넌트의 크기 변경 이벤트를 처리
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Restaurant restaurant : restaurants) {
                    setRandomPosition(restaurant);
                }
                setInitialCharacterPosition();  // 레스토랑 객체가 설정된 후 빨간 네모의 초기 위치 설정
                repaint();
            }
        });
    }

    // 키 상태 초기화 메서드
    public void resetKeyState() {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
    }

    // Restaurant 객체의 랜덤 위치 설정
    private void setRandomPosition(Restaurant restaurant) {
        Random rand = new Random();
        int randX, randY;
        boolean overlap;

        do {
            overlap = false;
            randX = rand.nextInt(getWidth() - restaurant.getSize());
            randY = rand.nextInt(getHeight() - restaurant.getSize());

            for (Restaurant r : restaurants) {
                if (Math.abs(r.getX() - randX) < restaurant.getSize() && Math.abs(r.getY() - randY) < restaurant.getSize()) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);

        restaurant.setX(randX);
        restaurant.setY(randY);
    }

    // 빨간 네모의 초기 위치 설정
    private void setInitialCharacterPosition() {
        Random rand = new Random();
        int randX, randY;
        boolean overlap;

        do {
            overlap = false;
            randX = rand.nextInt(getWidth() - RECT_SIZE);
            randY = rand.nextInt(getHeight() - RECT_SIZE);

            for (Restaurant restaurant : restaurants) {
                if (Math.abs(restaurant.getX() - randX) < restaurant.getSize() + RECT_SIZE &&
                    Math.abs(restaurant.getY() - randY) < restaurant.getSize() + RECT_SIZE) {
                    overlap = true;
                    break;
                }
            }
        } while (overlap);

        this.x = randX;
        this.y = randY;
    }
    
    public void setPositionNearRestaurant(Restaurant restaurant) {
        Random rand = new Random();
        int randX, randY;
        boolean overlap;

        do {
            overlap = false;
            int direction = rand.nextInt(4); // 0: 상, 1: 하, 2: 좌, 3: 우
            int offset = restaurant.getSize() + RECT_SIZE;

            switch (direction) {
                case 0: // 상
                    randX = restaurant.getX() + rand.nextInt(restaurant.getSize() - RECT_SIZE);
                    randY = restaurant.getY() - offset;
                    break;
                case 1: // 하
                    randX = restaurant.getX() + rand.nextInt(restaurant.getSize() - RECT_SIZE);
                    randY = restaurant.getY() + restaurant.getSize();
                    break;
                case 2: // 좌
                    randX = restaurant.getX() - offset;
                    randY = restaurant.getY() + rand.nextInt(restaurant.getSize() - RECT_SIZE);
                    break;
                case 3: // 우
                    randX = restaurant.getX() + restaurant.getSize();
                    randY = restaurant.getY() + rand.nextInt(restaurant.getSize() - RECT_SIZE);
                    break;
                default:
                    randX = restaurant.getX();
                    randY = restaurant.getY();
            }

            if (randX < 0 || randX > getWidth() - RECT_SIZE || randY < 0 || randY > getHeight() - RECT_SIZE) {
                overlap = true;
            } else {
                for (Restaurant r : restaurants) {
                    if (Math.abs(r.getX() - randX) < r.getSize() && Math.abs(r.getY() - randY) < r.getSize()) {
                        overlap = true;
                        break;
                    }
                }
            }
        } while (overlap);

        this.x = randX;
        this.y = randY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 빨간 네모를 그립니다.
        g.setColor(Color.RED);
        g.fillRect(x, y, RECT_SIZE, RECT_SIZE);

        // 모든 음식점 객체들을 검정색 네모로 그립니다.
        g.setColor(Color.BLACK);
        for (Restaurant restaurant : restaurants) {
            g.fillRect(restaurant.getX(), restaurant.getY(), restaurant.getSize(), restaurant.getSize());
            g.setColor(Color.WHITE);
            g.fillRect(restaurant.getX(), restaurant.getY() - 15, restaurant.getSize(), 15);
            g.setColor(Color.BLACK);
            g.drawString(restaurant.getName(), restaurant.getX() + 5, restaurant.getY() - 5);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (leftPressed) x -= STEP;
        if (rightPressed) x += STEP;
        if (upPressed) y -= STEP;
        if (downPressed) y += STEP;

        if (x < 0) x = 0;
        if (x > getWidth() - RECT_SIZE) x = getWidth() - RECT_SIZE;
        if (y < 0) y = 0;
        if (y > getHeight() - RECT_SIZE) y = getHeight() - RECT_SIZE;

        checkCollision();
        repaint();
    }

    // 충돌 감지 메서드
    private void checkCollision() {
        for (Restaurant restaurant : restaurants) {
            if (x < restaurant.getX() + restaurant.getSize() &&
                x + RECT_SIZE > restaurant.getX() &&
                y < restaurant.getY() + restaurant.getSize() &&
                y + RECT_SIZE > restaurant.getY()) {
                System.out.println("Collision with: " + restaurant.getName());
                showRestaurantInterior(restaurant);
            }
        }
    }

    // 레스토랑 내부 화면으로 전환하는 메서드
    private void showRestaurantInterior(Restaurant restaurant) {
        timer.stop();
        RestaurantInterior interiorPanel = new RestaurantInterior(restaurant, frame, this, me);
        frame.setContentPane(interiorPanel);
        frame.revalidate();
        interiorPanel.requestFocusInWindow();
        interiorPanel.startTimer();
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public static void main(String[] args) {
        SimulationManager manager = SimulationManager.getInstance();

        // 고객 및 소유자 초기화
        manager.addCustomer(new Customer("아름이"));
        manager.addCustomer(new Customer("민석이"));
        manager.addCustomer(new Customer("주영이"));
        manager.addCustomer(new Customer("하랑이"));

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

        JFrame frame = new JFrame("Moving Character");
        MovingCharacter movingCharacter = new MovingCharacter(frame);
        frame.add(movingCharacter);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
