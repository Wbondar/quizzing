package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.util.Identifiable;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;

interface Factory<T extends Identifiable<T>> 
{
	public abstract T getInstance (Identificator<T> id);
}
