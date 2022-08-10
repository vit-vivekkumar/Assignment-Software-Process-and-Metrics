package application;

import java.io.Serializable;

public class Message implements Serializable
{

    private String msgProcessTime;
    private String userName;
    private String message;
    private final static long serialVersionUID = 2680085127210975752L;

    public String getMsgProcessTime() {
        return msgProcessTime;
    }

    public void setMsgProcessTime(String msgProcessTime) {
        this.msgProcessTime = msgProcessTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
