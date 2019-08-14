package edu.udacity.java.nano.chat;


/**
 * WebSocket message model
 */
public class Message {

    private MessageType type;
    private String username;
    private String msg;
    private int onlineCount;


    public Message() {
    }

    public Message(MessageType type, String username, String msg, int onlineCount) {
        this.type = type;
        this.username = username;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }


    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", msg='" + msg + '\'' +
                ", onlineCount=" + onlineCount +
                '}';
    }
}
