package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class PoolMapperException 
extends MapperException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1779827392989745348L;

    PoolMapperException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    PoolMapperException (Throwable cause)
    {
        super("Failed to map entity.", cause);
    }
}
