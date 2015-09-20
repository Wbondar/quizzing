package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;

public final class Pools 
extends Controller 
{
    public Result create (String titleOfPool) 
    {
        PoolFactory poolFactory = Factories.<PoolFactory>getInstance(PoolFactory.class);
        Pool pool = poolFactory.newInstance(titleOfPool);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(pool);
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
    
    public Result updateTaskAdd (Pool pool, Task task) 
    {
        PoolFactory poolFactory = Factories.<PoolFactory>getInstance(PoolFactory.class);
        pool = poolFactory.update(pool, task, true);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(pool);
    }
    
    public Result updateTaskRemove (Pool pool, Task task) 
    {
        PoolFactory poolFactory = Factories.<PoolFactory>getInstance(PoolFactory.class);
        pool = poolFactory.update(pool, task, false);
        if (pool == null || pool.equals(Pool.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(pool);
    }
    
    public Result destroy (Pool pool) 
    {
        PoolFactory poolFactory = Factories.<PoolFactory>getInstance(PoolFactory.class);
        boolean success = poolFactory.destroy(pool);
        if (!success)
        {
            return badRequest("Failure.");
        }
        return ok("Success.");
    }
}
