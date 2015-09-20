package ch.protonmail.vladyslavbond.quizzing.domain;

public interface Identifiable<T> 
{
	public abstract Identificator<T> getId ( );
}
