package ConditionalTrainers.System;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;

import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;

import ConditionalTrainers.Data.Player.PlayerData;
import ConditionalTrainers.Data.Player.PlayerKeys;
import ConditionalTrainers.Data.Trainer.ITrainer;
import ConditionalTrainers.Data.Trainer.TrainerProvider;

public class Utility
{
	// CT Info beginning
	@Listener
	public void onRightClickEntity(InteractEntityEvent.Secondary event, @Root Player player)
	{
		// debug
		System.out.println(player.get(PlayerKeys.NPC_INFO_SEARCH).get().booleanValue());
		System.out.println("Right click registered");
		
		if(player.get(PlayerKeys.NPC_INFO_SEARCH).get().booleanValue() == true)
		{
			//debug
			System.out.println("Info search registered");
			
			player.offer(PlayerKeys.NPC_INFO_SEARCH, false);
			
			displayTeams(event.getTargetEntity(), player);
		} 
	}
	
	public void displayTeams(Entity entity, Player player)
	{	
		if (entity instanceof NPCTrainer)
		{	
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			player.sendMessage(Text.of(trainer.getTeams()));
		}
	}
	// CT Info end
	
	// Join creation or fill beginning
	public static void onJoinCreation(Player player)
	{
		player.offer(player.getOrCreate(PlayerData.class).get());
	}
	// Join creation or fill ending
}