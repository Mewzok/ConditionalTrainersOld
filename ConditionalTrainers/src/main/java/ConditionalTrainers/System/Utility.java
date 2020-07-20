package ConditionalTrainers.System;

import java.util.Set;

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
		} else if(player.get(PlayerKeys.NPC_LOAD_SEARCH).get().booleanValue() == true && PlayerKeys.OBJECTIVE != null)
		{
			setAllFalse(player);
			
			loadTeam(event.getTargetEntity(), player);
		}
	}
	// Right click NPC event ending
	// CT Info beginning
	public void displayTeams(Entity entity, Player player)
	{	
		if (entity instanceof NPCTrainer)
		{	
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			if(trainer.getTeams().equals(null) || trainer.getTeams().isEmpty())
				player.sendMessage(Text.of("This NPC has no teams saved."));
			else
				player.sendMessage(Text.of(trainer.getTeams().toString()));
		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a trainer NPC."));
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
			
			String scobo = createScobo(objective, value);
			
			// reset player's data
			player.offer(PlayerKeys.OBJECTIVE, "");
			player.offer(PlayerKeys.VALUE, 0);
			
			NBTTagCompound nbt = new NBTTagCompound();
			NPCTrainer npc = (NPCTrainer)entity;
			
			
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			// dynamically name parties based on number in arraylist
			nbt.setString("party" + trainer.getTeams().size(), scobo);
			npc.getPokemonStorage().writeToNBT(nbt);
			
			trainer.addTeam(nbt);
			
			trainer.addScoreboard(scobo);
			
			player.sendMessage(Text.of("Team added."));
		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a trainer Npc."));
	}
	// CT Create ending
	// CT Load beginning
	public void loadTeam(Entity entity, Player player)
	{
		if (entity instanceof NPCTrainer)
		{
			// get command arguments
			String objectiveInput = player.get(PlayerKeys.OBJECTIVE).get();
			
			// reset player's data
			player.offer(PlayerKeys.OBJECTIVE, "");
			player.offer(PlayerKeys.VALUE, 0);
			
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			Boolean matches = false;
			
			int a = 0;
			int objTeamIdx = 0;
			do
			{
				if(trainer.getTeams().get(a).hasKey(objectiveInput))
				{
					matches = true;
					objTeamIdx = a;
				}
				a++;
			} while(matches != true || a < trainer.getTeams().size());
			
			if(matches == true && trainer.getScoreboard().contains(objectiveInput))
			{
				matches = false;
				int sepValInput = getVal(objectiveInput);
				
				int idx = trainer.getScoreboard().indexOf(objectiveInput);
				
				int valueActual = getVal(trainer.getScoreboard().get(idx));
				
				if(sepValInput == valueActual)
				{	
					npc.getPokemonStorage().readFromNBT(trainer.getTeams().get(objTeamIdx));
				} else
					player.sendMessage(Text.of("No team found for the given objective with the given value."));
			} else
				player.sendMessage(Text.of("No team found for the given objective."));

		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a trainer NPC."));;
	}
	// CT Load ending
	
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
		player.offer(PlayerKeys.NPC_LOAD_SEARCH, false);
		player.offer(PlayerKeys.NPC_DELETE_SEARCH, false);
		player.offer(PlayerKeys.NPC_INFO_SEARCH, false);
	}
	
	public static String createScobo(String objective, int value)
	{
		String scobo = objective + " " + Integer.toString(value);
		
		return scobo;
	}
	
	public static String getObj(String objective)
	{
		String[] split = objective.split("\\s+");
		String sepObjInput = split[0];
		
		return sepObjInput;
	}
	
	public static int getVal(String objective)
	{
		String[] split = objective.split("\\s+");
		int sepValInput = Integer.parseInt(split[1]);
		
		return sepValInput;
	}
	// General utility ending
}