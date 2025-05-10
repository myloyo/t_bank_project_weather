package ru.tedusar.entity;

public class UserClass {
    private Integer idUser;
    private String name;
    private String email;
    private String password;

    public UserClass(Integer id_user, String name, String email, String password) {
        this.idUser = id_user;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Integer getIdUser() {return idUser;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public void setIdUser(Integer idUser) {this.idUser = idUser;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}

}
