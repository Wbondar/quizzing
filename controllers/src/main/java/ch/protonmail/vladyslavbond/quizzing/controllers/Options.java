package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class Options 
extends Controller 
{
    public Option create (Instructor instructor, Task task, String messageOfOption, Integer reward) throws OptionsControllerException 
    {
        try
        {
            Option option = getOptionFactory( ).newInstance(instructor, task, messageOfOption, reward);
            if (option == null || option.equals(Option.EMPTY))
            {
                return Option.EMPTY;
            }
            return option;   
        } catch (OptionFactoryException e) {
            throw new OptionsControllerException (e);
        }
    }
    
    public Option retrieve (Identificator<Option> id) throws OptionsControllerException 
    {
        try
        {
            Option option = getOptionFactory( ).getInstance(id);
            if (option == null || option.equals(Option.EMPTY))
            {
                return Option.EMPTY;
            }
            return option;   
        } catch (OptionFactoryException e) {
            throw new OptionsControllerException (e);
        }
    }
    
    private OptionFactory getOptionFactory()
    {
        return Factories.<OptionFactory>getInstance(OptionFactory.class);
    }

    public Option update (Instructor instructor, Option option, String messageOfOption, Integer reward) throws OptionsControllerException 
    {
        Option updatedOption = Option.EMPTY;
        try
        {
            if (messageOfOption != null && !messageOfOption.isEmpty( ))
            {
                updatedOption = getOptionFactory( ).update(instructor, option, messageOfOption);
                if (updatedOption == null || updatedOption.equals(Option.EMPTY))
                {
                    throw new OptionsControllerException ("Failed to update message of an option.");
                }
            }
            if (reward != null)
            {
                updatedOption = getOptionFactory( ).update(instructor, option, reward);
                if (updatedOption == null || updatedOption.equals(Option.EMPTY))
                {
                    throw new OptionsControllerException ("Failed to update reward of an option.");
                }
            }
            if (updatedOption == null || updatedOption.equals(Option.EMPTY))
            {
                throw new OptionsControllerException ("Failed to update an option.");
            }
            return updatedOption;   
        } catch (OptionFactoryException e) {
            throw new OptionsControllerException (e);
        }
    }
    
    public boolean destroy (Identificator<Option> id) throws OptionsControllerException 
    {
        try
        {
            return this.destroy(getOptionFactory( ).getInstance(id));   
        } catch (OptionFactoryException e) {
            throw new OptionsControllerException (e);
        }
    }

    private boolean destroy(Option instance)
    {
        return getOptionFactory( ).destroy(instance);
    }

    public Option create(Integer idOfInstructor, Integer idOfTask, String messageOfOption, Integer reward) throws OptionsControllerException, InstructorFactoryException
    {
        try
        {
            return this.create(NumericIdentificator.<Instructor>valueOf(idOfInstructor), NumericIdentificator.<Task>valueOf(idOfTask), messageOfOption, reward);
        } catch (TaskFactoryException e)
        {
           throw new OptionsControllerException (e);
        }
    }

    private Option create(Identificator<Instructor> idOfInstructor, Identificator<Task> idOfTask, String messageOfOption, Integer reward) throws OptionsControllerException, TaskFactoryException, InstructorFactoryException
    {
        return this.create(getInstructorFactory( ).getInstance(idOfInstructor), getTaskFactory( ).getInstance(idOfTask), messageOfOption, reward);
    }
}
