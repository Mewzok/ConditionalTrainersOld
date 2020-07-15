package ConditionalTrainers.Data.Trainer;

import java.util.HashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;

public class ImmutableTrainerData extends AbstractImmutableData<ImmutableTrainerData, TrainerData>
{
	private HashMap<String, PartyStorage> teams;
	
	ImmutableTrainerData(HashMap<String, PartyStorage> teams)
	{
		this.teams = teams;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters()
	{
		registerFieldGetter(TrainerKeys.TEAMS, () -> this.teams);
		
		registerKeyValue(TrainerKeys.TEAMS, this::teams);
	}
	
	public ImmutableValue<HashMap<String, PartyStorage>> teams()
	{
		return Sponge.getRegistry().getValueFactory().createValue(TrainerKeys.TEAMS, teams).asImmutable();
	}
	
	@Override
	public TrainerData asMutable()
	{
		return new TrainerData(teams);
	}
	
	@Override
	public int getContentVersion()
	{
		return 1;
	}
	
	// Writes data to NBT
	@Override
	public DataContainer toContainer()
	{
		return super.toContainer()
				.set(TrainerKeys.TEAMS.getQuery(), this.teams);
	}
}
