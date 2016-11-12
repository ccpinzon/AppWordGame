package edu.uptc.appwordgame.Logic;

/**
 * Created by cristhian on 7/11/16.
 */

public class User {
    private String userId;
    private String name;
    private String nickName;
    private String password;
    private int score;


    public User(String userId, String name, String nickName, String password, int score) {
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", score=" + score +
                '}';
    }
}
