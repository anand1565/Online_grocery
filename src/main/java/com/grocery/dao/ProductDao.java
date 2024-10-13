package com.grocery.dao;

import com.grocery.model.Product;
import com.grocery.dao.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
	public boolean addProduct(Product product) {
        String insertSQL = "INSERT INTO products (productId, productName, price, description, productImage) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
        	pstmt.setString(1, product.getProductId());
            pstmt.setString(2, product.getProductName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setString(4, product.getDescription());
            pstmt.setString(5, product.getProductImage());
            pstmt.executeUpdate();
            return true; // Product added successfully
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false; // Error adding product
        }
    }

    // Method to retrieve all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectSQL = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setProductImage(rs.getString("productImage"));
                products.add(product); // Add the product to the list
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return products; // Return the list of products
    }
    
    // Searching a product
    public List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE productName LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                    rs.getString("productId"),
                    rs.getString("productName"),
                    rs.getDouble("price"),
                    rs.getString("description"),
                    rs.getString("productImage")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    // Method to retrieve a specific product by its ID
    public Product getProductById(String productId) {
        String selectSQL = "SELECT * FROM products WHERE productId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
             
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setProductImage(rs.getString("productImage"));
                return product; // Return the found product
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving product by ID: " + e.getMessage());
        }
        return null; // Return null if product not found
    }
}
