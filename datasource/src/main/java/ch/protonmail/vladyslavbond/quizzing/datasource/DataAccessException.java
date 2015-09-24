package ch.protonmail.vladyslavbond.quizzing.datasource;

public class DataAccessException 
extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -3429558867692340515L;

    DataAccessException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    DataAccessException (String message)
    {
        super(message);
    }
}
