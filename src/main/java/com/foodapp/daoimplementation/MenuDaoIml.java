package com.foodapp.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.foodapp.dao.MenuInterface;
import com.foodapp.model.Menu;
import com.util.Dbconnection;

public class MenuDaoIml implements MenuInterface{

	private static final String INSERT_QUERY = "INSERT INTO `menu` (`restaurantId`, `itemName`, `description`, `price`, `isAvailable`, `rating`, `imagePath`) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_QUERY = "SELECT * FROM `menu` WHERE `menuId`=?";
	private static final String UPDATE_QUERY = "UPDATE `menu` SET `restaurantId`=?, `itemName`=?, `description`=?, `price`=?, `isAvailable`=?, `rating`=?, `imagePath`=? WHERE `menuId`=?";
	private static final String DELETE_QUERY = "DELETE FROM `menu` WHERE `menuId`=?";
	private static final String GET_ALL_QUERY = "SELECT * FROM `menu`";
	private static final String GET_BY_RESTAURANT_QUERY = "SELECT * FROM `menu` WHERE `restaurantId` = ?";
	
	@Override
	public void addMenuItem(Menu menu) {
		
		try (Connection connection = Dbconnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

			preparedStatement .setInt(1, menu.getRestaurantId());
			preparedStatement .setString(2, menu.getItemname());
			preparedStatement .setString(3, menu.getDescription());
			preparedStatement .setFloat(4, menu.getPrice());
			preparedStatement .setString(5, menu.isIsavailable());
			preparedStatement .setFloat(6, menu.getRating());
			preparedStatement .setString(7, menu.getImagepath());

			preparedStatement .executeUpdate();
			System.out.println("Menu item added successfully!");

	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

	@Override
	public Menu getMenuItem(int menuId) {
		
		Menu menu = null;
        try (Connection connection = Dbconnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_QUERY)) {

        	preparedStatement.setInt(1, menuId);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) {
                menu = new Menu(
                        res.getInt("menuId"),
                        res.getInt("restaurantId"),
                        res.getString("itemName"),
                        res.getString("description"),
                        res.getFloat("price"),
                        res.getString("isAvailable"),
                        res.getFloat("rating"),
                        res.getString("imagePath"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
	}

	@Override
	public void updateMenuItem(Menu menu) {

		try (Connection connection = Dbconnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {

			preparedStatement.setInt(1, menu.getRestaurantId());
			preparedStatement.setString(2, menu.getItemname());
			preparedStatement.setString(3, menu.getDescription());
			preparedStatement.setFloat(4, menu.getPrice());
			preparedStatement.setString(5, menu.isIsavailable());
			preparedStatement.setFloat(6, menu.getRating());
			preparedStatement.setString(7, menu.getImagepath());
			preparedStatement.setInt(8, menu.getMenuId());

			preparedStatement.executeUpdate();
			System.out.println("Menu item updated successfully!");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	}
	@Override
	public void deleteMenuItem(int menuId) {

		try (Connection connection = Dbconnection.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

			preparedStatement.setInt(1, menuId);
			preparedStatement.executeUpdate();
	            System.out.println("Menu item deleted successfully!");
	        } 
		catch (SQLException e) {
	            e.printStackTrace();
	        }

	}

	@Override
	public List<Menu> getAllMenuItems() {

		List<Menu> menuList = new ArrayList<>();
        try (Connection connection = Dbconnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet res = stmt.executeQuery(GET_ALL_QUERY)) {

            while (res.next()) {
                menuList.add(new Menu(
                        res.getInt("menuId"),
                        res.getInt("restaurantId"),
                        res.getString("itemName"),
                        res.getString("description"),
                        res.getFloat("price"),
                        res.getString("isAvailable"),
                        res.getFloat("rating"),
                        res.getString("imagePath")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }


	@Override
	public List<Menu> getAllMenuByRestaurantId(int restaurantId) {
	    List<Menu> menuList = new ArrayList<>();

	    try (Connection connection = Dbconnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_RESTAURANT_QUERY)) {

	        preparedStatement.setInt(1, restaurantId);
	        ResultSet res = preparedStatement.executeQuery();

	        while (res.next()) {
	            Menu menu = new Menu(
	                    res.getInt("menuId"),
	                    res.getInt("restaurantId"),
	                    res.getString("itemName"),
	                    res.getString("description"),
	                    res.getFloat("price"),
	                    res.getString("isAvailable"),
	                    res.getFloat("rating"),
	                    res.getString("imagePath")
	            );
	            menuList.add(menu);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return menuList;
	}

	

}
