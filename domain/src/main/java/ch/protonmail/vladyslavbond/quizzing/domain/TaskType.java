package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public enum TaskType
implements Identifiable<TaskType>
{
      ChoiceMultiple (1, ChoiceMultipleRewardCalculator.class)
    , ChoiceSingle (2, ChoiceSingleRewardCalculator.class)
    , Guess (3, GuessRewardCalculator.class)
    , WrittenCommunication (4, WrittenCommunicationRewardCalculator.class)
    ;
      
    private final Identificator<TaskType> id;
    private final Class<? extends RewardCalculator> typeOfRewardCalculator;

    private TaskType (Identificator<TaskType> id, Class<? extends RewardCalculator> typeOfRewardCalculator)
    {
        this.id = id;
        this.typeOfRewardCalculator = typeOfRewardCalculator;
    }
      
    private TaskType (int id, Class<? extends RewardCalculator> typeOfRewardCalculator)
    {
        this(NumericIdentificator.<TaskType>valueOf(id), typeOfRewardCalculator);
    }
    
    public static final TaskType valueOf (Identificator<TaskType> id)
    {
        for (TaskType taskType : TaskType.values( ))
        {
            int value = ((NumericIdentificator<TaskType>)taskType.getId( )).intValue( );
            int idValue = ((NumericIdentificator<TaskType>)id).intValue( );
            if (value == idValue)
            {
                return taskType;
            }
        }
        throw new UnknownTaskTypeException (id);
    }
    
    public static final TaskType valueOf (Integer id)
    {
        return TaskType.valueOf(NumericIdentificator.<TaskType>valueOf(id));
    }

    @Override
    public Identificator<TaskType> getId ( )
    {
        return this.id;
    }

    public Class<? extends RewardCalculator> getTypeOfRewardCalculator()
    {
        return this.typeOfRewardCalculator;
    }
}
