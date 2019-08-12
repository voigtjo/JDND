package edu.udacity.java.nano.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        for (String sessionId : onlineSessions.keySet()){
            Session session = onlineSessions.get(sessionId);
            session.getAsyncRemote().sendText(msg);
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) throws JsonProcessingException {
        //TODO: add on open connection.
        logger.info("# onOpen method: call; session.id= " + session.getId());
        onlineSessions.put(session.getId(), session);
        Message message = new Message();
        message.setType(Message.ENTER);
        message.setOnlineCount(onlineSessions.size());
        ObjectMapper mapper = new ObjectMapper();
        String msg = mapper.writeValueAsString(message);
        sendMessageToAll(msg);

    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException {
        //TODO: add send message.
        logger.info("# onMessage method: call; jsonStr= " + jsonStr + ", session.id= " + session.getId());
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(jsonStr, Message.class);
        message.setType(Message.CHAT);
        message.setOnlineCount(onlineSessions.size());
        String msg = mapper.writeValueAsString(message);
        sendMessageToAll(msg);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) throws JsonProcessingException {
        //TODO: add close connection.
        logger.info("# onClose method: call; session.id= " + session.getId());
        onlineSessions.remove(session.getId());
        Message message = new Message();
        message.setType(Message.LEAVE);
        message.setOnlineCount(onlineSessions.size());
        ObjectMapper mapper = new ObjectMapper();
        String msg = mapper.writeValueAsString(message);
        sendMessageToAll(msg);
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
