package model;

import java.io.Serializable;

public class user implements Serializable {

    private int userID;
    private String name;
    private String phone;
    private String gender;
    private int isReceptionist;
    private String password;

    public user (){

    }

    public user(int userID,String name,String phone,String gender,int isReceptionist, String password){

        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.isReceptionist = isReceptionist;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "user{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", isReceptionist=" + isReceptionist +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsReceptionist() {
        return isReceptionist;
    }

    public void setIsReceptionist(int isReceptionist) {
        this.isReceptionist = isReceptionist;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
