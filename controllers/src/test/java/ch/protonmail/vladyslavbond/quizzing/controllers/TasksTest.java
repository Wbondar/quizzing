/**
 * 
 */
package ch.protonmail.vladyslavbond.quizzing.controllers;


import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.protonmail.vladyslavbond.quizzing.domain.Factories;
import ch.protonmail.vladyslavbond.quizzing.domain.Instructor;
import ch.protonmail.vladyslavbond.quizzing.domain.InstructorFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Member;
import ch.protonmail.vladyslavbond.quizzing.domain.MemberFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskType;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

/**
 * @author Vladyslav Bondarenko
 *
 */
public class TasksTest
{
    private transient Task                      task;
    private transient Identificator<Task>       taskId;
    private transient TaskType                  taskType;
    private transient String                    description;
    private transient String                    updatedDescription;
    private transient Identificator<Instructor> instructorId;
    private transient Instructor                instructor;
    private transient Tasks                     controller;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        this.controller   = Controllers.getInstance(Tasks.class);
        String taskstester = "taskstester" + (new Random ( )).nextInt( );
        Member member = Factories.<MemberFactory>getInstance(MemberFactory.class).newInstance(taskstester, taskstester);
        this.instructor = Factories.<InstructorFactory>getInstance(InstructorFactory.class).getInstance(NumericIdentificator.<Instructor>valueOf(member.getId( ).toNumber( ).intValue( )));
        this.instructorId = instructor.getId( );
        this.taskType     = TaskType.valueOf((new Random( )).nextInt(3) + 1);
        this.description  = "Description of a test task.";
        this.updatedDescription  = "Updated description of a test task.";
    }
    
    private void create ( ) throws TasksControllerException
    {
        this.task = controller.create(instructor, taskType, description);
        this.taskId = task.getId( );
        assertEquals(description, task.getDescription( ));   
    }
    
    private void retrieve ( ) throws TasksControllerException
    {
        Task retrievedTask = controller.retrieve(taskId);
        /*
         * It is assumed, that retrieved task wasn't updated since creation.
         * Thus, original and retrieved tasks has to be equals,
         * since their fields are the same.
         */
        assertEquals(task, retrievedTask);
    }
    
    private void updateDescription ( ) throws TasksControllerException
    {
        Task updatedTask = controller.update(instructor, task, updatedDescription);
        assertEquals(updatedDescription, updatedTask.getDescription( ));
        /*
         * Domain classes, such as task, in this application are only immutable descriptors.
         * Hence original task and updated task has to be unequal.
         */
        assertFalse(task.equals(updatedTask));
    }
    
    private void destroy ( ) throws TasksControllerException
    {
        boolean success = false;
        /*
         * Deleting task from the database is yet to be implemented.
         */
        assertFalse(success);
    }
    
    @Test
    public void testController ( ) throws TasksControllerException
    {
        create( );
        retrieve( );
        /*
         * Updating task by manipulating it's options is saved for the different test.
         */
        updateDescription( );
        destroy( );
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        this.task = null;
        this.taskId = null;
        this.taskType     = null;
        this.description = null;
        this.description  = null;
        this.updatedDescription = null;
        this.instructorId = null;
        this.controller   = null;
    }

}
