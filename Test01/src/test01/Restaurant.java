package test01;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    private List<Menu> menus;

    public Restaurant(String name, String location) {
        this.name = name;
        this.location = location;
        this.menus = new ArrayList<>();
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
    }

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

    // 추가 메소드 구현
}