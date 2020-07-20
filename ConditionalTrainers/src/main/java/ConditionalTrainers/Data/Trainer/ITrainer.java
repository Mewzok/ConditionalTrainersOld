package ConditionalTrainers.Data.Trainer;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

public interface ITrainer
{
	// Sets a list of teams for the particular NPC
	public void setTeams(ArrayList<NBTTagCompound> teams);
	
	// Gets a list of all teams for the particular NPC
	public ArrayList<NBTTagCompound> getTeams();
	
	// Adds a team to the list
	public void addTeam(NBTTagCompound team);
	
	// Removes a team from the list
	public void removeTeam(NBTTagCompound tag);
	
	
	// Scoreboard objectives
	// Sets a list of all scoreboard objectives and scores
	public void setScoreboard(ArrayList<String> scoreboard);
	
	// Gets a list of all scoreboard objectives and scores
	public ArrayList<String> getScoreboard();
	
	// Adds a single scoreboard objective with a score to the list
	public void addScoreboard(String scoreboard);
	
	// Removes a single scoreboard objective with a score from the list
	public void removeScoreboard(String tag);
	
	// Team data
	// Sets all team data
	public void setTeamData(NBTTagCompound scoreboard, NBTTagCompound teams);
	
	// Gets all team data
	public NBTTagCompound getTeamData();
}
