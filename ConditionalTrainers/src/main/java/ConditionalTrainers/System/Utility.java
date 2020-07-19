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
import net.minecraft.nbt.NBTTagCompound;

public class Utility
{
	// Right click NPC event beginning
	@Listener
	public void onRightClickEntity(InteractEntityEvent.Secondary event, @Root Player player)
	{	
		if(player.get(PlayerKeys.NPC_INFO_SEARCH).get().booleanValue() == true)
		{
			setAllFalse(player);
			
			displayTeams(event.getTargetEntity(), player);
		} else if(player.get(PlayerKeys.NPC_CREATE_SEARCH).get().booleanValue() == true && PlayerKeys.OBJECTIVE != null)
		{
			setAllFalse(player);
			
			createTeam(event.getTargetEntity(), player);
		}
	}
	// Right click NPC event ending
	// CT Info beginning
	public void displayTeams(Entity entity, Player player)
	{	
		if (entity instanceof NPCTrainer)
		{	
			// debug
			System.out.println("Entity is instanceof NPCTrainer");
			
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			// debug
			System.out.println("Trainer assigned capability");
			
			if(trainer.getTeams().equals(null))
				player.sendMessage(Text.of("This NPC has no teams saved."));
			else
				player.sendMessage(Text.of(trainer.getTeams()));
		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a trainer Npc."));
	}
	// CT Info ending
	
	// CT Create beginning
	public static void createTeam(Entity entity, Player player)
	{
		if(entity instanceof NPCTrainer)
		{
			// get command arguments
			String objective = player.get(PlayerKeys.OBJECTIVE).get();
			int value = player.get(PlayerKeys.VALUE).get();
			
			// reset player's data
			player.offer(PlayerKeys.OBJECTIVE, "");
			player.offer(PlayerKeys.VALUE, 0);
			
			NBTTagCompound nbt = new NBTTagCompound();
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			NBTTagCompound scobo = new NBTTagCompound();
			
			scobo.setInteger(objective, value);
			
			npc.getPokemonStorage().writeToNBT(nbt);
			
			// debug
			System.out.println("Team NBT: " + nbt.getKeySet()); // this saves as party0
			
			// This breaks
			trainer.addTeam(nbt);
			
			trainer.addScoreboard(scobo);
			
			player.sendMessage(Text.of("Team added."));
		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a trainer Npc."));
	}
	// CT Create ending
	
	// Join creation or fill beginning
	public static void onJoinCreation(Player player)
	{
		player.offer(player.getOrCreate(PlayerData.class).get());
	}
	// Join creation or fill ending
	// General utility beginning
	public static void setAllFalse(Player player)
	{
		player.offer(PlayerKeys.NPC_CREATE_SEARCH, false);
		player.offer(PlayerKeys.NPC_DELETE_SEARCH, false);
		player.offer(PlayerKeys.NPC_INFO_SEARCH, false);
	}
	// General utility ending
}