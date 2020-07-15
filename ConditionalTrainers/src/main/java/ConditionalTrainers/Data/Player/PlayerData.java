package ConditionalTrainers.Data.Player;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

public class PlayerData extends AbstractData<PlayerData, ImmutablePlayerData>
{
	private Boolean npcSearch;
	
	PlayerData(Boolean npcSearch)
	{
		this.npcSearch = npcSearch;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters()
	{
		registerFieldGetter(PlayerKeys.NPC_SEARCH, () -> this.npcSearch);
		
		registerFieldSetter(PlayerKeys.NPC_SEARCH, npcSearch -> this.npcSearch = npcSearch);
		
		registerKeyValue(PlayerKeys.NPC_SEARCH, this::npcSearch);
	}
	
	public Value<Boolean> npcSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_SEARCH, npcSearch);
	}
	
	@Override
	public Optional<PlayerData> fill(DataHolder dataHolder, MergeFunction overlap)
	{
		Optional<PlayerData> otherData_ = dataHolder.get(PlayerData.class);
		if(otherData_.isPresent())
		{
			PlayerData otherData = otherData_.get();
			PlayerData finalData = overlap.merge(this, otherData);
			this.npcSearch = finalData.npcSearch;
		}
		return Optional.of(this);
	}
	
	@Override
	public Optional<PlayerData> from(DataContainer container)
	{
		return from((DataView) container);
	}
	
	public Optional<PlayerData> from(DataView view)
	{
		if(view.contains(PlayerKeys.NPC_SEARCH.getQuery()))
		{
			this.npcSearch = view.getBoolean(PlayerKeys.NPC_SEARCH.getQuery()).get();
			return Optional.of(this);
		} else
		{
			return Optional.empty();
		}
	}
	
	@Override
	public PlayerData copy()
	{
		return new PlayerData(this.npcSearch);
	}
	
	@Override
	public ImmutablePlayerData asImmutable()
	{
		return new ImmutablePlayerData(this.npcSearch);
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
				.set(PlayerKeys.NPC_SEARCH.getQuery(), this.npcSearch);
	}
}
