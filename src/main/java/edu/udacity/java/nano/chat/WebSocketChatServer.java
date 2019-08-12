package edu.udacity.java.nano.chat;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

    Logger logger = LoggerFactory.getLogger(WebSocketChatServer.class);
    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) {
        //TODO: add send message method.
        onlineSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) {
        //TODO: add on open connection.
        logger.info("# onOpen method: call; session.id= " + session.getId());
        onlineSessions.put(session.getId(), session);
        sendMessageToAll(Message.jsonStr(Message.ENTER, "", "", onlineSessions.size()));

    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) {
        //TODO: add send message.
        logger.info("# onMessage method: call; jsonStr= " + jsonStr + ", session.id= " + session.getId());
        Message message = JSON.parseObject(jsonStr, Message.class);
        sendMessageToAll(Message.jsonStr(Message.CHAT, message.getUsername(), message.getMsg(), onlineSessions.size()));
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) {
        //TODO: add close connection.
        logger.info("# onClose method: call; session.id= " + session.getId());
        onlineSessions.remove(session.getId());
        sendMessageToAll(Message.jsonStr(Message.LEAVE, "", "", onlineSessions.size()));
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("### onError method: call; session.id= " + session.getId() + "error= " + error.toString());
        error.printStackTrace();
    }

}
