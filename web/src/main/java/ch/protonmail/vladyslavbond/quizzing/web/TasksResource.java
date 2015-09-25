package ch.protonmail.vladyslavbond.quizzing.web;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.quizzing.controllers.ControllerException;
import ch.protonmail.vladyslavbond.quizzing.controllers.Controllers;
import ch.protonmail.vladyslavbond.quizzing.controllers.Options;
import ch.protonmail.vladyslavbond.quizzing.controllers.OptionsControllerException;
import ch.protonmail.vladyslavbond.quizzing.controllers.Pools;
import ch.protonmail.vladyslavbond.quizzing.controllers.Tasks;
import ch.protonmail.vladyslavbond.quizzing.controllers.TasksControllerException;
import ch.protonmail.vladyslavbond.quizzing.domain.Instructor;
import ch.protonmail.vladyslavbond.quizzing.domain.Option;
import ch.protonmail.vladyslavbond.quizzing.domain.Pool;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskType;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

@Path("/tasks")
public enum TasksResource
{
    INSTANCE;
    
    private final static Identificator<Instructor> ID_OF_INSTRUCTOR = NumericIdentificator.<Instructor>valueOf(1);
    private final static Identificator<Pool>       ID_OF_POOL       = NumericIdentificator.<Pool>valueOf(1);
    
    private TasksResource ( ) {}

    @POST
    @Path("/new")
    @Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    public Response taskCreate (@FormParam("type") Integer idOfTaskType, @FormParam("description") String description) 
            throws URISyntaxException, ControllerException
    {
        Tasks controller = Controllers.<Tasks>getInstance(Tasks.class);
        Task task = controller.create(ID_OF_INSTRUCTOR, idOfTaskType, description);
        
        if (task == null || task.equals(Task.EMPTY))
        {
            return Response.serverError( ).build( );
        }
        
        /* Since there is only one exam and only one pool in the demo,
         * newly created task automatically added to default pool.
         * Remove it in production.
         */
        Pools poolsController = Controllers.<Pools>getInstance(Pools.class);
        poolsController.updateTaskAdd(ID_OF_POOL, task.getId( ));
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
        StringBuilder html = new StringBuilder ( );
        html.append("<!DOCTYPE html><html><head><title>Task.</title></head><body>");
        html.append(String.format("<article id='%d'>", idOfTask));
        html.append(String.format("<header><h1>Task #%d.</h1></header>", idOfTask));
        html.append(String.format("<section><h2>Description of task.</h2><p>%s</p></section>", task.getDescription( )));
        if (task.getOptions( ) != null && !task.getOptions( ).isEmpty())
        {
            html.append("<section><h2>Options.</h2>");
            for (Option option : task.getOptions( ))
            {
                Long idOfOption = Long.valueOf(option.getId( ).toString( ));
                html.append(String.format("<article id='%d'>", idOfOption));
                html.append(String.format("<header><h3><a href='/tasks/%1$d/options/%2$d'>Option #%2$d.</a></h3></header>", idOfTask, idOfOption));
                html.append("<section><h4>Message.</h4>");
                html.append(String.format("<p>%s</p>", option.getMessage( )));
                html.append("</section>");
                html.append("<section><h4>Reward.</h4>");
                html.append(String.format("<p>%d</p>", option.getReward( )));
                html.append("</section>");
                html.append("</section>");
                html.append("</article>");
            }
            html.append("</section>");
        }
        html.append("</article>");
        html.append(String.format("<section><h2>%s</h2>", "Update task description."));
        html.append(String.format("<form action='/tasks/%1$d/update' method='POST'><input type='hidden' name='id' value='%1$d' /><textarea name='description'>%2$s</textarea><input type='submit' /></form>", idOfTask, task.getDescription( )));
        html.append("</section>");
        html.append(String.format("<section><h2>%s</h2>", "Delete task."));
        html.append(String.format("<form action='/tasks/%1$d/destroy' method='POST'><input type='hidden' name='id' value='%1$d' /><input type='submit' /></form>", idOfTask));
        html.append("</section>");
        html.append("</body></html>");
        return Response.ok( ).entity(html.toString( )).build( );
    }
    
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id : \\d+}/update")
    public Response taskUpdate (@PathParam("id") Integer idOfTask, @FormParam("description") String description) 
            throws URISyntaxException, TasksControllerException
    {
        Tasks controller = Controllers.getInstance(Tasks.class);
        Task task = Task.EMPTY;
        task = controller.update(ID_OF_INSTRUCTOR, NumericIdentificator.<Task>valueOf(idOfTask), description);
        if (task == null || task.equals(Option.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI(String.format("/tasks/%d", idOfTask))).build( );
    }
}
