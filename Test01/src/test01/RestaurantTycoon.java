package test01;

import javax.swing.*;
import java.awt.*;

public class RestaurantTycoon extends JFrame {
    private static final long serialVersionUID = 1L;
	private RestaurantPanel restaurantPanel;

    public RestaurantTycoon() {
        setTitle("Restaurant Tycoon Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        restaurantPanel = new RestaurantPanel();
        add(restaurantPanel);

        new Timer(10, e -> restaurantPanel.repaint()).start(); // 10ms로 타이머 설정
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RestaurantTycoon frame = new RestaurantTycoon();
            frame.setVisible(true);
        });
    }
}

class RestaurantPanel extends JPanel {
    private static final long serialVersionUID = 1L;
	private Customer[] customers;
    private int currentCustomerIndex;

    public RestaurantPanel() {
        customers = new Customer[3]; // 손님 수 3명으로 설정
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(i);
        }
        currentCustomerIndex = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRestaurant(g);

        for (int i = 0; i <= currentCustomerIndex && i < customers.length; i++) {
        	Customer customer = customers[i];
            customer.move();
            customer.draw(g);
        }

        if (currentCustomerIndex < customers.length && customers[currentCustomerIndex].isAtCounter()) {
            currentCustomerIndex++;
        }
    }

    private void drawRestaurant(Graphics g) {
        // 카운터 그리기
        g.setColor(Color.GRAY);
        g.fillRect(300, 100, 200, 50);

        // 테이블 그리기
        g.setColor(Color.BLUE);
        g.fillRect(150, 300, 100, 100);
        g.fillRect(550, 300, 100, 100);
        g.fillRect(150, 450, 100, 100);
        g.fillRect(550, 450, 100, 100);
    }
}