package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class AssessmentMapper extends Mapper<Assessment>
{
    public AssessmentMapper ( )
    {
        super(Assessment.class);
    }
    
    @Override
    public Assessment build ( )
    {
        Identificator<Assessment> id = NumericIdentificator.<Assessment>valueOf(get("id", Long.class));
        MemberFactory memberFactory = Factories.<MemberFactory>getInstance(MemberFactory.class);
        Identificator<Member> idOfStudent = NumericIdentificator.<Member>valueOf(get("student_id", Long.class));
        Student student = memberFactory.getStudent(idOfStudent);
        TaskFactory taskFactory = Factories.<TaskFactory>getInstance(TaskFactory.class);
        Set<Task> tasks = taskFactory.getAssessmentInstances(id);
        Assessment assessment = new Assessment (id, student, tasks);
        return assessment;
    }

}
