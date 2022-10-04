package com.shihabmahamud.eshoppers.web;

import com.shihabmahamud.eshoppers.domain.User;
import com.shihabmahamud.eshoppers.dto.LoginDTO;
import com.shihabmahamud.eshoppers.repository.UserRepositoryImpl;
import com.shihabmahamud.eshoppers.service.UserService;
import com.shihabmahamud.eshoppers.service.UserServiceImpl;
import com.shihabmahamud.eshoppers.util.ValidationUtil;
import com.sun.net.httpserver.HttpsExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final static Logger LOGGER =
            LoggerFactory.getLogger(HomeServlet.class);
    private final UserService userService =
            new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("Serving login page");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var loginDTO = new LoginDTO(
                req.getParameter("username"),
                req.getParameter("password")
        );

        LOGGER.info("Received login data: {}", loginDTO);

        var errors = ValidationUtil.getInstance().validate(loginDTO);

        if (!errors.isEmpty()) {
            LOGGER.info("Failed to login, sending login form again");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
            return;
        }

        try {
            login(loginDTO, req);
            LOGGER.info("Login successful, redirecting to home page");
            resp.sendRedirect("/home");
        } catch (UserNotFoundException e) {
            LOGGER.error("incorrect username/password", e);

            errors.put("username", "Incorrect username/password");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

    private void login(LoginDTO loginDTO, HttpServletRequest req)
            throws UserNotFoundException {
        var user = userService.verifyUser(loginDTO);
        // get the old session and invalidate
        HttpSession oldSession = req.getSession(false);
        if (oldSession != null)
            oldSession.invalidate();

        // put use in the sessions
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);
    }
}
