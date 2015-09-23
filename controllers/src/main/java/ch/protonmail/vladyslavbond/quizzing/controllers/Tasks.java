package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class Tasks 
extends Controller 
{   
    private TaskFactory getTaskFactory ( )
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class);
    }

    public Task create(Identificator<Instructor> idOfInstructor, Integer idOfTaskType, String description)
    {
        return create(idOfInstructor, TaskType.valueOf(idOfTaskType), description);
    }

    public Task create(Identificator<Instructor> idOfInstructor, TaskType taskType, String description)
    {
        return getTaskFactory( ).newInstance(idOfInstructor, taskType, description);
    }

    public Task retrieve(Long idOfTask)
    {
        return this.retrieve(NumericIdentificator.<Task>valueOf(idOfTask));
    }

    public Task retrieve(Identificator<Task> id)
    {
        Task task = getTaskFactory( ).getInstance(id);
        if (task == null || task.equals(Task.EMPTY))
        {
            return Task.EMPTY;
        }
        return task;
    }
    
    private InstructorFactory getInstructorFactory ( )
    {
        return Factories.<InstructorFactory>getInstance(InstructorFactory.class);
    }
    
    public Task update (Identificator<Instructor> idOfInstructor, Identificator<Task> idOfTask, String newDescription)
    {
        return this.update(getInstructorFactory( ).getInstance(idOfInstructor), getTaskFactory( ).getInstance(idOfTask), newDescription);
    }

    public Task update (Instructor instructor, Task task, String newDescription)
    {
        task = getTaskFactory( ).update(instructor, task, newDescription);
        if (task == null || task.equals(Task.EMPTY))
        {
            return Task.EMPTY;
        }
        return task;
    }
}
