package ch.protonmail.vladyslavbond.quizzing.controllers;

public class AssessmentsControllerException 
extends ControllerException
{
    /**
     * 
     */
    private static final long serialVersionUID = 7821416949695449989L;

    AssessmentsControllerException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    AssessmentsControllerException (Throwable cause)
    {
        super(cause);
    }
}
