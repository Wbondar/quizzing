package ch.protonmail.vladyslavbond.quizzing.web.views;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.WebApplicationException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

abstract class ThymeleafView implements View
{
    private final String   pathToTemplate;
    private final IContext context;

    ThymeleafView (String pathToTemplate, IContext context)
    {
        this.pathToTemplate = pathToTemplate;
        this.context        = context;
    }
    
    private final TemplateEngine getTemplateEngine ( )
    {
        return ThymeleafTemplateEngine.getInstance( );
    }
    
    @Override
    public final void write(OutputStream os) 
            throws IOException, WebApplicationException
    {
        Writer writer = new BufferedWriter(new OutputStreamWriter(os));
        writer.write(getTemplateEngine( ).process(pathToTemplate, context));
        writer.flush();
    }
}
