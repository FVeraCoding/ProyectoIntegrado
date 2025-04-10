package com.backend.elex.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponseVO implements Serializable{
	
	@JsonProperty("token")
    private String token;

    public AuthResponseVO() {}

    public AuthResponseVO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
