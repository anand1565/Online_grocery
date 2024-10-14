<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.model.CartItem" %>

<%
    // Retrieve cart items and total amount from the session with null checks
    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
    Double totalAmount = (Double) session.getAttribute("totalAmount");
    if (totalAmount == null) totalAmount = 0.0; // Fallback to 0 if null

    String paymentMode = request.getParameter("paymentMode");
    if (paymentMode == null) paymentMode = "N/A"; // Handle missing payment mode

    // Clear the cart after displaying the invoice
    session.removeAttribute("cartItems");
    session.removeAttribute("totalAmount");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Invoice</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .invoice-container { max-width: 800px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        .invoice-header { text-align: center; margin-bottom: 30px; }
    </style>
</head>
<body>
    <div class="invoice-container">
        <div class="invoice-header">
            <h1>Invoice</h1>
            <p>Thank you for shopping with us!</p>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Product Name</th>
                    <th scope="col">Price</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Total</th>
                </tr>
            </thead>
            <tbody>
                <% if (cartItems != null && !cartItems.isEmpty()) { 
                    for (CartItem item : cartItems) { %>
                        <tr>
                            <td><%= item.getProductName() %></td>
                            <td>₹<%= item.getPrice() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td>₹<%= item.getPrice() * item.getQuantity() %></td>
                        </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="4" class="text-center text-danger">No items in the cart.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>



        <div class="d-flex justify-content-between">
            <h5>Total Amount:</h5>
            <h5>₹<%= totalAmount %></h5>
        </div>

        <div class="text-center mt-4">
            <a href="ProductServlet" class="btn btn-primary">Continue Shopping</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
