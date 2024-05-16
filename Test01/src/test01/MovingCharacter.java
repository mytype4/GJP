package test01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MovingCharacter extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private int x = 50;
    private int y = 50;
    private final int STEP = 5;  // 캐릭터 이동 속도
    private final int RECT_SIZE = 20;  // 빨간 네모의 크기
    private boolean leftPressed, rightPressed, upPressed, downPressed;
    private Timer timer;
    private List<Restaurant> restaurants; // 레스토랑 리스트
    private JFrame frame; // JFrame 객체를 저장

    public MovingCharacter(List<Restaurant> restaurants, JFrame frame) {
        this.restaurants = restaurants;
        this.frame = frame;
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
                // 레스토랑 객체들의 랜덤 위치 설정
                for (Restaurant restaurant : restaurants) {
                    setRandomPosition(restaurant);
                }
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

            // 기존 음식점들과 겹치는지 확인
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

    // 특정 레스토랑 근처에 위치 설정
    public void setPositionNearRestaurant(Restaurant restaurant) {
        Random rand = new Random();
        int randX, randY;
        boolean overlap;
        int attempts = 0;
        int maxAttempts = 100; // 최대 시도 횟수

        do {
            overlap = false;
            randX = restaurant.getX() + restaurant.getSize() + 10 + rand.nextInt(50);
            randY = restaurant.getY() + restaurant.getSize() + 10 + rand.nextInt(50);

            // 화면 경계를 넘어가지 않도록 x, y 좌표 제한
            if (randX < 0) randX = 0;
            if (randX > getWidth() - RECT_SIZE) randX = getWidth() - RECT_SIZE;
            if (randY < 0) randY = 0;
            if (randY > getHeight() - RECT_SIZE) randY = getHeight() - RECT_SIZE;

            // 기존 음식점들과 겹치는지 확인
            for (Restaurant r : restaurants) {
                if (r != restaurant && Math.abs(r.getX() - randX) < restaurant.getSize() && Math.abs(r.getY() - randY) < restaurant.getSize()) {
                    overlap = true;
                    break;
                }
            }
            attempts++;
        } while (overlap && attempts < maxAttempts);

        // 빨간 네모의 위치 설정
        this.x = randX;
        this.y = randY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 빨간 네모를 그립니다.
        g.setColor(Color.RED); // 그리기 색상을 빨간색으로 설정
        g.fillRect(x, y, RECT_SIZE, RECT_SIZE);  // 빨간 네모를 (x, y) 좌표에 RECT_SIZE 크기로 그리기

        // 모든 음식점 객체들을 검정색 네모로 그립니다.
        g.setColor(Color.BLACK); // 그리기 색상을 검정색으로 설정
        for (Restaurant restaurant : restaurants) {
            g.fillRect(restaurant.getX(), restaurant.getY(), restaurant.getSize(), restaurant.getSize());
            g.setColor(Color.WHITE); // 텍스트 배경을 흰색으로 설정
            g.fillRect(restaurant.getX(), restaurant.getY() - 15, restaurant.getSize(), 15); // 텍스트 배경 네모
            g.setColor(Color.BLACK); // 텍스트 색상을 검정으로 설정
            g.drawString(restaurant.getName(), restaurant.getX() + 5, restaurant.getY() - 5); // 레스토랑 이름을 네모 위에 출력
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (leftPressed) x -= STEP; // 왼쪽 키가 눌린 상태이면 x 좌표를 STEP만큼 감소
        if (rightPressed) x += STEP; // 오른쪽 키가 눌린 상태이면 x 좌표를 STEP만큼 증가
        if (upPressed) y -= STEP; // 위쪽 키가 눌린 상태이면 y 좌표를 STEP만큼 감소
        if (downPressed) y += STEP; // 아래쪽 키가 눌린 상태이면 y 좌표를 STEP만큼 증가

        // 화면 경계를 넘어가지 않도록 x, y 좌표 제한
        if (x < 0) x = 0;
        if (x > getWidth() - RECT_SIZE) x = getWidth() - RECT_SIZE;
        if (y < 0) y = 0;
        if (y > getHeight() - RECT_SIZE) y = getHeight() - RECT_SIZE;

        checkCollision(); // 충돌 감지 로직 호출
        repaint(); // 화면을 다시 그리도록 요청
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
        timer.stop(); // 타이머 정지
        RestaurantInterior interiorPanel = new RestaurantInterior(restaurant, frame, this);
        frame.setContentPane(interiorPanel);
        frame.revalidate();
        interiorPanel.requestFocusInWindow(); // 내부 화면에서 키 입력을 받을 수 있도록 포커스 설정
        interiorPanel.startTimer(); // 레스토랑 내부에서 타이머 시작
    }

    public void startTimer() {
        timer.start(); // 타이머 시작
    }

    public void stopTimer() {
        timer.stop(); // 타이머 정지
    }

    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        
        // Customer 객체 리스트 생성
        customers.add(new Customer("Customer1"));
        customers.add(new Customer("Customer2"));
        customers.add(new Customer("Customer3"));

        // Owner 객체 리스트 생성
        owners.add(new Owner("Owner1"));
        owners.add(new Owner("Owner2"));
        owners.add(new Owner("Owner3"));

        // 레스토랑 리스트 생성
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(owners.get(0).createRestaurant("Restaurant1"));
        restaurants.add(owners.get(0).createRestaurant("Restaurant2"));
        restaurants.add(owners.get(1).createRestaurant("Restaurant3"));
        restaurants.add(owners.get(1).createRestaurant("Restaurant4"));
        restaurants.add(owners.get(2).createRestaurant("Restaurant5"));
        restaurants.add(owners.get(2).createRestaurant("Restaurant6"));

        JFrame frame = new JFrame("Moving Character");
        MovingCharacter movingCharacter = new MovingCharacter(restaurants, frame);
        frame.add(movingCharacter);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}