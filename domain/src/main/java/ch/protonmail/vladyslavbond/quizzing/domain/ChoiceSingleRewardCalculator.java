package ch.protonmail.vladyslavbond.quizzing.domain;

final class ChoiceSingleRewardCalculator 
extends RewardCalculator 
{
	public ChoiceSingleRewardCalculator (Task task) 
	{
		super(task);
	}
	
	private Option selectedOption = null;
	
	@Override
	public boolean provideAnswer (String answer)
	{
		this.selectedOption = null;
		Identificator<Option> idOfOption = new IntIdentificator<Option> (Integer.valueOf(answer));
		for (Option availableOption : this.getTask( ).getOptions( ))
		{
			if (availableOption.getId( ).equals(idOfOption))
			{
				this.selectedOption = availableOption;
				return true;
			}
		}
		throw new NoSuchOptionException ( );
	}
	
	@Override
	public Integer score ( )
	{
		if (this.selectedOption != null)
		{
			return this.selectedOption.getReward( );
		}
		return 0;
	}
	
	@Override
	public final java.util.Collection<String> getInput ( )
	{
		java.util.ArrayList<String> input = new java.util.ArrayList<String> ( );
		if (this.selectedOption != null)
		{
			input.add(this.selectedOption.getId( ).toString( ));
			return input;
		}
		return input;
	}
	
	private final static Identificator<RewardCalculator> ID = new IntIdentificator<RewardCalculator> (2);
	
	@Override
	public final Identificator<RewardCalculator> getId ( )
	{
		return ChoiceSingleRewardCalculator.ID;
	}
}
