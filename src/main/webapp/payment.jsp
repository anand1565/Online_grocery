<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.grocery.model.CartItem" %>

<%
    // Get cart items and total amount from the request
    String cartItemsData = request.getParameter("cartItems");
    double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
    
    String paymentMode = request.getParameter("paymentMethod");

    // Store payment details in the session
    session.setAttribute("paymentMode", paymentMode);
    session.setAttribute("totalAmount", totalAmount);

    // Parse the cart items from the serialized string
    List<CartItem> cartItems = new ArrayList<>();
    if (cartItemsData != null && !cartItemsData.isEmpty()) {
        String[] items = cartItemsData.split(";");
        for (String itemData : items) {
            String[] details = itemData.split(":");
            if (details.length == 5) {  // Ensure all fields are present
                CartItem item = new CartItem(
                    details[0],                 // productId (String)
                    details[1],                 // productName
                    Double.parseDouble(details[2]), // price
                    Integer.parseInt(details[3]),  // quantity
                    details[4]                  // productImage
                );
                cartItems.add(item);
            }
        }
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
    <jsp:include page="userNav.jsp" />
    <div class="container mt-5">
        <h1 class="text-center mb-4">Payment Options</h1>

        <div class="row justify-content-center">
            <div class="col-md-6">
                <form action="invoice.jsp" method="post">
                    <div class="form-group mb-3">
                        <label for="paymentMethod" class="form-label">Select Payment Method:</label>
                        <select name="paymentMethod" id="paymentMethod" class="form-control" required>
                            <option value="Credit Card">Credit Card</option>
                            <option value="Debit Card">Debit Card</option>
                            <option value="UPI">UPI</option>
                            <option value="Cash on Delivery">Cash on Delivery</option>
                        </select>
                    </div>

                    <input type="hidden" name="totalAmount" value="<%= totalAmount %>">

                    <%-- Serialize cart items again to pass to invoice.jsp --%>
                    <input type="hidden" name="cartItems" value="<%= cartItemsData %>">

                    <button type="submit" class="btn btn-primary w-100">Pay Now</button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
