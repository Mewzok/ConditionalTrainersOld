package ConditionalTrainers;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.data.Has;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.inject.Inject;

import ConditionalTrainers.Commands.CT;
import ConditionalTrainers.Commands.Info;
import ConditionalTrainers.Data.Player.PlayerData;
import ConditionalTrainers.Data.Player.ImmutablePlayerData;
import ConditionalTrainers.Data.Player.PlayerDataBuilder;
import ConditionalTrainers.Data.Player.PlayerKeys;
import ConditionalTrainers.Data.Trainer.CapabilityHandler;
import ConditionalTrainers.Data.Trainer.ITrainer;
import ConditionalTrainers.Data.Trainer.Trainer;
import ConditionalTrainers.Data.Trainer.TrainerStorage;
import ConditionalTrainers.System.Utility;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Plugin(id = "conditionaltrainers", name = "Conditional Trainers", version = "0.0.1")
public class Main {
	@Inject
	PluginContainer container;
	
	// Command specs begin
	// Info - CT child
	CommandSpec ctiCommandSpec = CommandSpec.builder()
			.executor(new Info())
			.build();
	// CT > Info
	CommandSpec ctCommandSpec = CommandSpec.builder()
			.child(ctiCommandSpec, "info", "i")
			.executor(new CT())
			.build();
	// Command specs end
	
	@Listener
	public void preInit(GamePreInitializationEvent e)
	{
		// Register data
		PlayerKeys.dummy();
		DataRegistration.builder()
		.name("Player Data")
		.id("player_data")
		.dataClass(PlayerData.class)
		.immutableClass(ImmutablePlayerData.class)
		.builder(new PlayerDataBuilder())
		.build();
		
		// Register capabilities
		CapabilityManager.INSTANCE.register(ITrainer.class, new TrainerStorage(), Trainer::new);
		
		// Register event handlers
		MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);
		
		// Register event listeners outside of main class
		Sponge.getEventManager().registerListeners(this, new Utility());
	}
	
	@Listener
	public void init(GameInitializationEvent e)
	{
		Sponge.getCommandManager().register(this, ctCommandSpec, "ct", "conditionaltrainers");
	}
	
	@Listener
	public void onFirstJoin(ClientConnectionEvent.Join e, @Getter("getTargetEntity") @Has(value = PlayerData.class, inverse = true) Player player)
	{
		Utility.onJoinCreation(player);
	}
	
	@Listener
	public void onReturningJoin(ClientConnectionEvent.Join e, @Getter("getTargetEntity") @Has(value = PlayerData.class, inverse = false) Player player)
	{
		Utility.onJoinCreation(player);
	}
}
