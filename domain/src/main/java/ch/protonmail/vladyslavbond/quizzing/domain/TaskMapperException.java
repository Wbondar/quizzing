package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class TaskMapperException 
extends MapperException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1317383038923408824L;

    TaskMapperException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    TaskMapperException (Throwable cause)
    {
        super("Failed to map entity.", cause);
    }
}
