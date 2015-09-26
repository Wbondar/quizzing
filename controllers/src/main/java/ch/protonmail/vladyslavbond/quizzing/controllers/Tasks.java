package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class Tasks 
extends Controller 
{   

    public Task create(Instructor instructor, Integer idOfTaskType, String description)
        throws TasksControllerException
    {
        return create(instructor, TaskType.valueOf(idOfTaskType), description);
    }

    public Task create(Instructor instructor, TaskType taskType, String description)
        throws TasksControllerException
    {
        try
        {
            Task task = getTaskFactory( ).newInstance(instructor, taskType, description);
            if (task == null)
            {
                return Task.EMPTY;
            }
            return task;
        } catch (TaskFactoryException e) {
            throw new TasksControllerException (e);
        }
    }

    public Task retrieve(Integer idOfTask) throws TasksControllerException
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
            Task updatedTask = getTaskFactory( ).update(instructor, task, newDescription);
            if (updatedTask == null)
            {
                return Task.EMPTY;
            }
            return updatedTask;
        } catch (TaskFactoryException e)
        {
            throw new TasksControllerException (e);
        }
    }
}
