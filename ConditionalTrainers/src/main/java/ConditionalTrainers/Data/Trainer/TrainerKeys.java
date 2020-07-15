package ConditionalTrainers.Data.Trainer;

import java.util.HashMap;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;

public class TrainerKeys
{
	private TrainerKeys() {}
	public static void dummy() {} // invoke static constructor
	public static final Key<Value<HashMap<String, PartyStorage>>> TEAMS;
	
	static
	{
		TEAMS = Key.builder()
				.type(new TypeToken<Value<HashMap<String, PartyStorage>>>(){})
				.id("ConditionalTrainers:TEAMS")
				.name("Teams")
				.query(DataQuery.of('.', "conditionaltrainers.teams"))
				.build();
	}
}
