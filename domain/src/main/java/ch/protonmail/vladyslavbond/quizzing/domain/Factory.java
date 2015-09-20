package ch.protonmail.vladyslavbond.quizzing.domain;

interface Factory<T extends Identifiable<T>> 
{
	public abstract T getInstance (Identificator<T> id);
}
