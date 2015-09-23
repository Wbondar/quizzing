package ch.protonmail.vladyslavbond.quizzing.domain;

public abstract class TaskTypeException 
extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 6706708035620715908L;

    TaskTypeException (String message)
    {
        super(message);
    }
    
    TaskTypeException (String message, Throwable cause)
    {
        super(message, cause);
    }
}
