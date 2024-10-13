package com.grocery.dao;

import com.grocery.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UserDao {

    public boolean registerUser(User user) {
        String insertSQL = "INSERT INTO users (fullName, email, password, address, contact, role, customerId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String customerId = generateUniqueCustomerId(); // Generate a unique customer ID
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            if (conn == null) {
                System.out.println("Connection is null.");
                return false; // Handle null connection case
            }

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getContact());
            pstmt.setString(6, user.getRole());
            pstmt.setString(7, customerId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public User loginUser(String customerId, String password) {
        String selectSQL = "SELECT * FROM users WHERE customerId = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            if (conn == null) {
                System.out.println("Connection is null.");
                return null; // Handle null connection case
            }

            pstmt.setString(1, customerId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address"),
                        rs.getString("contact"),
                        rs.getString("role"),
                        rs.getString("customerId")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error logging in user: " + e.getMessage());
        }
        return null; // Return null if user not found
    }
    
    public boolean updateUser(User user) {
        String updateSQL = "UPDATE users SET fullName = ?, email = ?, password = ?, " +
                           "address = ?, contact = ?, profileImage = ? WHERE customerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            if (conn == null) {
                System.out.println("Connection is null.");
                return false;
            }

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getContact());
            pstmt.setString(6, user.getProfileImage());
            pstmt.setString(7, user.getCustomerId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    private String generateUniqueCustomerId() {
        String customerId;
        while (true) {
            customerId = String.format("%06d", new Random().nextInt(1000000));
            if (!customerIdExists(customerId)) {
                break; // Found a unique customer ID
            }
        }
        return customerId;
    }

    private boolean customerIdExists(String customerId) {
        String selectSQL = "SELECT COUNT(*) FROM users WHERE customerId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            if (conn == null) {
                System.out.println("Connection is null.");
                return false; // Handle null connection case
            }

            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if ID exists
            }
        } catch (SQLException e) {
            System.out.println("Error checking customer ID: " + e.getMessage());
        }
        return false; // ID does not exist
    }
}
