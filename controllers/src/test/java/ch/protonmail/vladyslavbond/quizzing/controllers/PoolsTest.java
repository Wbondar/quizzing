/**
 * 
 */
package ch.protonmail.vladyslavbond.quizzing.controllers;


import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ch.protonmail.vladyslavbond.quizzing.domain.Factories;
import ch.protonmail.vladyslavbond.quizzing.domain.Instructor;
import ch.protonmail.vladyslavbond.quizzing.domain.InstructorFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Member;
import ch.protonmail.vladyslavbond.quizzing.domain.MemberFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Pool;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskFactoryException;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskType;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

/**
 * @author Vladyslav Bondarenko
 *
 */
public class PoolsTest
{
    private transient Instructor instructor;
    private transient Pools      controller;
    private transient Pool       pool;
    private transient String     titleOfPool;
    private transient String     titleOfPoolUpdated;
    private transient Task       task;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        String poolstester = "poolstester" + (new Random ( )).nextInt( );
        Member member = Factories.<MemberFactory>getInstance(MemberFactory.class).newInstance(poolstester, poolstester);
        instructor = Factories.<InstructorFactory>getInstance(InstructorFactory.class).getInstance(NumericIdentificator.<Instructor>valueOf(member.getId( ).toNumber( ).intValue( )));
        controller = Controllers.<Pools>getInstance(Pools.class);
        titleOfPool = "pool" + UUID.randomUUID( ).toString();
        titleOfPoolUpdated = "Updated " + titleOfPool;
        task = Factories.<TaskFactory>getInstance(TaskFactory.class).newInstance(instructor, TaskType.ChoiceMultiple, "Description of a task to be added to pool of title " + titleOfPool + ".");
    }
    
    private final void create ( ) throws PoolsControllerException
    {
        pool = controller.create(instructor, titleOfPool);
        assertEquals(titleOfPool, pool.getTitle( ));
    }
    
    private final void retrieve ( ) throws PoolsControllerException
    {
        Pool poolRetrieved = controller.retrieve(pool.getId( ));
        assertEquals(pool, poolRetrieved);
    }
    
    private final void updateTitle ( ) throws PoolsControllerException
    {
        Pool poolUpdated = controller.update(instructor, pool, titleOfPoolUpdated);
        assertEquals(titleOfPoolUpdated, poolUpdated.getTitle( ));
    }
    
    private final void updateTaskAdd ( ) throws PoolsControllerException, TaskFactoryException
    {
        Pool poolTaskAdded = controller.updateTaskAdd(instructor, pool, task);
        assertTrue(poolTaskAdded.getTasks( ).contains(task));
    }
    
    private final void updateTaskRemove ( ) throws PoolsControllerException, TaskFactoryException
    {
        Pool poolTaskRemoved = controller.updateTaskRemove(instructor, pool, task);
        assertFalse(poolTaskRemoved.getTasks( ).contains(task));
    }
    
    @Test
    public void testController ( ) throws PoolsControllerException, TaskFactoryException
    {
        create( );
        retrieve( );
        updateTitle( );
        updateTaskAdd( );
        updateTaskRemove( );
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

}
