package by.f1oating.servlets;

import by.f1oating.dto.UserDto;
import by.f1oating.exception.DataBaseException;
import by.f1oating.exception.ValidationException;
import by.f1oating.service.UserService;
import by.f1oating.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.pathOf("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_name = req.getParameter("user_name");
        String user_login = req.getParameter("user_login");
        String user_password = req.getParameter("user_password");

        try{
            var user = UserService.getInstance().save(new UserDto(
                    null, user_name, user_login, user_password, "user"
            )).get();
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/");
        }catch (ValidationException e){
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }catch (DataBaseException e){
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }

}
