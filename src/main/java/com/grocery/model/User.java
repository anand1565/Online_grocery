package com.grocery.model;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String contact;
    private String role;
    private String customerId;
    private String profileImage;  // New field for storing the profile image URL

    // Constructor with all fields
    public User(String fullName, String email, String password, String address, 
                String contact, String role, String customerId, String profileImage) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.contact = contact;
        this.role = role;
        this.customerId = customerId;
        this.profileImage = profileImage;
    }

    // Constructor without profile image (optional)
    public User(String fullName, String email, String password, String address, 
                String contact, String role, String customerId) {
        this(fullName, email, password, address, contact, role, customerId, null);
    }

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

//    // Optional: Override toString() for better debugging/logging
//    @Override
//    public String toString() {
//        return "User{" +
//                "fullName='" + fullName + '\'' +
//                ", email='" + email + '\'' +
//                ", address='" + address + '\'' +
//                ", contact='" + contact + '\'' +
//                ", role='" + role + '\'' +
//                ", customerId='" + customerId + '\'' +
//                ", profileImage='" + profileImage + '\'' +
//                '}';
//    }
}
