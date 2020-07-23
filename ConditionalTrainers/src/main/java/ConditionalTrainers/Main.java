package ConditionalTrainers;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
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
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;

import ConditionalTrainers.Commands.CT;
import ConditionalTrainers.Commands.Create;
import ConditionalTrainers.Commands.Delete;
import ConditionalTrainers.Commands.Info;
import ConditionalTrainers.Commands.Load;
import ConditionalTrainers.Data.Player.PlayerData;
import ConditionalTrainers.Data.Player.ImmutablePlayerData;
import ConditionalTrainers.Data.Player.PlayerDataBuilder;
import ConditionalTrainers.Data.Player.PlayerKeys;
import ConditionalTrainers.Data.Trainer.CapabilityHandler;
import ConditionalTrainers.Data.Trainer.ITrainer;
import ConditionalTrainers.Data.Trainer.Trainer;
import ConditionalTrainers.Data.Trainer.TrainerStorage;
import ConditionalTrainers.System.ModListener;
import ConditionalTrainers.System.Utility;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

@Plugin(id = "conditionaltrainers", name = "Conditional Trainers", version = "0.0.1")
public class Main {
	@Inject
	PluginContainer container;
	
	// Command specs begin
	// Create - CT child
	CommandSpec ctcCommandSpec = CommandSpec.builder()
			.arguments(
					GenericArguments.onlyOne(GenericArguments.string(Text.of("objective"))),
					GenericArguments.onlyOne(GenericArguments.integer(Text.of("value")))
						)
			.executor(new Create())
			.build();
	// Load - CT child
	CommandSpec ctlCommandSpec = CommandSpec.builder()
			.arguments(
					GenericArguments.onlyOne(GenericArguments.string(Text.of("objective"))),
					GenericArguments.onlyOne(GenericArguments.integer(Text.of("value")))
							)
			.executor(new Load())
			.build();
	// Delete - CT child
	CommandSpec ctdCommandSpec = CommandSpec.builder()
			.arguments(
					GenericArguments.onlyOne(GenericArguments.string(Text.of("objective"))),
					GenericArguments.onlyOne(GenericArguments.integer(Text.of("value")))
					)
			.executor(new Delete())
			.build();
	// Info - CT child
	CommandSpec ctiCommandSpec = CommandSpec.builder()
			.executor(new Info())
			.build();
	// CT > Info
	CommandSpec ctCommandSpec = CommandSpec.builder()
			.child(ctiCommandSpec, "info", "i", "list")
			.child(ctcCommandSpec, "create", "c", "add")
			.child(ctlCommandSpec, "load", "l")
			.child(ctdCommandSpec, "delete", "del", "d")
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
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new ModListener());
		Pixelmon.EVENT_BUS.register(new ModListener());
		
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
	
	/*
	*/
}
