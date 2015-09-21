package ch.protonmail.vladyslavbond.quizzing.datasource;

public final class DataAccessFactory
{
    private DataAccessFactory ( ) {}
    
    public static <T> DataAccess<T> getInstance(Class<T> typeOfEntities, Mapper<T> mapper)
    {
        return new DataAccess<T> (NativeStatementFactory.INSTANCE, typeOfEntities, mapper);
    }
}
