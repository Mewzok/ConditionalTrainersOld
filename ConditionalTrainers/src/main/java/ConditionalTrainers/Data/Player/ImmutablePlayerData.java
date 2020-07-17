package ConditionalTrainers.Data.Player;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutablePlayerData extends AbstractImmutableData<ImmutablePlayerData, PlayerData>
{
	private Boolean npcInfoSearch;
	private Boolean npcCreateSearch;
	private Boolean npcDeleteSearch;
	
	ImmutablePlayerData(Boolean npcInfoSearch, Boolean npcCreateSearch, Boolean npcDeleteSearch)
	{
		this.npcInfoSearch = npcInfoSearch;
		this.npcCreateSearch = npcCreateSearch;
		this.npcDeleteSearch = npcDeleteSearch;
		
		registerGetters();
	}
	
	@Override
	protected void registerGetters()
	{
		registerFieldGetter(PlayerKeys.NPC_INFO_SEARCH, () -> this.npcInfoSearch);
		registerFieldGetter(PlayerKeys.NPC_CREATE_SEARCH, () -> this.npcCreateSearch);
		registerFieldGetter(PlayerKeys.NPC_DELETE_SEARCH, () -> this.npcDeleteSearch);
		
		registerKeyValue(PlayerKeys.NPC_INFO_SEARCH, this::npcInfoSearch);
		registerKeyValue(PlayerKeys.NPC_CREATE_SEARCH, this::npcCreateSearch);
		registerKeyValue(PlayerKeys.NPC_DELETE_SEARCH, this::npcDeleteSearch);
	}
	
	public ImmutableValue<Boolean> npcInfoSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_INFO_SEARCH, npcInfoSearch).asImmutable();
	}
	
	public ImmutableValue<Boolean> npcCreateSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_CREATE_SEARCH, npcCreateSearch).asImmutable();
	}
	
	public ImmutableValue<Boolean> npcDeleteSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_DELETE_SEARCH, npcDeleteSearch).asImmutable();
	}
	
	@Override
	public PlayerData asMutable()
	{
		return new PlayerData(npcInfoSearch, npcCreateSearch, npcDeleteSearch);
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
				.set(PlayerKeys.NPC_INFO_SEARCH.getQuery(), this.npcInfoSearch)
				.set(PlayerKeys.NPC_CREATE_SEARCH.getQuery(), this.npcCreateSearch)
				.set(PlayerKeys.NPC_DELETE_SEARCH.getQuery(), this.npcDeleteSearch);
	}
}
