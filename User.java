class User {
    public String fullname = null;
    public String username = null;
    public String password = null;

    public User(String fullname, String username, String password) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
    }

    // Getter method to return the full name of the user
    public String getFullname() {
        return fullname;
    }

    // Setter method to set the full name of the user
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Getter method to return the username of the user
    public String getUsername() {
        return username;
    }

    // Setter method to set the username of the user
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method to return the password of the user
    public String getPassword() {
        return password;
    }

    // Setter method to set the password of the user
    public void setPassword(String password) {
        this.password = password;
    }
}
