package edu.udacity.java.nano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {
    Logger logger = LoggerFactory.getLogger(WebSocketChatApplication.class);

    @Value("${server.port}")
    private String serverPort;
    @Value("${server.ip}")
    private String serverIp;

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        logger.info("# login method: call");
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    /**
     * Chatroom Page
     *
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        //login to chatroom.
        logger.info("# index method: call");
        if (username == null || username.length() == 0) {
            return new ModelAndView("login");
        }
        ModelAndView mav = new ModelAndView("chat");
        mav.addObject("username", username);

        // Websocket connection used from chat.html
        // var webSocket = new WebSocket(/*[[${webSocketUrl}]]*/ 'ws://localhost:8080/chat');
        // webSocket.onmessage(event)
        // to receive chat messages from server
        String webSocketUrl = "ws://" + serverIp + ":" + serverPort + "/chat";
        logger.info("webSocketUrl= " + webSocketUrl);
        mav.addObject("webSocketUrl", webSocketUrl);
        return mav;
    }

    @ExceptionHandler
    public ModelAndView handlerException(Exception e) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e.toString());
        e.printStackTrace();
        return mav;
    }
}
