package com.backend.elex.vo;

public class UserRegistrationRequestVO {

	private UserVO user;
	private String password;
	
	public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
}
