package com.example.newversion;

public class user {
    public user(String email, String userId, int phoneNumber, String name) {

    }

    public user(String email, String userId, String phoneNumber, String name) {
    }

    public class User {
        private String email;
        private String userId;
        private String phoneNumber;
        private String name;

        public User() {
        }

        public User(String email, String userId, String phoneNumber, String name) {
            this.email = email;
            this.userId = userId;
            this.phoneNumber = phoneNumber;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public String getUserId() {
            return userId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getName() {
            return name;
        }
    }

}
