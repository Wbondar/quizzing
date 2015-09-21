package ch.protonmail.vladyslavbond.quizzing.domain;

import ch.protonmail.vladyslavbond.quizzing.datasource.Mapper;
import ch.protonmail.vladyslavbond.quizzing.util.Identificator;
import ch.protonmail.vladyslavbond.quizzing.util.NumericIdentificator;

class OptionMapper 
extends Mapper<Option>
{
    public OptionMapper ( )
    {
        super(Option.class);
    }
    
    @Override
    public Option build ( )
    {
        Identificator<Option> id = NumericIdentificator.<Option>valueOf(this.<Long>get("id", Long.class));
        String message = this.<String>get("message", String.class);
        Integer reward = this.<Integer>get("reward", Integer.class);
        Option option = new Option (id, message, reward);
        return option;
    }
}
