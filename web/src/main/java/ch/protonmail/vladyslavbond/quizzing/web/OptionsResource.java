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
import ch.protonmail.vladyslavbond.quizzing.controllers.Tasks;
import ch.protonmail.vladyslavbond.quizzing.domain.Option;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

import static ch.protonmail.vladyslavbond.quizzing.web.QuizzingApplication.*;

@Path("/tasks/{task_id : \\d+}/options/")
public enum OptionsResource
{
    INSTANCE;
    
    private OptionsResource ( ) {}
    
    @POST
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response optionCreate (@PathParam("task_id") Integer idOfTask, @FormParam("message") String messageOfOption, @FormParam("reward") Integer reward) 
            throws URISyntaxException, ControllerException
    {
        Options controller = Controllers.getInstance(Options.class);
        Task task = Controllers.<Tasks>getInstance(Tasks.class).retrieve(NumericIdentificator.<Task>valueOf(idOfTask));
        Option option = controller.create(INSTRUCTOR, task, messageOfOption, reward);
        if (option == null || option.equals(Option.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI(String.format("/tasks/%1$d/options/%2$d", idOfTask, option.getId( ).toNumber( ).longValue( )))).build( );
    }
    
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public Response optionCreate (@PathParam("task_id") Integer idOfTask)
    {
        StringBuilder html = new StringBuilder ( );
        html.append("<!DOCTYPE html><html><head><title>New option.</title></head><body>");
        html.append("<section><header><h1>New option for task #" + idOfTask.toString( ) + ".</h1></header>");
        html.append("<form action='/tasks/" + idOfTask.toString( ) + "/options/new' method='POST'><fieldset><legend>Parameters of an option.</legend><label>Message.<input type='text' name='message' /></label><label>Reward<input type='number' name='reward'></label></fieldset><input type='submit' /></form>");
        html.append("</section>");
        html.append("</body></html>");
        return Response.ok( ).entity(html.toString( )).build( );
    }
    
    @GET
    @Path("/{option_id : \\d+}")
    @Produces(MediaType.TEXT_HTML)
    public Response optionRetrieve (@PathParam("task_id") Integer idOfTask, @PathParam ("option_id") Long idOfOption) throws OptionsControllerException 
    {
        Options controller = Controllers.<Options>getInstance(Options.class);
        Option option;
            option = controller.retrieve(NumericIdentificator.<Option>valueOf(idOfOption));
        if (option == null || option.equals(Option.EMPTY))
        {
            return Response.status(Status.NOT_FOUND).build( );
        }
        StringBuilder html = new StringBuilder ( );
        html.append("<!DOCTYPE html><html><head><title>Option.</title></head><body>");
        html.append(String.format("<article id='%d'>", idOfOption));
        html.append(String.format("<header><h1>Option #%d.</h1></header>", idOfOption));
        html.append("<section><h2>Message.</h2>");
        html.append(String.format("<p>%s</p>", option.getMessage( )));
        html.append("</section>");
        html.append("<section><h2>Reward.</h2>");
        html.append(String.format("<p>%d</p>", option.getReward( )));
        html.append("</section>");
        html.append(String.format("<section><h2>%s</h2>", "Update option."));
        html.append(String.format("<form action='/tasks/%1$d/options/%2$d/update' method='POST'>", idOfTask, idOfOption));
        html.append(String.format("<fieldset><legend>Parameters of the option.</legend><input type='hidden' name='task_id' value='%d' required />", idOfTask));
        html.append(String.format("<input type='hidden' name='option_id' value='%d' required />", idOfOption));
        html.append(String.format("<label>Message.<textarea name='message' required>%s</textarea></label>", option.getMessage( )));
        html.append(String.format("<label>Reward.<input type='number' name='reward' value='%d' required/></label>", option.getReward( )));
        html.append("</fieldset><input type='submit' />");
        html.append("</form>");
        html.append("</section>");
        html.append(String.format("<section><h2>%s</h2>", "Delete option."));
        html.append(String.format("<form action='/tasks/%1$d/options/%2$d/destroy' method='POST'>", idOfTask, idOfOption));
        html.append(String.format("<input type='hidden' name='task_id' value='%d' required />", idOfTask));
        html.append(String.format("<input type='hidden' name='option_id' value='%d' required />", idOfOption));
        html.append("<input type='submit' />");
        html.append("</form>");
        html.append("</section>");
        html.append("</article>");
        html.append("</body></html");
        return Response.ok( ).entity(html.toString( )).build( );
    }

    @POST
    @Path("/{option_id : \\d+}/update")
    @Produces(MediaType.TEXT_HTML)
    public Response optionUpdate (@PathParam("task_id") Integer idOfTask, @PathParam("option_id") Long idOfOption, @FormParam("message") String messageOfOption, @FormParam("reward") Integer reward) 
            throws URISyntaxException, OptionsControllerException
    {
        Options controller = Controllers.getInstance(Options.class);
        Identificator<Option> id = NumericIdentificator.<Option>valueOf(idOfOption);
        Option option = controller.retrieve(id);
        option = controller.update(INSTRUCTOR, option, messageOfOption, reward);
        if (option == null || option.equals(Option.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI(String.format("/tasks/%d/options/%d", idOfTask, idOfOption))).build( );
    }
    
    @POST
    @Path("/{option_id : \\d+}/destroy")
    @Produces (MediaType.TEXT_HTML)
    public Response optionDestroy (Integer idOfTask, Long idOfOption) 
            throws URISyntaxException, OptionsControllerException
    {
        Options controller = Controllers.getInstance(Options.class);
        Identificator<Option> id = NumericIdentificator.<Option>valueOf(idOfTask.toString( ).concat(idOfOption.toString( )));
        boolean success = false;
            success = controller.destroy(id);
        if (success)
        {
            return Response.seeOther(new URI (String.format("/tasks/%d", idOfTask))).build( );
        }
        return Response.status(Status.BAD_REQUEST).build( );
    }
}
