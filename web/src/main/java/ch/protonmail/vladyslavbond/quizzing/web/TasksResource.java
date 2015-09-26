package ch.protonmail.vladyslavbond.quizzing.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.quizzing.controllers.*;
import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;
import ch.protonmail.vladyslavbond.quizzing.web.views.TaskCompleteView;

import static ch.protonmail.vladyslavbond.quizzing.web.QuizzingApplication.*;

@Path("/tasks")
public enum TasksResource
{
    INSTANCE;
    
    private TasksResource ( ) {}

    @POST
    @Path("/new")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public Response taskCreate (@FormParam("type") Integer idOfTaskType, @FormParam("description") String description) 
            throws URISyntaxException, ControllerException
    {
        Tasks controller = Controllers.<Tasks>getInstance(Tasks.class);
        Task task = controller.create(INSTRUCTOR, idOfTaskType, description);
        
        if (task == null || task.equals(Task.EMPTY))
        {
            return Response.serverError( ).build( );
        }
        
        /* Since there is only one exam and only one pool in the demo,
         * newly created task automatically added to default pool.
         * Remove it in production.
         */
        Pools poolsController = Controllers.<Pools>getInstance(Pools.class);
        Pool pool = poolsController.updateTaskAdd(INSTRUCTOR, POOL, task);
        
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return Response.serverError( ).build( );
        }
        Long idOfTask = Long.valueOf(task.getId( ).toString( ));
        return Response.seeOther(new URI (String.format("/tasks/%d", idOfTask))).build( );   
    }
    
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response taskCreate ( )
    {
        StringBuilder html = new StringBuilder ( );
        html.append("<!DOCTYPE html><html><head><title>New task.</title></head><body><main>");
        html.append("<h1>New task.</h1>");
        html.append("<form action='/tasks/new' method='POST'>");
        html.append("<fieldset>");
        html.append("<legend>Properties of a task.</legend>");
        html.append("<label>Type of a task.");
        html.append("<select name='type'>");
        for (TaskType taskType : TaskType.values( ))
        {
            Integer idOfTaskType = Integer.valueOf(taskType.getId( ).toString( ));
            html.append(String.format("<option value='%d'>%s</option>", idOfTaskType, taskType.toString( )));
        }
        html.append("</select>");
        html.append("</label>");
        html.append("<label>Description of a task.");
        html.append("<textarea name='description'></textarea>");
        html.append("</label>");
        html.append("</fieldset>");
        html.append("<input type='submit'>");
        html.append("</form></main></body></html>");
        return Response.ok( ).entity(html.toString( )).build( );
    }
    
    @GET
    @Path("/{id : \\d+}")
    @Produces(MediaType.TEXT_HTML)
    public Response taskRetrieve (@PathParam("id") Integer idOfTask)
        throws ControllerException
    {
        Tasks controller = Controllers.<Tasks>getInstance(Tasks.class);
        Task task = Task.EMPTY;
            task = controller.retrieve(idOfTask);
        if (task == null || task.equals(Task.EMPTY))
        {
            return Response.status(Status.NOT_FOUND).build( );
        }
        return Response.ok( ).entity(new TaskCompleteView(task)).build( );
    }
    
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id : \\d+}/update")
    public Response taskUpdate (@PathParam("id") Integer idOfTask, @FormParam("description") String description) 
            throws URISyntaxException, TasksControllerException
    {
        Tasks controller = Controllers.getInstance(Tasks.class);
        Task task = controller.retrieve(NumericIdentificator.<Task>valueOf(idOfTask));
        task = controller.update(INSTRUCTOR, task, description);
        if (task == null || task.equals(Option.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI(String.format("/tasks/%d", idOfTask))).build( );
    }
}
