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
	private Boolean npcLoadSearch;
	private Boolean npcDeleteSearch;
	private String objective;
	private int value;
	
	PlayerData(Boolean npcInfoSearch, Boolean npcCreateSearch, Boolean npcLoadSearch, Boolean npcDeleteSearch, String objective,
			int value)
	{
		this.npcInfoSearch = npcInfoSearch;
		this.npcCreateSearch = npcCreateSearch;
		this.npcLoadSearch = npcLoadSearch;
		this.npcDeleteSearch = npcDeleteSearch;
		this.objective = objective;
		this.value = value;
		
		registerGettersAndSetters();
	}
	
	@Override
	protected void registerGettersAndSetters()
	{
		registerFieldGetter(PlayerKeys.NPC_INFO_SEARCH, () -> this.npcInfoSearch);
		registerFieldGetter(PlayerKeys.NPC_CREATE_SEARCH, () -> this.npcCreateSearch);
		registerFieldGetter(PlayerKeys.NPC_LOAD_SEARCH, () -> this.npcLoadSearch);
		registerFieldGetter(PlayerKeys.NPC_DELETE_SEARCH, () -> this.npcDeleteSearch);
		registerFieldGetter(PlayerKeys.OBJECTIVE, () -> this.objective);
		registerFieldGetter(PlayerKeys.VALUE, () -> this.value);
		
		registerFieldSetter(PlayerKeys.NPC_INFO_SEARCH, npcInfoSearch -> this.npcInfoSearch = npcInfoSearch);
		registerFieldSetter(PlayerKeys.NPC_CREATE_SEARCH, npcCreateSearch -> this.npcCreateSearch = npcCreateSearch);
		registerFieldSetter(PlayerKeys.NPC_LOAD_SEARCH, npcLoadSearch -> this.npcLoadSearch = npcLoadSearch);
		registerFieldSetter(PlayerKeys.NPC_DELETE_SEARCH, npcDeleteSearch -> this.npcDeleteSearch = npcDeleteSearch);
		registerFieldSetter(PlayerKeys.OBJECTIVE, objective -> this.objective = objective);
		registerFieldSetter(PlayerKeys.VALUE, value -> this.value = value);
		
		registerKeyValue(PlayerKeys.NPC_INFO_SEARCH, this::npcInfoSearch);
		registerKeyValue(PlayerKeys.NPC_CREATE_SEARCH, this::npcCreateSearch);
		registerKeyValue(PlayerKeys.NPC_LOAD_SEARCH, this::npcLoadSearch);
		registerKeyValue(PlayerKeys.NPC_DELETE_SEARCH, this::npcDeleteSearch);
		registerKeyValue(PlayerKeys.OBJECTIVE, this::objective);
		registerKeyValue(PlayerKeys.VALUE, this::value);
	}
	
	public Value<Boolean> npcInfoSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_INFO_SEARCH, npcInfoSearch);
	}
	
	public Value<Boolean> npcCreateSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_CREATE_SEARCH, npcCreateSearch);
	}
	
	public Value<Boolean> npcLoadSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_LOAD_SEARCH, npcLoadSearch);
	}
	
	public Value<Boolean> npcDeleteSearch()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.NPC_DELETE_SEARCH, npcDeleteSearch);
	}
	
	public Value<String> objective()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.OBJECTIVE, objective);
	}

	public Value<Integer> value()
	{
		return Sponge.getRegistry().getValueFactory().createValue(PlayerKeys.VALUE, value);
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
			this.npcLoadSearch = finalData.npcLoadSearch;
			this.npcDeleteSearch = finalData.npcDeleteSearch;
			this.objective = finalData.objective;
			this.value = finalData.value;
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
				view.contains(PlayerKeys.NPC_LOAD_SEARCH.getQuery()) && view.contains(PlayerKeys.NPC_DELETE_SEARCH.getQuery()) 
				&& view.contains(PlayerKeys.OBJECTIVE.getQuery()) && view.contains(PlayerKeys.VALUE.getQuery()))
		{
			this.npcInfoSearch = view.getBoolean(PlayerKeys.NPC_INFO_SEARCH.getQuery()).get();
			this.npcCreateSearch = view.getBoolean(PlayerKeys.NPC_CREATE_SEARCH.getQuery()).get();
			this.npcLoadSearch = view.getBoolean(PlayerKeys.NPC_LOAD_SEARCH.getQuery()).get();
			this.npcDeleteSearch = view.getBoolean(PlayerKeys.NPC_DELETE_SEARCH.getQuery()).get();
			this.objective = view.getString(PlayerKeys.OBJECTIVE.getQuery()).get();
			this.value = view.getInt(PlayerKeys.VALUE.getQuery()).get();
			return Optional.of(this);
		} else
		{
			return Optional.empty();
		}
	}
	
	@Override
	public PlayerData copy()
	{
		return new PlayerData(this.npcInfoSearch, this.npcCreateSearch, this.npcLoadSearch, this.npcDeleteSearch, this.objective,
				this.value);
	}
	
	@Override
	public ImmutablePlayerData asImmutable()
	{
		return new ImmutablePlayerData(this.npcInfoSearch, this.npcCreateSearch, this.npcLoadSearch, this.npcDeleteSearch,
				this.objective, this.value);
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
				.set(PlayerKeys.NPC_LOAD_SEARCH.getQuery(), this.npcLoadSearch)
				.set(PlayerKeys.NPC_DELETE_SEARCH.getQuery(), this.npcDeleteSearch)
				.set(PlayerKeys.OBJECTIVE.getQuery(), this.objective)
				.set(PlayerKeys.VALUE.getQuery(), this.value);
	}
}
