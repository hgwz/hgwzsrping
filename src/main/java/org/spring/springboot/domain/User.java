package org.spring.springboot.domain;

import java.io.Serializable;


/**
 * 城市实体类
 *
 */
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    /**
     * 用户名
     */
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                '}';
    }
}
