package xyz.carbule8.video.pojo;

import javax.validation.constraints.NotBlank;

public class Account {
    private Integer id;

    @NotBlank(message = "{account.username.notnull}")
    private String username;

    @NotBlank(message = "{account.password.notnull}")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}