package ConditionalTrainers.Data.Player;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;


public class PlayerDataBuilder extends AbstractDataBuilder<PlayerData> implements DataManipulatorBuilder<PlayerData, ImmutablePlayerData>
{
	public PlayerDataBuilder()
	{
		super(PlayerData.class, 1);
	}
	
	@Override
	public PlayerData create()
	{
		return new PlayerData(false);
	}
	
	@Override
	public Optional<PlayerData> createFrom(DataHolder dataHolder)
	{
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<PlayerData> buildContent(DataView container) throws InvalidDataException
	{
		return create().from(container);
	}
}
