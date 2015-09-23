package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Set;

public interface Assessment
{
    public abstract Student getStudent();

    public abstract Set<Task> getTasks();

    public abstract Set<Answer> getAnswers();
    
    public abstract Set<Answer> getAnswers(Task task);
}
