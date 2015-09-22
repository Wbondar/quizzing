package ch.protonmail.vladyslavbond.quizzing.datasource;

public interface Mapper<T>
{
    public abstract void set (String label, Class<?> parameterType, Object argument);
    public abstract <P> P get (String label, Class<P> parameterType);
    public abstract T build ( );
    public abstract void clear();
    public abstract void remove(String label);
    public abstract Class<T> getType();
}
