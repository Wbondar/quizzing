package ch.protonmail.vladyslavbond.quizzing.controllers;

public abstract class ControllerException 
extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 3729480966598099622L;

    ControllerException (String message, Throwable cause)
    {
        super(message, cause);
    }
}
