package ch.protonmail.vladyslavbond.quizzing.web.views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import ch.protonmail.vladyslavbond.quizzing.domain.Task;

public final class ViewFactory
{
    private ViewFactory ( ) {}
    
    private static WebContext getContext (HttpServletRequest request, HttpServletResponse response)
    {
        return new WebContext(request, response, request.getServletContext(), request.getLocale());
    }
    
    public static View getTaskCompleteView (HttpServletRequest request, HttpServletResponse response, Task task)
    {
        WebContext ctx = getContext(request, response);
        ctx.setVariable("task", task);
        return new ThymeleafView ("tasks/task", ctx);
    }
}
