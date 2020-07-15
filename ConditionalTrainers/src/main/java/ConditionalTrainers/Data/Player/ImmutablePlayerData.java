package ConditionalTrainers.Data.Player;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutablePlayerData extends AbstractImmutableData<ImmutablePlayerData, PlayerData>
{
	private Boolean npcSearch;
	
	ImmutablePlayerData(Boolean npcSearch)
	{
		this.npcSearch = npcSearch;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters()
	{
		registerFieldGetter(PlayerKeys.NPC_SEARCH, () -> this.npcSearch);
		
		registerKeyValue(PlayerKeys.NPC_SEARCH, this::npcSearch);
	}
	
	public ImmutableValue<Boolean> npcSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_SEARCH, npcSearch).asImmutable();
	}
	
	@Override
	public PlayerData asMutable()
	{
		return new PlayerData(npcSearch);
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
