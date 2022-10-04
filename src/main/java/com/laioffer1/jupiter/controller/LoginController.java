package com.laioffer1.jupiter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer1.jupiter.entity.request.LoginRequestBody;
import com.laioffer1.jupiter.entity.response.LoginResponseBody;
import com.laioffer1.jupiter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody LoginRequestBody body, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstname = loginService.verifyLogin(body.getUserId(), body.getPassword());

        // Create a new session for the user if user ID and password are correct, otherwise return Unauthorized error.
        if (!firstname.isEmpty()) {
            // Create a new session, put user ID as an attribute into the session object, and set the expiration time to 600 seconds.
            HttpSession session = request.getSession();
            session.setAttribute("user_id", body.getUserId());
            session.setMaxInactiveInterval(600);

            LoginResponseBody loginResponseBody = new LoginResponseBody(body.getUserId(), firstname);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(loginResponseBody));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
