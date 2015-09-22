package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class ExamMapper 
extends NativeMapper<Exam>
{
    public ExamMapper ( )
    {
        super(Exam.class);
    }
    
    @Override
    public Exam build ( )
    {
        Identificator<Exam> id = NumericIdentificator.<Exam>valueOf(this.<Long>get("id", Long.class));
        String title = this.<String>get("title", String.class);
        Exam exam = new Exam (id, title);
        return exam;
    }

}
