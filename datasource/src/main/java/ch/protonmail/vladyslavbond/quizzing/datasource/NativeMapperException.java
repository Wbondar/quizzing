package ch.protonmail.vladyslavbond.quizzing.datasource;

public class NativeMapperException 
extends MapperException
{

    /**
     * 
     */
    private static final long serialVersionUID = 174540652140330234L;

    public NativeMapperException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NativeMapperException(Throwable cause)
    {
        super(cause);
    }
    
    public NativeMapperException (String message)
    {
        super(message);
    }

}
