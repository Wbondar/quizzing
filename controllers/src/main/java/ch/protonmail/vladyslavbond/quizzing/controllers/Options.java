package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class Options 
extends Controller 
{
    public Option create (Task task, String messageOfOption, Integer reward) throws OptionsControllerException 
    {
        try
        {
            Option option = getOptionFactory( ).newInstance(task, messageOfOption, reward);
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

    public Option update (Identificator<Option> id, String messageOfOption, Integer reward) throws OptionsControllerException 
    {
        try
        {
            Option option = getOptionFactory( ).getInstance(id);
            if (messageOfOption != null && !messageOfOption.isEmpty( ))
            {
                option = getOptionFactory( ).update(option, messageOfOption);
            }
            if (reward != null)
            {
                option = getOptionFactory( ).update(option, reward);
            }
            if (option == null || option.equals(Option.EMPTY))
            {
                return Option.EMPTY;
            }
            return option;   
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

    public Option create(Long idOfTask, String messageOfOption, Integer reward) throws OptionsControllerException
    {
        try
        {
            return this.create(NumericIdentificator.<Task>valueOf(idOfTask), messageOfOption, reward);
        } catch (TaskFactoryException e)
        {
           throw new OptionsControllerException (e);
        }
    }

    private TaskFactory getTaskFactory()
    {
        return Factories.<TaskFactory>getInstance(TaskFactory.class);
    }

    private Option create(Identificator<Task> idOfTask, String messageOfOption, Integer reward) throws OptionsControllerException, TaskFactoryException
    {
        return this.create(getTaskFactory( ).getInstance(idOfTask), messageOfOption, reward);
    }
}
