package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public enum TaskType
implements Identifiable<TaskType>
{
      ChoiceMultiple (1)
    , ChoiceSingle (2)
    , Guess (3)
    , WrittenCommunication (4)
    ;
      
    private final Identificator<TaskType> id;

    private TaskType (Identificator<TaskType> id)
    {
        this.id = id;
    }
      
    private TaskType (int id)
    {
        this(NumericIdentificator.<TaskType>valueOf(id));
    }
    
    public static final TaskType valueOf (Identificator<TaskType> id)
    {
        for (TaskType taskType : TaskType.values( ))
        {
            if (taskType.getId( ).equals(id))
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
}
