package ConditionalTrainers.Data.Trainer;

import java.util.HashMap;
import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import com.pixelmonmod.pixelmon.api.storage.PartyStorage;

public class TrainerDataBuilder extends AbstractDataBuilder<TrainerData> implements DataManipulatorBuilder<TrainerData, ImmutableTrainerData>
{	
	public TrainerDataBuilder()
	{
		super(TrainerData.class, 1);
	}
	
	@Override
	public TrainerData create()
	{
		HashMap<String, PartyStorage> hm = new HashMap<String, PartyStorage>();
		hm.put("default", null);
		return new TrainerData(hm);
	}
	
	@Override
	public Optional<TrainerData> createFrom(DataHolder dataHolder)
	{
		return create().fill(dataHolder);
	}
	
	@Override
	protected Optional<TrainerData> buildContent(DataView container) throws InvalidDataException
	{
		return create().from(container);
	}
}
