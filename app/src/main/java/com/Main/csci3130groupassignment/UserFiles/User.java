package com.Main.csci3130groupassignment.UserFiles;

public abstract class User {
        public String Username;
        public String Password;
        public String Role;

        public User(String Username, String password, String role) {
            this.Username = Username;
            this.Password = password;
            this.Role = role;
        }
}
