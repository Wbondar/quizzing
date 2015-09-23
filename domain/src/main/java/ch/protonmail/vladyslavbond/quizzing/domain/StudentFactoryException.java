package ch.protonmail.vladyslavbond.quizzing.domain;

public class StudentFactoryException 
extends MemberFactoryException
{
    /**
     * 
     */
    private static final long serialVersionUID = 5886051527148407125L;

    StudentFactoryException (Throwable cause)
    {
        super("Failed to get student.", cause);
    }
}
