package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class InstructorFactory 
extends SimpleFactory<Instructor>
implements Factory<Instructor>
{

    InstructorFactory( )
    {
        super(Instructor.class, new InstructorMapper ( ));
    }

    @Override
    public Instructor getInstance(Identificator<Instructor> id) throws InstructorFactoryException
    {
        Object[] arguments = {((NumericIdentificator<Instructor>)id).longValue( )};
        try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_instructors WHERE id = ?;", arguments);
        } 
        catch (MapperException | DataAccessException e)
        {
            throw new InstructorFactoryException (e);
        }
    }
}
