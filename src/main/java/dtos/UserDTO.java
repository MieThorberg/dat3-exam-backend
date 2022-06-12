package dtos;

import entities.User;

import java.util.List;

public class UserDTO {
    private String userName;
    private List<String> roles;
    private String userPass;

    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.roles = user.getRolesAsStrings();
        this.userPass = user.getUserPass();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}
