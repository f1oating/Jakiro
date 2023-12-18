package by.f1oating.utils;

public class JspHelper {

    private final static String JSP_PATH = "WEB-INF/jsp/%s.jsp";

    public static String pathOf(String name){
        return String.format(JSP_PATH, name);
    }

}
