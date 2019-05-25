package com.esprit.entity.user;

/**
 *
 * @author Dalli
 */
public class User {

    private int id_user;
    private String username;
    private String email;
    private String first_name;
    private String last_name;
    private String address;
    private String status;
    private String phone;
    private String roles;
    private String password;
    private String devisName;

    public User() {
    }

    public User(int id_user, String username, String email, String first_name, String last_name, String address, String status, String phone, String roles, String password, String devisName) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.status = status;
        this.phone = phone;
        this.roles = roles;
        this.password = password;
        this.devisName = devisName;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevisName() {
        return devisName;
    }

    public void setDevisName(String devisName) {
        this.devisName = devisName;
    }

    @Override
    public String toString() {
        return "User{" + "id_user=" + id_user + ", username=" + username + ", email=" + email + ", first_name=" + first_name + ", last_name=" + last_name + ", address=" + address + ", status=" + status + ", phone=" + phone + ", roles=" + roles + ", password=" + password + ", devisName=" + devisName + '}';
    }

}
