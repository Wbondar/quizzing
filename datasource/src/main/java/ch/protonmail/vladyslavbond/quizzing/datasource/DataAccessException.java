package ch.protonmail.vladyslavbond.quizzing.datasource;

public class DataAccessException 
extends Exception
{
    DataAccessException (String message, Throwable cause)
    {
        super(message, cause);
    }
}
