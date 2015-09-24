package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public interface Assessment
{
    public abstract Student getStudent()  throws StudentFactoryException;

    public abstract Set<Task> getTasks() throws TaskFactoryException;

    public abstract Set<Answer> getAnswers()  throws AnswerFactoryException;
    
    public abstract Set<Answer> getAnswers(Task task)  throws AnswerFactoryException;
}
