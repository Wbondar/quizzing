package ch.protonmail.vladyslavbond.quizzing.domain;

public class TaskFactoryException 
extends FactoryException
{
    /**
     * 
     */
    private static final long serialVersionUID = 3507492926622737553L;

    TaskFactoryException (String message, Throwable e)
    {
        super(message, e);
    }
}
