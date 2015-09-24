package ch.protonmail.vladyslavbond.quizzing.util;

public interface Identificator<T> 
{
    public abstract NumericIdentificator<T> toNumber ( );
}