<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.model.CartItem" %>

<%
    // Get total amount from the request
    double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
    
    // Retrieve cart items from the request
    String[] cartItemsArray = request.getParameterValues("cartItems");
    List<CartItem> cartItems = new ArrayList<>();

    for (String item : cartItemsArray) {
        String[] details = item.split(":");
        String productId = details[0];
        String productName = details[1];
        double price = Double.parseDouble(details[2]);
        int quantity = Integer.parseInt(details[3]);
        cartItems.add(new CartItem(productId, productName, price, quantity, "imagePath")); // Add productImage as needed
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Payment Options</h1>
        <form action="invoice.jsp" method="post">
            <div class="mb-3">
                <label for="paymentMethod" class="form-label">Select Payment Method:</label>
                <select id="paymentMethod" name="paymentMethod" class="form-select" required>
                    <option value="">Choose...</option>
                    <option value="Credit Card">Credit Card</option>
                    <option value="Debit Card">Debit Card</option>
                    <option value="Net Banking">Net Banking</option>
                </select>
            </div>
            <input type="hidden" name="totalAmount" value="<%= totalAmount %>">
            <%
                for (CartItem item : cartItems) {
            %>
                <input type="hidden" name="cartItems" value="<%= item.getProductId() + ':' + item.getProductName() + ':' + item.getPrice() + ':' + item.getQuantity() %>">
            <%
                }
            %>
            <button type="submit" class="btn btn-primary">Pay Now</button>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
