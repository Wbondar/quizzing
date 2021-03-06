/**
 * 
 */
package ch.protonmail.vladyslavbond.quizzing.controllers;


import java.util.Random;

import org.junit.*;
import static org.junit.Assert.*;

import ch.protonmail.vladyslavbond.quizzing.domain.Factories;
import ch.protonmail.vladyslavbond.quizzing.domain.Instructor;
import ch.protonmail.vladyslavbond.quizzing.domain.InstructorFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Member;
import ch.protonmail.vladyslavbond.quizzing.domain.MemberFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.Option;
import ch.protonmail.vladyslavbond.quizzing.domain.Task;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskFactory;
import ch.protonmail.vladyslavbond.quizzing.domain.TaskType;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

/**
 * @author Vladyslav Bondarenko
 *
 */
public class OptionsTest
{
    private transient Task task;
    private transient TaskType taskType;
    private transient String description;
    private transient Identificator<Instructor> idOfInstructor;
    private Options controller;
    private Option option;
    private Identificator<Option> idOfOption;
    private Instructor instructor;
    
    public OptionsTest ( ) {}
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        controller = Controllers.<Options>getInstance(Options.class);
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        String tester = "optionstester" + (new Random ( )).nextInt( );
        Member member = Factories.<MemberFactory>getInstance(MemberFactory.class).newInstance(tester, tester);
        this.instructor = Factories.<InstructorFactory>getInstance(InstructorFactory.class).getInstance(NumericIdentificator.<Instructor>valueOf(member.getId( ).toNumber( ).intValue( )));
        this.idOfInstructor = instructor.getId( );
        taskType     = TaskType.valueOf((new Random( )).nextInt(3) + 1);
        description  = "Description of a task to be tested as options container.";
        task = taskFactory.newInstance(instructor, taskType, description);
    }
    
    private void create ( ) throws OptionsControllerException
    {
        String messageOfOption = "Message of test option.";
        Integer reward = (new Random ( )).nextInt(10) + 2;
        option = controller.create(instructor, task, messageOfOption, reward);
        idOfOption = option.getId( );
        assertEquals(messageOfOption, option.getMessage( ));
        assertEquals(reward, option.getReward( ));
    }
    
    private void retrieve ( ) throws OptionsControllerException
    {
        Option retreivedOption = controller.retrieve(idOfOption);
        assertEquals(option, retreivedOption);
    }
    
    private void update ( ) throws OptionsControllerException
    {
        String updatedMessageOfOption = "Updated " + option.getMessage( );
        Integer updatedReward = option.getReward( ) + (new Random ( )).nextInt(10) + 1;
        Option optionUpdated = controller.update(instructor, option, updatedMessageOfOption, updatedReward);
        assertEquals(updatedMessageOfOption, optionUpdated.getMessage());
        assertEquals(updatedReward, optionUpdated.getReward());
    }
    
    @Test
    public void testController ( ) throws OptionsControllerException
    {
        create( );
        retrieve( );
        update( );
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

}
