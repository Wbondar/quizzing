package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class ScoreMapperException 
extends MapperException
{
    /**
     * 
     */
    private static final long serialVersionUID = 79436237124546045L;

    ScoreMapperException (Throwable cause)
    {
        super("Failed to map entity.", cause);
    }
}
