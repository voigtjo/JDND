//package edu.udacity.java.nano.exceptions;
//
//import edu.udacity.java.nano.WebSocketChatApplication;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//import org.thymeleaf.exceptions.TemplateInputException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
//public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
//    Logger logger = LoggerFactory.getLogger(MyHandlerExceptionResolver.class);
//
//    @Override
//    public ModelAndView resolveException(HttpServletRequest request,
//                                         HttpServletResponse response, @Nullable Object o, Exception e) {
//        logger.error("### resolveException method: exception= " + e.toString());
//        String username = request.getUserPrincipal().getName();
//        ModelAndView mve = new ModelAndView("error");
//        mve.addObject("username", username);
//        mve.addObject("exception", e.toString());
//        return mve;
//    }
//}
