package ru.tedusar.classes;

public class UserClass {
    private Integer id_user;
    private String name;
    private String email;
    private String password;

    public UserClass(Integer id_user, String name, String email, String password) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getId_user() {return id_user;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public void setId_user(Integer id_user) {this.id_user = id_user;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}

}
