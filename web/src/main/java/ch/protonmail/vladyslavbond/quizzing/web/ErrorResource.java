package ch.protonmail.vladyslavbond.quizzing.web;

import java.io.*;

import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.*;

@Provider
public enum ErrorResource
implements ExceptionMapper<Exception>
{
    INSTANCE ( );
    
    private ErrorResource ( ) {}

    @Override
    public Response toResponse (Exception e)
    {
        StringWriter stringWriter = new StringWriter ( );
        PrintWriter printWriter = new PrintWriter (stringWriter);
        e.printStackTrace(printWriter);
        return Response.status(Status.BAD_REQUEST).entity(stringWriter.toString( )).type(MediaType.TEXT_PLAIN).build( );
    }
}
