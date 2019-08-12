package edu.udacity.java.nano;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        //TODO: add code for login to chatroom.
        logger.info("# index method: call");
        if (StringUtils.isEmpty(username)) {
            username = "NO user";
        }
        ModelAndView mav = new ModelAndView("chat");
        mav.addObject("username", username);
        String webSocketUrl = "ws://"+ InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+request.getContextPath()+"/chat";
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
