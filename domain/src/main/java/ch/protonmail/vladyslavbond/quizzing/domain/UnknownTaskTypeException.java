package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

public final class UnknownTaskTypeException 
extends TaskTypeException
{
    /**
     * 
     */
    private static final long serialVersionUID = 4341141546701319096L;

    UnknownTaskTypeException (Identificator<TaskType> id)
    {
        super("There is no task type of id " + id.toString( ) + ".");
    }
    
    UnknownTaskTypeException (String title)
    {
        super("There is no task type of title \"" + title + "\".");
    }
}
