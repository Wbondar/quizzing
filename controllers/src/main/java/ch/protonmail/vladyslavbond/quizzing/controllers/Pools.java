package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

public final class Pools 
extends Controller 
{   
    public Pool create (Instructor instructor, String titleOfPool) 
            throws PoolsControllerException 
    {
        Pool pool = Pool.EMPTY;
        try
        {
            pool = getPoolFactory( ).newInstance(instructor, titleOfPool);
        } catch (PoolFactoryException e)
        {
            throw new PoolsControllerException (e);
        }
        return pool;
    }
    
    public Pool create (Identificator<Instructor> idOfInstructor, String titleOfPool) 
            throws PoolsControllerException
    {
        try
        {
            Pool pool = this.create(getInstructorFactory( ).getInstance(idOfInstructor), titleOfPool);
            if (pool == null)
            {
                return Pool.EMPTY;
            }
            return pool;
        } catch (InstructorFactoryException e)
        {
            throw new PoolsControllerException (e);
        }
    }
    
    public Pool retrieve (Identificator<Pool> idOfPool) 
            throws PoolsControllerException
    {
        Pool pool = Pool.EMPTY;
        try
        {
            pool = getPoolFactory( ).getInstance(idOfPool);
        } catch (PoolFactoryException e)
        {
            throw new PoolsControllerException (e);
        }
        return pool;
    }
    
    public Pool update (Instructor instructor, Pool pool, String newTitle)
            throws PoolsControllerException
    {
        try
        {
            Pool updatedPool = getPoolFactory( ).update(instructor, pool, newTitle);
            if (updatedPool == null)
            {
                return Pool.EMPTY;
            }
            return updatedPool;
        } catch (PoolFactoryException e)
        {
            throw new PoolsControllerException ("Failed to update title of a pool.", e);
        }
    }
    
    public Pool updateTaskAdd (Instructor instructor, Pool pool, Task task) 
            throws PoolsControllerException 
    {
        try
        {
            if (instructor == null)
            {
                throw new PoolsControllerException ("Instructor is missing.");
            }
            if (pool == null)
            {
                throw new PoolsControllerException ("Pool is missing.");
            }
            if (task == null)
            {
                throw new PoolsControllerException ("Task is missing.");
            }
            Pool updatedPool = getPoolFactory( ).update(instructor, pool, task, true);
            if (updatedPool == null)
            {
                return Pool.EMPTY;
            }
            return updatedPool;
        } catch (PoolFactoryException e)
        {
            throw new PoolsControllerException ("Failed to add a task to a pool.", e);
        }
    }
    
    public Pool updateTaskRemove (Instructor instructor, Pool pool, Task task) 
            throws PoolsControllerException 
    {
        try
        {
            Pool updatedPool = getPoolFactory( ).update(instructor, pool, task, false);
            if (updatedPool == null)
            {
                return Pool.EMPTY;
            }
            return updatedPool;
        } catch (PoolFactoryException e)
        {
            throw new PoolsControllerException (e);
        }
    }

    public Pool updateTaskAdd (Identificator<Instructor> idOfInstructor, Identificator<Pool> idOfPool, Identificator<Task> id) 
            throws PoolsControllerException
    {
        try
        {
            return updateTaskAdd(getInstructorFactory( ).getInstance(idOfInstructor), getPoolFactory( ).getInstance(idOfPool), getTaskFactory( ).getInstance(id));
        } catch (FactoryException e)
        {
            throw new PoolsControllerException (e);
        }
    }
}
