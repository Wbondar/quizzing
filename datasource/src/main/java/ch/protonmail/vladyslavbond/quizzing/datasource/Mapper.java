package ch.protonmail.vladyslavbond.quizzing.datasource;

public interface Mapper<T>
{
    public abstract void set (String label, Class<?> parameterType, Object argument);
    public abstract <P> P get (String label, Class<P> parameterType) throws MapperException;
    public abstract T build ( ) throws MapperException;
    public abstract void clear();
    public abstract void remove(String label);
    public abstract Class<T> getType();
}
