package test01;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class RestaurantInterior extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Restaurant restaurant;
    private int redRectX; // 빨간 네모의 초기 x 좌표
    private int redRectY; // 빨간 네모의 초기 y 좌표
    private final int RECT_SIZE = 20; // 빨간 네모의 크기
    private final int STEP = 5;  // 캐릭터 이동 속도
    private Timer timer;
    private boolean leftPressed, rightPressed, upPressed, downPressed;
    private JFrame frame;
    private MovingCharacter movingCharacterPanel;

    // 출구 크기 설정
    private final int EXIT_WIDTH = 100; // 출구의 너비
    private final int EXIT_HEIGHT = 20; // 출구의 높이

    public RestaurantInterior(Restaurant restaurant, JFrame frame, MovingCharacter movingCharacterPanel) {
        this.restaurant = restaurant;
        this.frame = frame;
        this.movingCharacterPanel = movingCharacterPanel;
        setBackground(Color.WHITE);

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

        // UI가 초기화된 후에 빨간 네모 초기 위치 설정: 출구보다 살짝 위에 위치
        SwingUtilities.invokeLater(() -> {
            redRectX = (getWidth() - RECT_SIZE) / 2;
            redRectY = getHeight() - EXIT_HEIGHT - RECT_SIZE - 10; // 출구보다 10픽셀 위
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString("Welcome to " + restaurant.getName(), 20, 20);

        // 빨간 네모를 그립니다.
        g.setColor(Color.RED);
        g.fillRect(redRectX, redRectY, RECT_SIZE, RECT_SIZE); // 빨간 네모 크기 20x20

        // 출구를 그립니다 (화면 하단에 위치한 직사각형)
        g.setColor(Color.BLACK);
        g.fillRect((getWidth() - EXIT_WIDTH) / 2, getHeight() - EXIT_HEIGHT, EXIT_WIDTH, EXIT_HEIGHT); // 하단 출구
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (leftPressed) redRectX -= STEP;
        if (rightPressed) redRectX += STEP;
        if (upPressed) redRectY -= STEP;
        if (downPressed) redRectY += STEP;

        // 화면 경계를 넘어가지 않도록 x, y 좌표 제한
        if (redRectX < 0) redRectX = 0;
        if (redRectX > getWidth() - RECT_SIZE) redRectX = getWidth() - RECT_SIZE;
        if (redRectY < 0) redRectY = 0;
        if (redRectY > getHeight() - RECT_SIZE) redRectY = getHeight() - RECT_SIZE;

        checkExit(); // 출구 충돌 감지 로직 호출
        repaint(); // 화면을 다시 그리도록 요청
    }

    // 출구 충돌 감지 메서드
    private void checkExit() {
        if (redRectX >= (getWidth() - EXIT_WIDTH) / 2 &&
            redRectX <= (getWidth() + EXIT_WIDTH) / 2 - RECT_SIZE &&
            redRectY >= getHeight() - EXIT_HEIGHT - RECT_SIZE) {
            timer.stop(); // 타이머 정지
            movingCharacterPanel.setPositionNearRestaurant(restaurant);
            movingCharacterPanel.resetKeyState(); // 키 상태 초기화
            frame.setContentPane(movingCharacterPanel);
            frame.revalidate();
            movingCharacterPanel.requestFocusInWindow();
            movingCharacterPanel.startTimer(); // 원래 화면에서 타이머 재시작
        }
    }

    public void startTimer() {
        timer.start(); // 타이머 시작
    }

    public void stopTimer() {
        timer.stop(); // 타이머 정지
    }
}