package ch.protonmail.vladyslavbond.quizzing.controllers;

import ch.protonmail.vladyslavbond.quizzing.domain.*;

public class Options 
extends Controller 
{
    public Result create (Task task, String messageOfOption, Integer reward) 
    {
        OptionFactory optionFactory = Factories.<OptionFactory>getInstance(OptionFactory.class);
        Option option = optionFactory.newInstance(task, messageOfOption, reward);
        if (option == null || option.equals(Option.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(option);
    }
    
    public Result read (Option option) 
    {
        if (option == null || option.equals(Option.EMPTY))
        {
            return badRequest("Failure.");
        }
        return ok(option.getMessage( ));
    }
    
    public Result update (Option option, String messageOfOption, Integer reward) 
    {
        OptionFactory optionFactory = Factories.<OptionFactory>getInstance(OptionFactory.class);
        if (messageOfOption != null && !messageOfOption.isEmpty( ))
        {
            option = optionFactory.update(option, messageOfOption);
        }
        if (reward != null)
        {
            option = optionFactory.update(option, reward);
        }
        if (option == null || option.equals(Option.EMPTY))
        {
            return badRequest("Failure.");
        }
        return this.read(option);
    }
    
    public Result destroy (Option option) 
    {
        OptionFactory optionFactory = Factories.<OptionFactory>getInstance(OptionFactory.class);
        boolean success = optionFactory.destroy(option);
        if (!success)
        {
            return badRequest("Failure.");
        }
        return ok("Success.");
    }
}
