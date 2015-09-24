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
        throws TasksControllerException
    {
        return create(idOfInstructor, TaskType.valueOf(idOfTaskType), description);
    }

    public Task create(Identificator<Instructor> idOfInstructor, TaskType taskType, String description)
        throws TasksControllerException
    {
        try
        {
            return getTaskFactory( ).newInstance(idOfInstructor, taskType, description);
        } catch (TaskFactoryException e) {
            throw new TasksControllerException (e);
        }
    }

    public Task retrieve(Long idOfTask) throws TasksControllerException
    {
        return this.retrieve(NumericIdentificator.<Task>valueOf(idOfTask));
    }

    public Task retrieve(Identificator<Task> id) throws TasksControllerException
    {
        try
        {
            Task task = getTaskFactory( ).getInstance(id);
            if (task == null || task.equals(Task.EMPTY))
            {
                return Task.EMPTY;
            }
            return task;
        } catch (TaskFactoryException e) {
            throw new TasksControllerException (e);
        }
    }
    
    private InstructorFactory getInstructorFactory ( )
    {
        return Factories.<InstructorFactory>getInstance(InstructorFactory.class);
    }
    
    public Task update (Identificator<Instructor> idOfInstructor, Identificator<Task> idOfTask, String newDescription) throws TasksControllerException
    {
        try
        {
            return this.update(getInstructorFactory( ).getInstance(idOfInstructor), getTaskFactory( ).getInstance(idOfTask), newDescription);
        } catch (InstructorFactoryException e)
        {
            throw new TasksControllerException (e);
        } catch (TaskFactoryException e)
        {
            throw new TasksControllerException (e);
        }
    }

    public Task update (Instructor instructor, Task task, String newDescription) throws TasksControllerException
    {
        try
        {
            task = getTaskFactory( ).update(instructor, task, newDescription);
        } catch (TaskFactoryException e)
        {
            throw new TasksControllerException (e);
        }
        if (task == null || task.equals(Task.EMPTY))
        {
            return Task.EMPTY;
        }
        return task;
    }
}
