package ConditionalTrainers.System;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Human;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.text.Text;

import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;

import ConditionalTrainers.Data.Player.PlayerKeys;
import net.minecraft.entity.player.EntityPlayerMP;

public class Utility
{
	static Player ctInfoPlayer = null;
	// CT Info beginning
	public static void searchingForNPC(Player player)
	{
		ctInfoPlayer = player;
		ctInfoPlayer.offer(PlayerKeys.NPC_SEARCH, true);
		ctInfoPlayer.sendMessage(Text.of("Right click the NPC"));
	}
	
	@Listener
	public void onRightClickEntity(InteractEntityEvent.Secondary event)
	{
		if(ctInfoPlayer.get(PlayerKeys.NPC_SEARCH).get() == true)
			ctInfoPlayer.offer(PlayerKeys.NPC_SEARCH, false);
			if(event.getCause().last(Player.class).get() == ctInfoPlayer)
			{
				System.out.println(event.getTargetEntity().getType());
				ctInfoPlayer = null;
				if(event.getTargetEntity().getType().getId() != "pixelmon:trainer")
				{
					ctInfoPlayer.sendMessage(Text.of("Not a valid Trainer NPC."));
				} else
				{
					displayTeams(event.getTargetEntity());
				}
			} else
			{
				System.out.println("Not same player");
				ctInfoPlayer = null;
			}
	}
	
	public void displayTeams(Entity entity)
	{	
		if (entity instanceof NPCTrainer)
		{
			NPCTrainer trainer = (NPCTrainer)entity;
		}
	}
	// CT Info end
}