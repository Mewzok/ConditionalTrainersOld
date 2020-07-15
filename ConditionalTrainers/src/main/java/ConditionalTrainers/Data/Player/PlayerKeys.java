package ConditionalTrainers.Data.Player;

import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.TypeTokens;

public class PlayerKeys
{
	private PlayerKeys() {}
	public static void dummy() {} // invoke static constructor
	public static final Key<Value<Boolean>> NPC_SEARCH;
	
	static
	{
		NPC_SEARCH = Key.builder()
				.type(TypeTokens.BOOLEAN_VALUE_TOKEN)
				.id("ConditionalTrainers:NPC_SEARCH")
				.name("NPC Search")
				.query(DataQuery.of('.', "conditionaltrainers.npcsearch"))
				.build();
	}
}
