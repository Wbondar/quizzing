package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;

public class Tasks 
extends Controller 
{
    public Result create (String descriptionOfTask, Integer idOfTaskType) 
    {
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        Task task = taskFactory.newInstance(descriptionOfTask, idOfTaskType);
        if (task.equals(Task.EMPTY) || task == null)
        {
            return badRequest("Failure.");
        }
        return this.read(task);
    }
    
    public Result read (Task task) 
    {
        if (task.equals(Task.EMPTY) || task == null)
        {
            return badRequest("Failure.");
        }
        return ok(task.getDescription( ));
    }
    
    public Result update (Task task, String descriptionOfTask) 
    {
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        task = taskFactory.update(task, descriptionOfTask);
        if (task.equals(Task.EMPTY) || task == null)
        {
            return badRequest("Failure.");
        }
        return this.read(task);
    }
    
    public Result destroy (Task task) 
    {
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        boolean success = taskFactory.destroy(task);
        if (!success)
        {
            return badRequest("Failure.");
        }
        return ok("Success.");
    }
}
