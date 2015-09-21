package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class PoolMapper extends Mapper<Pool>
{
    public PoolMapper ( )
    {
        super(Pool.class);
    }
    
    @Override
    public Pool build ( )
    {
        Identificator<Pool> id = NumericIdentificator.<Pool>valueOf(this.<Long>get("id", Long.class));
        String title = this.<String>get("title", String.class);
        Pool pool = new Pool (id, title);
        return pool;
    }
}
