package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public class TaskMapper 
extends ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper<Task> 
implements Mapper<Task>
{
    public TaskMapper ( )
    {
        super(Task.class);
    }
    
    @Override
    public Task build ( ) throws TaskMapperException
    {
        Task task = Task.EMPTY;
        try
        {
            Identificator<Task> idOfTask = NumericIdentificator.<Task>valueOf(this.<Integer>get("id", Integer.class));
            Integer idOfTaskType = this.<Integer>get("type_id", Integer.class);
            Class<? extends RewardCalculator> typeOfRewardCalculator = TaskType.valueOf(idOfTaskType).getTypeOfRewardCalculator( );
            String description = this.<String>get("description", String.class);
            OptionFactory optionFactory = Factories.<OptionFactory>getInstance(OptionFactory.class);
            Set<Option> options = optionFactory.getInstances(idOfTask);
            task = new Task(idOfTask, typeOfRewardCalculator, description, options);
        } catch (Exception e) {
            throw new TaskMapperException (e);
        }
        return task;
    }
}
