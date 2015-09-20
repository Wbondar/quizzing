package ch.protonmail.vladyslavbond.quizzing.domain;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

final class ChoiceMultipleRewardCalculator 
extends RewardCalculator 
{
	public ChoiceMultipleRewardCalculator (Task task) 
	{
		super(task);
	}
	
	private final Set<Option> selectedOptions = Collections.<Option>synchronizedSet(new HashSet<Option> ( ));
	
	@Override
	public boolean provideAnswer (String answer)
	{
		Identificator<Option> idOfOption = new IntIdentificator<Option> (Integer.valueOf(answer));
		for (Option availableOption : this.getTask( ).getOptions( ))
		{
			if (availableOption.getId( ).equals(idOfOption))
			{
				this.selectedOptions.add(availableOption);
				return true;
			}
		}
		throw new NoSuchOptionException ( );
	}
	
	@Override
	public Integer score ( )
	{
		Integer reward = 0;
		for (Option selectedOption : this.selectedOptions)
		{
			reward = reward + selectedOption.getReward( );
		}
		return reward;
	}
	
	public final java.util.Collection<String> getInput ( )
	{
		java.util.ArrayList<String> input = new java.util.ArrayList<String> ( );
		for (Option selectedOption : this.selectedOptions)
		{
			input.add(selectedOption.getId( ).toString( ));
		}
		return input;
	}
	
	private final static Identificator<RewardCalculator> ID = new IntIdentificator<RewardCalculator> (1);
	
	@Override
	public final Identificator<RewardCalculator> getId ( )
	{
		return ChoiceMultipleRewardCalculator.ID;
	}
}
