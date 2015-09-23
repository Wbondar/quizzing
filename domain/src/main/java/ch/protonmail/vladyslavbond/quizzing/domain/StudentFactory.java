package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.DataAccessException;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class StudentFactory 
extends SimpleFactory<Student>
implements Factory<Student>
{

    StudentFactory( )
    {
        super(Student.class, new StudentMapper ( ));
    }

    @Override
    public Student getInstance (Identificator<Student> id)
    {
        Object[] arguments = {((NumericIdentificator<Student>)id).longValue( )};
        try
        {
            return this.getDataAccess( ).fetch("SELECT * FROM view_students WHERE id = ?;", arguments);
        } 
        catch (DataAccessException e)
        {
            throw new StudentFactoryException (e);
        }
    }
}
