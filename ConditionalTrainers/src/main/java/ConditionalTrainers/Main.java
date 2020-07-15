package ConditionalTrainers;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import ConditionalTrainers.Commands.CT;
import ConditionalTrainers.Commands.Info;
import ConditionalTrainers.System.Utility;

@Plugin(id = "conditionaltrainers", name = "Conditional Trainers", version = "0.0.1")
public class Main {
	
	private Logger logger;
	
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
		Sponge.getEventManager().registerListeners(this,  new Utility());
	}
	
	@Listener
	public void init(GameInitializationEvent e)
	{
		Sponge.getCommandManager().register(this, ctCommandSpec, "ct", "conditionaltrainers");
	}
}
