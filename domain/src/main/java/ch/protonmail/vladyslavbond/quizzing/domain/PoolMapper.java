package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.MapperException;
import ch.protonmail.vladyslavbond.quizzing.datasource.NativeMapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class PoolMapper extends NativeMapper<Pool>
{
    public PoolMapper ( )
    {
        super(Pool.class);
    }
    
    @Override
    public Pool build ( ) throws PoolMapperException, MapperException
    {
        Identificator<Pool> id = NumericIdentificator.<Pool>valueOf(this.<Integer>get("id", Integer.class));
        String title = this.<String>get("title", String.class);
        Pool pool = new Pool (id, title);
        return pool;
    }
}
