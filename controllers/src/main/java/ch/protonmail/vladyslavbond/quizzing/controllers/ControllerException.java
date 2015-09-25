package ch.protonmail.vladyslavbond.quizzing.controllers;

public abstract class ControllerException 
extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 3729480966598099622L;

    ControllerException (String message, Throwable cause)
    {
        super(message, cause);
    }
    
    ControllerException (Throwable cause)
    {
        this("Controller failed to perform it's duties.", cause);
    }

    public ControllerException(String string)
    {
        super(string);
    }
}
