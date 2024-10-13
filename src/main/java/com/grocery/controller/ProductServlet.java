package com.grocery.controller;

import com.grocery.dao.ProductDao;
import com.grocery.model.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/ProductServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	ProductDao productDao = new ProductDao();
        List<Product> productList = productDao.getAllProducts();

        // Debugging: Check if productList is null or empty
        if (productList == null || productList.isEmpty()) {
            System.out.println("No products found in the database.");
        } else {
            System.out.println("Products retrieved: " + productList.size());
        }

        request.setAttribute("productList", productList);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productName = request.getParameter("productName");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        Part productImage = request.getPart("productImage");

        // Generate unique product ID
        String productId = String.valueOf(System.currentTimeMillis());

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setPrice(Double.parseDouble(price));
        product.setDescription(description);

        // Save the image and get its path
        String imagePath = saveImage(productImage);
        product.setProductImage(imagePath);

        ProductDao productDao = new ProductDao();
        boolean success = productDao.addProduct(product);

        if (success) {
            response.sendRedirect("adminHome.jsp?success=true");
        } else {
            response.sendRedirect("addProduct.jsp?error=true");
        }
    }

    private String saveImage(Part productImage) {
        String uploadDir = getServletContext().getRealPath("") + "images";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        String fileName = productImage.getSubmittedFileName();
        String filePath = uploadDir + File.separator + fileName;

        try {
            productImage.write(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "images/" + fileName;  // Relative path for displaying the image later
    }
}
