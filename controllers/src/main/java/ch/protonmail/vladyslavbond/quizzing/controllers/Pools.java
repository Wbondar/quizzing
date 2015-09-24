package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

public final class Pools 
extends Controller 
{
    public Pool create (Instructor instructor, String titleOfPool) 
    {
        Pool pool = getPoolFactory( ).newInstance(instructor.getId( ), titleOfPool);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return Pool.EMPTY;
        }
        return pool;
    }
    
    private InstructorFactory getInstructorFactory ( )
    {
        return Factories.<InstructorFactory>getInstance(InstructorFactory.class);
    }
    
    public Pool create (Identificator<Instructor> idOfInstructor, String titleOfPool) throws PoolsControllerException
    {
        try
        {
            return this.create(getInstructorFactory( ).getInstance(idOfInstructor), titleOfPool);
        } catch (InstructorFactoryException e)
        {
            throw new PoolsControllerException (e);
        }
    }
    
    public Result read (Pool pool) 
    {
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return badRequest("Failure.");
        }
        return ok(pool.getTitle( ));
    }
    
    public Result update (Pool pool, String titleOfPool) 
    {
        PoolFactory poolFactory = Factories.<PoolFactory>getInstance(PoolFactory.class);
        pool = poolFactory.update(pool, titleOfPool);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(pool);
    }
    
    public Pool updateTaskAdd (Pool pool, Task task) 
    {
        pool = getPoolFactory( ).update(pool, task, true);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return Pool.EMPTY;
        }
        return pool;
    }
    
    public Pool updateTaskRemove (Pool pool, Task task) 
    {
        pool = getPoolFactory( ).update(pool, task, false);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return Pool.EMPTY;
        }
        return pool;
    }
    
    private final PoolFactory getPoolFactory ( )
    {
        return Factories.<PoolFactory>getInstance(PoolFactory.class);
    }
    
    private final TaskFactory getTaskFactory ( )
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class);
    }

    public Pool updateTaskAdd (Identificator<Pool> idOfPool, Identificator<Task> id) throws PoolsControllerException
    {
        try
        {
            return updateTaskAdd(getPoolFactory( ).getInstance(idOfPool), getTaskFactory( ).getInstance(id));
        } catch (TaskFactoryException e)
        {
            throw new PoolsControllerException (e);
        }
    }
}
