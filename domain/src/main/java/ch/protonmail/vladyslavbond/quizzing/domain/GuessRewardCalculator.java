package ch.protonmail.vladyslavbond.quizzing.domain;

final class GuessRewardCalculator 
extends RewardCalculator 
{
	public GuessRewardCalculator (Task task) 
	{
		super(task);
	}
	
	private String guess = null;
	
	@Override
	public boolean provideAnswer (String answer)
	{
		this.guess = answer;
		return true;
	}
	
	@Override
	public Integer score ( )
	{
		Integer score = 0;
		if (this.guess != null && !this.guess.isEmpty( ))
		{
			String answer = this.guess.toLowerCase( );
			for (Option availableOption : this.getTask( ).getOptions( ))
			{
				if (availableOption.getMessage( ).toLowerCase( ).equals(answer))
				{
					return availableOption.getReward( );
				}
			}
		}
		return score;
	}
	
	@Override
	public final java.util.Collection<String> getInput ( )
	{
		java.util.ArrayList<String> input = new java.util.ArrayList<String> ( );
		if (this.guess != null)
		{
			input.add(this.guess);
			return input;
		}
		return input;
	}
	
	private final static Identificator<RewardCalculator> ID = new IntIdentificator<RewardCalculator> (3);
	
	@Override
	public final Identificator<RewardCalculator> getId ( )
	{
		return GuessRewardCalculator.ID;
	}
}
