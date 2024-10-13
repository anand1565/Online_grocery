package com.grocery.controller;

import com.grocery.dao.ProductDao;
import com.grocery.model.CartItem;
import com.grocery.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forwarding to cart.jsp to display the cart items
        HttpSession session = request.getSession();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        request.setAttribute("cartItems", cartItems);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String productId = request.getParameter("productId");

        // Get the current session
        HttpSession session = request.getSession();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        // Initialize cart items if it doesn't exist
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        if ("remove".equals(action)) {
            // Remove the item from the cart
            cartItems.removeIf(item -> item.getProductId().equals(productId));
        } else {
            // Fetch product details from the database
            ProductDao productDao = new ProductDao();
            Product product = productDao.getProductById(productId);

            if (product != null) {
                if ("increment".equals(action)) {
                    // Increment quantity
                    for (CartItem item : cartItems) {
                        if (item.getProductId().equals(productId)) {
                            item.setQuantity(item.getQuantity() + 1);
                            break;
                        }
                    }
                } else if ("decrement".equals(action)) {
                    // Decrement quantity, ensure it does not go below 1
                    for (CartItem item : cartItems) {
                        if (item.getProductId().equals(productId) && item.getQuantity() > 1) {
                            item.setQuantity(item.getQuantity() - 1);
                            break;
                        }
                    }
                } else {
                    // Default action: add to cart
                    boolean found = false;
                    for (CartItem item : cartItems) {
                        if (item.getProductId().equals(productId)) {
                            item.setQuantity(item.getQuantity() + 1);
                            found = true;
                            break;
                        }
                    }

                    // If not found, create a new CartItem and add to the cart
                    if (!found) {
                        CartItem newItem = new CartItem();
                        newItem.setProductId(product.getProductId());
                        newItem.setProductName(product.getProductName());
                        newItem.setPrice(product.getPrice());
                        newItem.setQuantity(1); // Start with quantity 1
                        newItem.setProductImage(product.getProductImage());
                        cartItems.add(newItem);
                    }
                }
            }
        }

        // Save the updated cart items back to the session
        session.setAttribute("cartItems", cartItems);
        session.setAttribute("cartSize", cartItems.size());
        // Redirect to cart.jsp to display the cart
//        response.sendRedirect("ProductServlet");
        if ("remove".equals(action)) {
            response.sendRedirect("CartServlet?message=Item removed successfully");
        } else if ("increment".equals(action)) {
            response.sendRedirect("CartServlet?message=Item incremented successfully");
        } else if ("decrement".equals(action)) {
            response.sendRedirect("CartServlet?message=Item decremented successfully");
        } else {
            response.sendRedirect("ProductServlet?message=Item added to cart");
        }
    }
}
