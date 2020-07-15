package ConditionalTrainers.Commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import ConditionalTrainers.System.Utility;

//import ConditionalTrainers.System.Utility;

public class Info implements CommandExecutor
{
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			Player player = (Player)src;
			
			Utility.searchingForNPC(player);
		}
		else
			src.sendMessage(Text.of("This command must be run by a player."));	
		return CommandResult.success();
	}
}
