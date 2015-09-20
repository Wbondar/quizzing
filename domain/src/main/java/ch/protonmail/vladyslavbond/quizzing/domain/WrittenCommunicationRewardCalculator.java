package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.ArrayList;
import java.util.Collection;

final class WrittenCommunicationRewardCalculator 
extends RewardCalculator 
{
	WrittenCommunicationRewardCalculator (Task task)
	{
		super(task);
	}
	
	private ArrayList<String> input = new ArrayList<String> ( );
	
	@Override
	public boolean provideAnswer (String answer) 
	{
		if (answer != null && !answer.isEmpty( ))
		{
			this.input.add(answer);
			return true;
		}
		return false;
	}

	@Override
	public Integer score ( ) 
	{
		// TODO
		throw new YetToBeCheckedException ( );
	}
	
	@Override
	public final Collection<String> getInput ( )
	{
		return this.input;
	}
	
	private final static Identificator<RewardCalculator> ID = new IntIdentificator<RewardCalculator> (4);
	
	@Override
	public final Identificator<RewardCalculator> getId ( )
	{
		return WrittenCommunicationRewardCalculator.ID;
	}
}