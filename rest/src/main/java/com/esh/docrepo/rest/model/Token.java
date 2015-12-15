package com.esh.docrepo.rest.model;

import java.io.Serializable;

public class Token implements Serializable {

    private static final long serialVersionUID = -3918540521453737284L;

    private String token;

    public Token() {}

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
