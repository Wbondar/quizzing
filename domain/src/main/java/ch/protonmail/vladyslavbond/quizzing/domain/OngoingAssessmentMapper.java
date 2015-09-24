package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

public final class OngoingAssessmentMapper
extends NativeMapper<OngoingAssessment>
implements Mapper<OngoingAssessment>
{
    public OngoingAssessmentMapper ( )
    {
        super(OngoingAssessment.class);
    }
    
    @Override
    public OngoingAssessment build ( ) throws OngoingAssessmentMapperException, MapperException
    {
        Identificator<OngoingAssessment> id = NumericIdentificator.<OngoingAssessment>valueOf(get("id", Long.class));
        StudentFactory studentFactory = Factories.<StudentFactory>getInstance(StudentFactory.class);
        Identificator<Student> idOfStudent = NumericIdentificator.<Student>valueOf(get("student_id", Long.class));
        Student student = Student.EMPTY;
        try
        {
            student = studentFactory.getInstance(idOfStudent);
        } catch (StudentFactoryException e)
        {
            throw new OngoingAssessmentMapperException (e);
        }
        return new OngoingAssessment (id, student);
    }
}
