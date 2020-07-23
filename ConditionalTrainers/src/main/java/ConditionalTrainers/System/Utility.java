package ConditionalTrainers.System;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;

import ConditionalTrainers.Data.Player.PlayerData;
import ConditionalTrainers.Data.Player.PlayerKeys;
import ConditionalTrainers.Data.Trainer.ITrainer;
import ConditionalTrainers.Data.Trainer.TrainerProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;

public class Utility
{
	// Right click NPC event beginning
	@Listener
	public void onRightClickEntity(InteractEntityEvent.Secondary event, @Root Player player)
	{	
		if(PlayerKeys.OBJECTIVE.toString() == "")
		{
			setAllFalse(player);
			
			player.sendMessage(Text.of("Invalid objective."));
		} else if(player.get(PlayerKeys.NPC_INFO_SEARCH).get().booleanValue() == true && PlayerKeys.OBJECTIVE != null)
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
		} else if(player.get(PlayerKeys.NPC_DELETE_SEARCH).get().booleanValue() == true && PlayerKeys.OBJECTIVE != null)
		{
			setAllFalse(player);
			
			deleteTeam(event.getTargetEntity(), player);
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

			if(trainer.getTeams().isEmpty())
				player.sendMessage(Text.of("This NPC has no teams saved."));
			else
			{
				for(int i = 0; i < trainer.getTeams().size(); i++)
				{	
					player.sendMessage(Text.of(trainer.getTeams().get(i).getKeySet()));
				}
			}
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
			
			// name party based on objective
			//nbt.setString(scobo, "party" + trainer.getTeams().size());
			nbt.setBoolean(scobo, true);
			npc.getPokemonStorage().writeToNBT(nbt);
			
			trainer.addTeam(nbt);
			
			trainer.addScoreboard(scobo);
			
			player.sendMessage(Text.of("Team added."));
		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a valid trainer Npc."));
	}
	// CT Create ending
	// CT Load beginning
	public static void loadTeam(Entity entity, Player player)
	{
		if (entity instanceof NPCTrainer)
		{	
			// get command arguments
			String objectiveInput = player.get(PlayerKeys.OBJECTIVE).get();
			int valueInput = player.get(PlayerKeys.VALUE).get();
			
			String playerScobo = createScobo(objectiveInput, valueInput);
			
			// reset player's data
			player.offer(PlayerKeys.OBJECTIVE, "");
			player.offer(PlayerKeys.VALUE, 0);
			
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			
			Boolean matches = false;
			
			int objTeamIdx = 0;
			
			for(int a = 0; a < trainer.getTeams().size(); a++)
			{
				if(trainer.getTeams().get(a).getBoolean(playerScobo))
				{
					matches = true;
					objTeamIdx = a;
				}
			}
			
			if(matches == true && trainer.getScoreboard().contains(playerScobo))
			{
				matches = false;
				int sepValInput = getVal(playerScobo);
				
				int idx = trainer.getScoreboard().indexOf(playerScobo);
				
				int valueActual = getVal(trainer.getScoreboard().get(idx));
				
				if(sepValInput == valueActual)
				{	
					// load logic
					npc.getPokemonStorage().readFromNBT(trainer.getTeams().get(objTeamIdx));
					player.sendMessage(Text.of("Team loaded."));
				} else
					player.sendMessage(Text.of("No team found for the given objective with the given value."));
			} else
				player.sendMessage(Text.of("No team found for the given objective."));

		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a valid trainer NPC."));
	}
	// CT Load ending
	// CT AutoLoad beginning
	public static void autoLoadTeam(Entity entity, Player player)
	{
		if (entity instanceof NPCTrainer)
		{	
			World world = ((net.minecraft.entity.Entity) player).getEntityWorld();

			Scoreboard scoreboard = world.getScoreboard();
			
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			Map<ScoreObjective, Score> playerScoresMap = scoreboard.getObjectivesForEntity(player.getName());
			Collection<ScoreObjective> plObjs = playerScoresMap.keySet();
			ArrayList<ScoreObjective> playerObjs = new ArrayList<>(plObjs);
			ArrayList<Score> plScores = new ArrayList<>(playerScoresMap.values());
			ArrayList<String> playerScores = new ArrayList<String>();

			System.out.println("playterSCoresMap size: " + playerScoresMap.size());
			
			for(int i = 0; i < playerScoresMap.size(); i++)
			{
				String p1 = playerObjs.get(i).getName();
				String p2 = Integer.toString(plScores.get(i).getScorePoints());
				
				playerScores.add(p1 + " " + p2);
			}
			
			Boolean matches = false;
			int objTeamIdx = 0;
			
			String playerScobo = "";
			
			for(int i = 0; i < trainer.getTeams().size(); i++)
			{	
				for(int j = 0; j < playerScores.size(); j++) // this doesnt run
				{
					playerScobo = playerScores.get(j);
					
					if(trainer.getTeams().get(i).getBoolean(playerScobo))
					{
						matches = true;
						objTeamIdx = i;
					}
				}
			}
			
			if(matches == true)
			{
				// load logic
				npc.getPokemonStorage().readFromNBT(trainer.getTeams().get(objTeamIdx));
				player.sendMessage(Text.of("Team loaded."));
			} else
			{
				player.sendMessage(Text.of("No team found for the given objective with the given value."));
			}
		} else
		{
			player.sendMessage(Text.of("No team found for the given objective."));
		}
	}
	// CT AutoLoad ending
	// CT Delete beginning
	public static void deleteTeam(Entity entity, Player player)
	{
		if (entity instanceof NPCTrainer)
		{
			// get command arguments
			String objectiveInput = player.get(PlayerKeys.OBJECTIVE).get();
			int valueInput = player.get(PlayerKeys.VALUE).get();
			
			String playerScobo = createScobo(objectiveInput, valueInput);
			
			// reset player's data
			player.offer(PlayerKeys.OBJECTIVE, "");
			player.offer(PlayerKeys.VALUE, 0);
			
			NPCTrainer npc = (NPCTrainer)entity;
			ITrainer trainer = npc.getCapability(TrainerProvider.TRAINER_CAP, null);
			
			
			Boolean matches = false;
			
			int objTeamIdx = 0;
			
			for(int a = 0; a < trainer.getTeams().size(); a++)
			{
				if(trainer.getTeams().get(a).getBoolean(playerScobo))
				{
					matches = true;
					objTeamIdx = a;
				}
			}
			
			if(matches == true && trainer.getScoreboard().contains(playerScobo))
			{
				matches = false;
				int sepValInput = getVal(playerScobo);
				
				int idx = trainer.getScoreboard().indexOf(playerScobo);
				
				int valueActual = getVal(trainer.getScoreboard().get(idx));
				
				if(sepValInput == valueActual)
				{
					// delete logic
					trainer.removeTeam(objTeamIdx);
					trainer.removeScoreboard(playerScobo);
					
					player.sendMessage(Text.of("Team deleted."));
				} else
					player.sendMessage(Text.of("No team found for the given objective with the given value."));
			} else
				player.sendMessage(Text.of("No team found for the given objective."));
		} else
			player.sendMessage(Text.of("Invalid NPC type. Must be a valid trainer NPC."));
	}
	// CT Delete ending
	
	// Join creation or fill beginning
	public static void onJoinCreation(Player player)
	{
		player.offer(player.getOrCreate(PlayerData.class).get());
	}
	// Join creation or fill ending
	// Set Appropriate Teams beginning
	public static void setAppropriateTeams(BattleControllerBase b)
	{
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<NPCTrainer> trainers = new ArrayList<NPCTrainer>();
		
		for(int i = 0; i < b.participants.size(); i++)
		{	
			if(b.participants.get(i).getEntity() instanceof EntityPlayerMP)
			{
				players.add((Player)b.participants.get(i).getEntity());
			}
			
			if(b.participants.get(i).getEntity() instanceof NPCTrainer)
			{
				trainers.add((NPCTrainer)b.participants.get(i).getEntity());
			}
		}
		if(!trainers.isEmpty() && !players.isEmpty())
		{
			if(trainers.size() == 1 && players.size() == 1)
			{
				autoLoadTeam((Entity)trainers.get(0), players.get(0));
			} else if(trainers.size() == 2 && players.size() == 2)
			{
				autoLoadTeam((Entity)trainers.get(0), players.get(0));
				autoLoadTeam((Entity)trainers.get(1), players.get(1));
			} else
				autoLoadTeam((Entity)trainers.get(0), players.get(0));
		}
	}
	// Set Appropriate Teams ending
	
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