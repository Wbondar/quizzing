
package ch.protonmail.vladyslavbond.quizzing.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.quizzing.controllers.Assessments;
import ch.protonmail.vladyslavbond.quizzing.controllers.AssessmentsControllerException;
import ch.protonmail.vladyslavbond.quizzing.controllers.ControllerException;
import ch.protonmail.vladyslavbond.quizzing.controllers.Controllers;
import ch.protonmail.vladyslavbond.quizzing.controllers.Options;
import ch.protonmail.vladyslavbond.quizzing.controllers.OptionsControllerException;
import ch.protonmail.vladyslavbond.quizzing.controllers.Pools;
import ch.protonmail.vladyslavbond.quizzing.controllers.Tasks;
import ch.protonmail.vladyslavbond.quizzing.controllers.TasksControllerException;
import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

// TODO Split MyResource into smaller parts.

@Path("/")
public enum MyResource 
{
    INSTANCE ( );
    
    private final static Identificator<Instructor> ID_OF_INSTRUCTOR = NumericIdentificator.<Instructor>valueOf(1);
    private final static Identificator<Student>    ID_OF_STUDENT    = NumericIdentificator.<Student>valueOf(1);
    private final static Identificator<Exam>       ID_OF_EXAM       = NumericIdentificator.<Exam>valueOf(1);
    private final static Identificator<Pool>       ID_OF_POOL       = NumericIdentificator.<Pool>valueOf(1);
    
    private MyResource ( ) {}
    
    @POST
    @Path("/tasks/new")
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
    @Path("/tasks/new")
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
    @Path("/tasks/{id : \\d+}")
    @Produces(MediaType.TEXT_HTML)
    public Response taskRetrieve (@PathParam("id") Long idOfTask)
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
                html.append(String.format("<header><h3>Option #%d.</h3></header>",  idOfOption));
                html.append("<section><h4>Message.</h4>");
                html.append(String.format("<p>%s</p>", option.getMessage( )));
                html.append("</section>");
                html.append("<section><h4>Reward.</h4>");
                html.append(String.format("<p>%d</p>", option.getReward( )));
                html.append("</section>");
                html.append(String.format("<section><h4>%s</h4>", "Update option."));
                html.append(String.format("<form action='/tasks/%1$d/option/%2$d' method='GET'>", idOfTask, idOfOption));
                html.append("<input type='submit />");
                html.append("</form>");
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
    @Path("/tasks/{id : \\d+}/update")
    public Response taskUpdate (@PathParam("id") Long idOfTask, @FormParam("description") String description) 
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
    
