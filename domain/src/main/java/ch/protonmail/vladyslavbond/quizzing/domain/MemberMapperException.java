package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;

public class MemberMapperException 
extends MapperException
{

    /**
     * 
     */
    private static final long serialVersionUID = -2116954070837616386L;

    public MemberMapperException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MemberMapperException(String message)
    {
        super(message);
    }

    public MemberMapperException(Throwable cause)
    {
        this("Failed to build member instance.", cause);
    }

}
