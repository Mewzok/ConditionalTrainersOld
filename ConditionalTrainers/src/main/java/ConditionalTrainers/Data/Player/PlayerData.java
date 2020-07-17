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
	private Boolean npcInfoSearch;
	private Boolean npcCreateSearch;
	private Boolean npcDeleteSearch;
	
	PlayerData(Boolean npcInfoSearch, Boolean npcCreateSearch, Boolean npcDeleteSearch)
	{
		this.npcInfoSearch = npcInfoSearch;
		this.npcCreateSearch = npcCreateSearch;
		this.npcDeleteSearch = npcDeleteSearch;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters()
	{
		registerFieldGetter(PlayerKeys.NPC_INFO_SEARCH, () -> this.npcInfoSearch);
		registerFieldGetter(PlayerKeys.NPC_CREATE_SEARCH, () -> this.npcCreateSearch);
		registerFieldGetter(PlayerKeys.NPC_DELETE_SEARCH, () -> this.npcDeleteSearch);
		
		registerFieldSetter(PlayerKeys.NPC_INFO_SEARCH, npcInfoSearch -> this.npcInfoSearch = npcInfoSearch);
		registerFieldSetter(PlayerKeys.NPC_CREATE_SEARCH, npcCreateSearch -> this.npcCreateSearch = npcCreateSearch);
		registerFieldSetter(PlayerKeys.NPC_DELETE_SEARCH, npcDeleteSearch -> this.npcDeleteSearch = npcDeleteSearch);
		
		registerKeyValue(PlayerKeys.NPC_INFO_SEARCH, this::npcInfoSearch);
		registerKeyValue(PlayerKeys.NPC_CREATE_SEARCH, this::npcCreateSearch);
		registerKeyValue(PlayerKeys.NPC_DELETE_SEARCH, this::npcDeleteSearch);
	}
	
	public Value<Boolean> npcInfoSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_INFO_SEARCH, npcInfoSearch);
	}
	
	public Value<Boolean> npcCreateSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_CREATE_SEARCH, npcCreateSearch);
	}
	
	public Value<Boolean> npcDeleteSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_DELETE_SEARCH, npcDeleteSearch);
	}
	
	@Override
	public Optional<PlayerData> fill(DataHolder dataHolder, MergeFunction overlap)
	{
		Optional<PlayerData> otherData_ = dataHolder.get(PlayerData.class);
		if(otherData_.isPresent())
		{
			PlayerData otherData = otherData_.get();
			PlayerData finalData = overlap.merge(this, otherData);
			this.npcInfoSearch = finalData.npcInfoSearch;
			this.npcCreateSearch = finalData.npcCreateSearch;
			this.npcDeleteSearch = finalData.npcDeleteSearch;
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
		if(view.contains(PlayerKeys.NPC_INFO_SEARCH.getQuery()) && view.contains(PlayerKeys.NPC_CREATE_SEARCH.getQuery()) &&
				view.contains(PlayerKeys.NPC_DELETE_SEARCH.getQuery()))
		{
			this.npcInfoSearch = view.getBoolean(PlayerKeys.NPC_INFO_SEARCH.getQuery()).get();
			this.npcCreateSearch = view.getBoolean(PlayerKeys.NPC_CREATE_SEARCH.getQuery()).get();
			this.npcDeleteSearch = view.getBoolean(PlayerKeys.NPC_DELETE_SEARCH.getQuery()).get();
			return Optional.of(this);
		} else
		{
			return Optional.empty();
		}
	}
	
	@Override
	public PlayerData copy()
	{
		return new PlayerData(this.npcInfoSearch, this.npcCreateSearch, this.npcDeleteSearch);
	}
	
	@Override
	public ImmutablePlayerData asImmutable()
	{
		return new ImmutablePlayerData(this.npcInfoSearch, this.npcCreateSearch, this.npcDeleteSearch);
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
