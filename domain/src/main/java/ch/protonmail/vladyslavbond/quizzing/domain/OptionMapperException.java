package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class OptionMapperException 
extends MapperException
{

    /**
     * 
     */
    private static final long serialVersionUID = 3491292336149628694L;

    OptionMapperException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    OptionMapperException (Throwable cause)
    {
        super("Failed to map entity.", cause);
    }
}
