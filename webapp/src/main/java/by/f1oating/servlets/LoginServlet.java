package by.f1oating.servlets;

import by.f1oating.dto.UserDto;
import by.f1oating.entity.User;
import by.f1oating.service.UserService;
import by.f1oating.utils.JspHelper;
import by.f1oating.validator.Error;
import by.f1oating.validator.ValidationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.pathOf("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("user_login");
        String password = req.getParameter("user_password");
        var validationResult = new ValidationResult();
        Optional<UserDto> userOptioanl = UserService.getInstance().findByLogin(login);

        if(userOptioanl.isPresent()){
            UserDto user = UserService.getInstance().findByLogin(login).get();
            if(user.user_password().equals(password)){
                req.getSession().setAttribute("user", user);
                resp.sendRedirect("/");
            }else{
                validationResult.add(
                        Error.of("incorrect.password", "Password is incorrect"));
                req.setAttribute("errors", validationResult.getErrors());
                doGet(req, resp);
            }
        }else{
            validationResult.add(Error.of("no.login.found", "Login is incorrect"));
            req.setAttribute("errors", validationResult.getErrors());
            doGet(req, resp);
        }
    }
}