    @POST
    @Path("/tasks/{id : \\d+}/option/new")
    @Produces(MediaType.TEXT_HTML)
    public Response optionCreate (@PathParam("id") Long idOfTask, @FormParam("message") String messageOfOption, @FormParam("reward") Integer reward) 
            throws URISyntaxException, OptionsControllerException
    {
        Options controller = Controllers.getInstance(Options.class);
        Option option = Option.EMPTY;
            option = controller.create(idOfTask, messageOfOption, reward);
        if (option == null || option.equals(Option.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI(String.format("/tasks/%d", idOfTask))).build( );
    }
    
    @GET
    @Path("/tasks/{task_id : \\d+}/option/{option_id : \\d+}")
    @Produces(MediaType.TEXT_HTML)
    public Response optionRetrieve (@PathParam("task_id") Long idOfTask, @PathParam ("option_id") Integer idOfOption) throws OptionsControllerException 
    {
        Options controller = Controllers.<Options>getInstance(Options.class);
        Option option;
            option = controller.retrieve(NumericIdentificator.<Option>valueOf(idOfTask.toString( ).concat(idOfOption.toString( ))));
        if (option == null || option.equals(Option.EMPTY))
        {
            return Response.status(Status.NOT_FOUND).build( );
        }
        StringBuilder html = new StringBuilder ( );
        html.append("<!DOCTYPE html><html><head><title>Option.</title></head><body>");
        html.append(String.format("<article id='%d'>", option.getId( ).toString( )));
        html.append(String.format("<header><h1>Option #%d.</h1></header>", option.getId( )));
        html.append("<section><h2>Message.</h2>");
        html.append(String.format("<p>%s</p>", option.getMessage( )));
        html.append("</section>");
        html.append("<section><h2>Reward.</h2>");
        html.append(String.format("<p>%d</p>", option.getReward( )));
        html.append("</section>");
        html.append(String.format("<section><h2>%s</h2>", "Update option."));
        html.append(String.format("<form action='/tasks/%1$d/option/%2$d/update' method='POST'>", idOfTask, option.getId( )));
        html.append(String.format("<input type='hidden' name='task_id' value='%d' required />", idOfTask));
        html.append(String.format("<input type='hidden' name='option_id' value='%d' required />", option.getId( )));
        html.append(String.format("<textarea name='message' required>%s</textarea>", option.getMessage( )));
        html.append(String.format("<input type='number' name='reward' value='%d' required/>", option.getReward( )));
        html.append("<input type='submit />");
        html.append("</form>");
        html.append("</section>");
        html.append(String.format("<section><h2>%s</h2>", "Delete option."));
        html.append(String.format("<form action='/tasks/%1$d/option/%2$d/destroy' method='POST'>", idOfTask, option.getId( )));
        html.append(String.format("<input type='hidden' name='task_id' value='%d' required />", idOfTask));
        html.append(String.format("<input type='hidden' name='option_id' value='%d' required />", option.getId( )));
        html.append("<input type='submit />");
        html.append("</form>");
        html.append("</section>");
        html.append("</article>");
        html.append("</body></html");
        return Response.ok( ).entity(html.toString( )).build( );
    }

    @POST
    @Path("/tasks/{task_id : \\d+}/option/{option_id : \\d+}/update")
    @Produces(MediaType.TEXT_HTML)
    public Response optionUpdate (@PathParam("task_id") Long idOfTask, @PathParam("option_id") Integer idOfOption, @FormParam("message") String messageOfOption, @FormParam("reward") Integer reward) 
            throws URISyntaxException, OptionsControllerException
    {
        Options controller = Controllers.getInstance(Options.class);
        Identificator<Option> id = NumericIdentificator.<Option>valueOf(idOfTask.toString( ).concat(idOfOption.toString( )));
        Option option = Option.EMPTY;
            option = controller.update(id, messageOfOption, reward);
        if (option == null || option.equals(Option.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI(String.format("/tasks/%d/option/%d", idOfTask, idOfOption))).build( );
    }
    
    @POST
    @Path("/tasks/{task_id : \\d+}/option/{option_id : \\d+}/destroy")
    @Produces (MediaType.TEXT_HTML)
    public Response optionDestroy (Long idOfTask, Integer idOfOption) 
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
    
    @POST
    @Path("/assessments/new")
    @Produces (MediaType.TEXT_HTML)
    public Response assessmentCreate ( ) 
            throws URISyntaxException, AssessmentsControllerException
    {
        Assessments controller = Controllers.<Assessments>getInstance(Assessments.class);
        OngoingAssessment assessment = OngoingAssessment.EMPTY;
            assessment = controller.create(ID_OF_STUDENT, ID_OF_EXAM);
        if (assessment == null || assessment.equals(OngoingAssessment.EMPTY))
        {
            return Response.status(Status.BAD_REQUEST).build( );
        }
        return Response.seeOther(new URI (String.format("/assessments/%d", assessment.getId( )))).build( );
    }
    
    @GET
    @Path("/assessments/{id : \\d+}")
    @Produces (MediaType.TEXT_HTML)
    public Response assessmentRetrieve (@PathParam("id") Long idOfAssessment) throws AssessmentsControllerException, NumberFormatException, TaskFactoryException, AnswerFactoryException 
    {
        Assessments controller = Controllers.<Assessments>getInstance(Assessments.class);
        Assessment assessment = FinishedAssessment.EMPTY;
            assessment = controller.retrieve(idOfAssessment);
        if (assessment == null || assessment.equals(FinishedAssessment.EMPTY))
        {
            return Response.status(Status.NOT_FOUND).build( );
        }
        OngoingAssessment ongoing = null;
        try
        {
            ongoing = OngoingAssessment.class.cast(assessment);   
        } catch (ClassCastException e) {
            ongoing = null;
        }
        boolean isOngoing = ongoing != null;
        StringBuilder html = new StringBuilder ( );
        html.append("<!DOCTYPE html><html><head><title>Assessment.</title></head><body>");
        html.append(String.format("<article id='%d'>", idOfAssessment));
        html.append(String.format("<header><h1>Assessment #%d.</h1></header>", idOfAssessment));
        boolean showScores = !isOngoing;
        // TODO Add information about student, total score, date-time, etc.
        if (showScores)
        {
            html.append(String.format("<section><h2>Total score.</h2><p>%s</p></section>", "Not yet implemented."));
        }
        for (Task task : assessment.getTasks( ))
        {
            Long idOfTask = Long.valueOf(task.getId( ).toString( ));
            html.append(String.format("<article id='%d'>", idOfTask));
            html.append(String.format("<header><h2>Task #%d.</h2></header>", idOfTask));
            html.append(String.format("<section><h3>Description of task.</h3><p>%s</p></section>", task.getDescription( )));
            if (task.getOptions( ) != null && !task.getOptions( ).isEmpty())
            {
                html.append("<section><h4>Options.</h4>");
                for (Option option : task.getOptions( ))
                {
                    html.append(String.format("<article id='%d'>", option.getId( )));
                    html.append(String.format("<header><h5>Option #%d.</h5></header>",  option.getId( )));
                    html.append("<section><h6>Message.</h6>");
                    html.append(String.format("<p>%s</p>", option.getMessage( )));
                    html.append("</section>");
                    html.append("</article>");
                }
                html.append("</section>");
            }
            html.append("<section><h3>Answer(s).</h3><table><tr><th>Input.</th>");
            if (showScores)
            {
                html.append("<th>Reward.</th></tr>");
                FinishedAssessment finishedAssessment = FinishedAssessment.class.cast(assessment);
                Score score = finishedAssessment.getScores(task);
                Set<Answer> answers = score.getAnswers( );
                Integer size = answers.size( );
                int i = 1;
                for (Answer answer : answers)
                {
                    boolean isLastOne = (i == size);
                    if (isLastOne)
                    {
                        html.append(String.format("<tr><td>%s</td><td rowspan='%d'>%s</td></tr>", answer.getInput( ), size, score.getReward( )));
                    } else {
                        html.append(String.format("<tr><td>%s</td></tr>", answer.getInput( )));
                    }
                    i++;
                }
            } else {
                html.append("</tr>");
                for (Answer answer : assessment.getAnswers(task))
                {
                    html.append(String.format("<tr><td>%s</td></tr>", answer.getInput( )));
                }
            }
            html.append("</table></section>");
            if (isOngoing)
            {
                html.append("<section><h3>Provide answer.</h3>");
                html.append(String.format("<form action='/assessments/%d/update' method='POST'>", idOfAssessment));
                html.append(String.format("<input type='hidden' name='id' value='%d' required />", idOfAssessment));
                html.append(String.format("<input type='hidden' name='task_id' value='%d' required />", idOfTask));
                html.append("<input type='text' name='input' placeholder='Number of option or text.' required />");
                html.append("</form></section>");
            }
            html.append("</article>");
        }
        html.append("</article>");
        html.append("</body></html>");
        return Response.ok( ).entity(html.toString( )).build( );
    }
    
    @POST
    @Path("/assessments/{id : \\d+}/update")
    @Produces (MediaType.TEXT_HTML)
    public Response assessmentUpdate (@PathParam("id") Long idOfAssessment, @FormParam("task_id") Long idOfTask, @FormParam("input") String input) 
            throws URISyntaxException, AssessmentsControllerException
    {
        Assessments controller = Controllers.<Assessments>getInstance(Assessments.class);
        OngoingAssessment assessment = controller.update(idOfAssessment, idOfTask, input);
        if (assessment == null || assessment.equals(OngoingAssessment.EMPTY))
        {
            return Response.status(Status.NOT_FOUND).build( );
        }
        return Response.seeOther(new URI (String.format("/assessments/%d", idOfAssessment))).build( );
    }
    
    @POST
    @Path("/assessments/{id : \\d+}/destroy")
    @Produces (MediaType.TEXT_HTML)
    public Response assessmentDestroy (@PathParam("id") Long idOfAssessment) throws AssessmentsControllerException 
    {
        Assessments controller = Controllers.<Assessments>getInstance(Assessments.class);
        boolean success = controller.destroy(idOfAssessment);
        if (success)
        {
            return Response.ok( ).build( );
        }
        return Response.status(Status.BAD_REQUEST).build( );
    }
    
    @POST
    @Path("/assessments/{id : \\d+}/finish")
    @Produces (MediaType.TEXT_HTML)
    public Response assessmentFinish (@PathParam("id") Long idOfAssessment)
            throws URISyntaxException, AssessmentsControllerException
    {
        Assessments controller = Controllers.<Assessments>getInstance(Assessments.class);
        boolean success = controller.finish(idOfAssessment);
        if (success)
        {
            return Response.seeOther(new URI (String.format("/assessments/%d", idOfAssessment))).build( );
        }
        return Response.status(Status.BAD_REQUEST).build( );
    }
}
