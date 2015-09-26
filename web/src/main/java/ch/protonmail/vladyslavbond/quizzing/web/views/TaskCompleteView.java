package ch.protonmail.vladyslavbond.quizzing.web.views;

import java.io.*;

import javax.ws.rs.WebApplicationException;

import ch.protonmail.vladyslavbond.quizzing.domain.Option;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;

public final class TaskCompleteView 
implements View
{
    private transient Task task;
    
    public TaskCompleteView (Task task)
    {
        this.task = task;
    }
    
    @Override
    public void write(OutputStream os) 
            throws IOException, WebApplicationException
    {
        Writer writer = new BufferedWriter(new OutputStreamWriter(os));
        Integer idOfTask = Integer.valueOf(task.getId( ).toNumber( ).intValue( ));
        writer.append("<!DOCTYPE html><html><head><title>Task.</title></head><body>");
        writer.append(String.format("<article id='%d'>", idOfTask));
        writer.append(String.format("<header><h1>Task #%d.</h1></header>", idOfTask));
        writer.append(String.format("<section><h2>Description of task.</h2><p>%s</p></section>", task.getDescription( )));
        if (task.getOptions( ) != null && !task.getOptions( ).isEmpty())
        {
            writer.append("<section><h2>Options.</h2>");
            for (Option option : task.getOptions( ))
            {
                Long idOfOption = Long.valueOf(option.getId( ).toString( ));
                writer.append(String.format("<article id='%d'>", idOfOption));
                writer.append(String.format("<header><h3><a href='/tasks/%1$d/options/%2$d'>Option #%2$d.</a></h3></header>", idOfTask, idOfOption));
                writer.append("<section><h4>Message.</h4>");
                writer.append(String.format("<p>%s</p>", option.getMessage( )));
                writer.append("</section>");
                writer.append("<section><h4>Reward.</h4>");
                writer.append(String.format("<p>%d</p>", option.getReward( )));
                writer.append("</section>");
                writer.append("</section>");
                writer.append("</article>");
            }
            writer.append("</section>");
        }
        writer.append("</article>");
        writer.append(String.format("<section><h2>%s</h2>", "Update task description."));
        writer.append(String.format("<form action='/tasks/%1$d/update' method='POST'><input type='hidden' name='id' value='%1$d' /><textarea name='description'>%2$s</textarea><input type='submit' /></form>", idOfTask, task.getDescription( )));
        writer.append("</section>");
        writer.append(String.format("<section><h2>%s</h2>", "Delete task."));
        writer.append(String.format("<form action='/tasks/%1$d/destroy' method='POST'><input type='hidden' name='id' value='%1$d' /><input type='submit' /></form>", idOfTask));
        writer.append("</section>");
        writer.append("</body></html>");
        writer.flush();
    }
}
