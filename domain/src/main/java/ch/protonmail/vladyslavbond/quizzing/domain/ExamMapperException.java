package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class ExamMapperException 
extends MapperException
{
    /**
     * 
     */
    private static final long serialVersionUID = 8466967877375638204L;

    ExamMapperException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    ExamMapperException (Throwable cause)
    {
        super("Failed to map entity.", cause);
    }
}
