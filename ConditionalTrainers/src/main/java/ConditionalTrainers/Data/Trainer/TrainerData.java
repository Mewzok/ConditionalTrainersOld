package ConditionalTrainers.Data.Trainer;

import java.util.HashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;

public class TrainerData extends AbstractData<TrainerData, ImmutableTrainerData>
{
	HashMap<String, PartyStorage> teams;
	
	TrainerData(HashMap<String, PartyStorage> teams)
	{
		this.teams = teams;
		
		registerGettersAndSetters();
	}
	
	public HashMap<String, PartyStorage> getTeams()
	{
		return this.teams;
	}
	
	public void setTeams(HashMap<String, PartyStorage> teams)
	{
		this.teams = teams;
	}
	
	@Override
	protected void registerGettersAndSetters()
	{
		registerKeyValue(TrainerKeys.TEAMS, this::teams);
		
		registerFieldGetter(TrainerKeys.TEAMS, this::getTeams);
		
		registerFieldSetter(TrainerKeys.TEAMS, this::setTeams);
	}
	
	public Value<HashMap<String, PartyStorage>> teams()
	{
		return Sponge.getRegistry().getValueFactory().createValue(TrainerKeys.TEAMS, teams);
	}
	
	@Override
	public Optional<TrainerData> fill(DataHolder dataHolder, MergeFunction overlap)
	{
		Optional<TrainerData> otherData_ = dataHolder.get(TrainerData.class);
		if(otherData_.isPresent())
		{
			TrainerData otherData = otherData_.get();
			TrainerData finalData = overlap.merge(this, otherData);
			this.teams = finalData.teams;
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<TrainerData> from(DataContainer container)
	{
		return from((DataView) container);
	}
	
	public Optional<TrainerData> from(DataView view)
	{
	if(view.contains(TrainerKeys.TEAMS.getQuery()))
		{
			this.teams = (HashMap<String, PartyStorage>) view.getMap(TrainerKeys.TEAMS.getQuery()).get();
			return Optional.of(this);
		} else
		{
			return Optional.empty();
		}
	}
	
	@Override
	public TrainerData copy()
	{
		return new TrainerData(this.teams);
	}
	
	@Override
	public ImmutableTrainerData asImmutable()
	{
		return new ImmutableTrainerData(this.teams);
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
