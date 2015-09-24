package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class FinishedAssessmentMapperException 
extends MapperException
{
    /**
     * 
     */
    private static final long serialVersionUID = -8567692666562010841L;

    FinishedAssessmentMapperException (Throwable  cause)
    {
        super(cause);
    }

    public FinishedAssessmentMapperException(String string,
            Throwable e)
    {
        super(string, e);
    }
}
