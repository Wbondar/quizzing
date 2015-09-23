package ch.protonmail.vladyslavbond.quizzing.domain;

public class InstructorFactoryException 
extends MemberFactoryException
{
    /**
     * 
     */
    private static final long serialVersionUID = 5886051527148407125L;

    InstructorFactoryException (Throwable cause)
    {
        super("Failed to get instructor.", cause);
    }
}
