package ch.protonmail.vladyslavbond.quizzing.domain;

public final class OptionFactory 
extends SimpleFactory<Option>
implements Factory<Option> 
{
	OptionFactory ( ) {}
	
	@Override
	public Option getInstance (Identificator<Option> id) 
	{
		return Option.EMPTY;
	}

	public Option newInstance(Task task, String messageOfOption, Integer reward) 
	{
		// TODO Auto-generated method stub
		return new Option (new IntIdentificator<Option> (task.getId( ).toString( ).concat(String.valueOf(task.getOptions( ).size( ) + 1))), messageOfOption, reward);
	}

	public Option update(Option option, Integer reward) 
	{
		// TODO Auto-generated method stub
		return new Option (option.getId( ), option.getMessage( ), reward);
	}

	public boolean destroy(Option option) {
		// TODO Auto-generated method stub
		return false;
	}

	public Option update (Option option, String messageOfOption) 
	{
		// TODO Auto-generated method stub
		return new Option (option.getId( ), messageOfOption, option.getReward( ));
	}
}
