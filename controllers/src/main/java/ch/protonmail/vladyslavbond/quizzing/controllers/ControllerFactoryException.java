package ch.protonmail.vladyslavbond.quizzing.controllers;

public final class ControllerFactoryException 
extends RuntimeException
{
    /**
     * 
     */
    private static final long serialVersionUID = -8386138820997085231L;

    ControllerFactoryException (String message, Throwable cause)
    {
        super(message, cause);
    }
}
