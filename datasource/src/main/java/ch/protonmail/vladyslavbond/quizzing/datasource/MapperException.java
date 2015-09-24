package ch.protonmail.vladyslavbond.quizzing.datasource;

public abstract class MapperException 
extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -165441073015190542L;

    public MapperException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public MapperException (Throwable cause)
    {
        this("Failed to map entity.", cause);
    }

    public MapperException(String message)
    {
       super(message);
    }
}
