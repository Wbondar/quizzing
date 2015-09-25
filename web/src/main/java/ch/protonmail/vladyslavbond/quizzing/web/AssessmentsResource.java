package ch.protonmail.vladyslavbond.quizzing.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ch.protonmail.vladyslavbond.quizzing.controllers.Assessments;
import ch.protonmail.vladyslavbond.quizzing.controllers.AssessmentsControllerException;
import ch.protonmail.vladyslavbond.quizzing.controllers.Controllers;
import ch.protonmail.vladyslavbond.quizzing.domain.Answer;
import ch.protonmail.vladyslavbond.quizzing.domain.AnswerFactoryException;
import ch.protonmail.vladyslavbond.quizzing.domain.Assessment;
import ch.protonmail.vladyslavbond.quizzing.domain.Exam;
import ch.protonmail.vladyslavbond.quizzing.domain.FinishedAssessment;
import ch.protonmail.vladyslavbond.quizzing.domain.OngoingAssessment;
import ch.protonmail.vladyslavbond.quizzing.domain.Option;
import ch.protonmail.vladyslavbond.quizzing.domain.Score;
import ch.protonmail.vladyslavbond.quizzing.domain.Student;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskFactoryException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

@Path("/assessments")
public enum AssessmentsResource
{
    INSTANCE;

    private final static Identificator<Student>    ID_OF_STUDENT    = NumericIdentificator.<Student>valueOf(1);
    private final static Identificator<Exam>       ID_OF_EXAM       = NumericIdentificator.<Exam>valueOf(1);
    
    private AssessmentsResource ( ) {}
    
    @POST
    @Path("/new")
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
    @Path("/{id : \\d+}")
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
    @Path("/{id : \\d+}/update")
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
    @Path("/{id : \\d+}/destroy")
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
    @Path("/{id : \\d+}/finish")
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
