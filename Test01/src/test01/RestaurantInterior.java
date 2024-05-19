package test01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

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
    private Me me;

    // 출구 크기 설정
    private final int EXIT_WIDTH = 100; // 출구의 너비
    private final int EXIT_HEIGHT = 20; // 출구의 높이

    // 데스크 크기 및 위치 설정
    private final int DESK_WIDTH = 200;
    private final int DESK_HEIGHT = 50;
    private final int DESK_X = (800 - DESK_WIDTH) / 2; // 중앙에 위치
    private final int DESK_Y = 100; // 상단에서 100픽셀 떨어진 위치

    private boolean isMenuDialogOpen = false; // 메뉴 창이 열려있는지 여부를 추적

    public RestaurantInterior(Restaurant restaurant, JFrame frame, MovingCharacter movingCharacterPanel, Me me) {
        this.restaurant = restaurant;
        this.frame = frame;
        this.movingCharacterPanel = movingCharacterPanel;
        this.me = me;
        this.isMenuDialogOpen = true;
        setBackground(Color.WHITE);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isMenuDialogOpen) { // 메뉴 창이 켜졌을 때만 키 입력 처리
                    int key = e.getKeyCode();
                    if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {
                        int menuIndex = key - KeyEvent.VK_0;
                        orderMenu(menuIndex);
                    }
                }
            }
        });
        
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
                    case KeyEvent.VK_ESCAPE:
                        if (menuDialog != null && menuDialog.isVisible()) {
                            menuDialog.dispose();
                            isMenuDialogOpen = false; // 메뉴 창이 닫혔음을 표시
                        }
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

        // 데스크를 그립니다 (중앙에 위치한 직사각형)
        g.setColor(Color.BLACK);
        g.fillRect(DESK_X, DESK_Y, DESK_WIDTH, DESK_HEIGHT);
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
        checkDeskCollision(); // 데스크 충돌 감지 로직 호출
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

    // 데스크 충돌 감지 메서드
    private void checkDeskCollision() {
        if (redRectX < DESK_X + DESK_WIDTH &&
            redRectX + RECT_SIZE > DESK_X &&
            redRectY < DESK_Y + DESK_HEIGHT &&
            redRectY + RECT_SIZE > DESK_Y) {
            if (!isMenuDialogOpen) {
                resetKeyState(); // 키 상태 초기화
                showMenuDialog(); // 메뉴 창 표시
            }
        } else {
            isMenuDialogOpen = false; // 네모가 데스크를 벗어남
        }
    }

    // 메뉴 창 표시 메서드
    private JDialog menuDialog;
    
    private void showMenuDialog() {
        if (menuDialog == null || !menuDialog.isVisible()) {
            StringBuilder menuText = new StringBuilder("<html><body style='text-align:center; font-size:14px;'>")
                .append("<h2 style='font-size:18px;'>")
                .append(restaurant.getName())
                .append(" 메뉴</h2><div style='text-align:center;'>"); // 제목 중앙 정렬 및 글자 크기 조정

            List<Menu> menus = restaurant.getMenus();
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                menuText.append("<p style='margin: 5px 0; font-size:16px;'>")
                        .append((i + 1)).append(". ")
                        .append(menu.getName())
                        .append(": ")
                        .append(menu.getPrice())
                        .append("원</p>");
            }
            menuText.append("</div></body></html>");

            menuDialog = new JDialog(frame, "메뉴", true);
            JLabel menuLabel = new JLabel(menuText.toString());
            menuLabel.setHorizontalAlignment(SwingConstants.CENTER); // 다이얼로그 중앙 정렬
            menuDialog.add(menuLabel);
            menuDialog.setSize(400, 300); // 다이얼로그 크기 조정
            menuDialog.setLocationRelativeTo(this);

            menuDialog.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        menuDialog.dispose();
                        isMenuDialogOpen = false; // 메뉴 창이 닫혔음을 표시
                    }
                }
            });

            menuDialog.setVisible(true);
            menuDialog.requestFocusInWindow(); // 메뉴 다이얼로그가 키 이벤트를 받을 수 있도록 포커스를 요청

            isMenuDialogOpen = true; // 메뉴 창이 열렸음을 표시
        }
    }

    
    private void orderMenu(int menuIndex) {
        List<Menu> menus = restaurant.getMenus();
        if (menuIndex >= 0 && menuIndex < menus.size()) {
            Menu selectedMenu = menus.get(menuIndex);
            me.orderFood(restaurant.getName(), selectedMenu.getName());
        } else {
            System.out.println("Invalid menu selection");
        }
    }

    // 키 상태 초기화 메서드
    private void resetKeyState() {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
    }

    public void startTimer() {
        timer.start(); // 타이머 시작
    }

    public void stopTimer() {
        timer.stop(); // 타이머 정지
    }
}
