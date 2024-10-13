package com.grocery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.grocery.model.CartItem;

@WebServlet("/InvoiceServlet")
public class InvoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch cart items from the session
        List<CartItem> cartItems = (List<CartItem>) request.getSession().getAttribute("cartItems");
        double totalAmount = 0;

        // Set response type and content
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Invoice</title></head><body>");
        out.println("<h1>Your Invoice</h1>");
        out.println("<table border='1'><tr><th>Product Name</th><th>Quantity</th><th>Price</th></tr>");

        // Generate the invoice details
        if (cartItems != null && !cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                out.println("<tr>");
                out.println("<td>" + item.getProductName() + "</td>");
                out.println("<td>" + item.getQuantity() + "</td>");
                out.println("<td>₹" + (item.getPrice() * item.getQuantity()) + "</td>");
                out.println("</tr>");
                totalAmount += item.getPrice() * item.getQuantity();
            }
        } else {
            out.println("<tr><td colspan='3'>No items in the cart</td></tr>");
        }

        out.println("</table>");
        out.println("<h3>Total Amount: ₹" + totalAmount + "</h3>");
        out.println("<form action='DownloadInvoiceServlet' method='post'>");
        out.println("<input type='hidden' name='cartItems' value='" + cartItems + "' />");
        out.println("<button type='submit'>Download Invoice</button>");
        out.println("</form>");
        out.println("</body></html>");
    }
}
