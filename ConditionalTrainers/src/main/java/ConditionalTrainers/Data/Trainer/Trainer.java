package ConditionalTrainers.Data.Trainer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class Trainer implements ITrainer
{
	private NBTTagCompound teams;
	private NBTTagCompound scoreboard;
	private NBTTagCompound teamData;
	
	public void setTeams(NBTTagCompound teams)
	{
		this.teams = teams;
	}

	public NBTTagCompound getTeams()
	{
		return teams;
	}

	public void addTeam(NBTTagCompound team)
	{
		teams.merge(team);
	}

	public void removeTeam(String tag)
	{
		teams.removeTag(tag);
	}

	public void setScoreboard(NBTTagCompound scoreboard)
	{
		this.scoreboard = scoreboard;
	}

	public NBTTagCompound getScoreboard()
	{
		return this.scoreboard;
	}

	public void addScoreboard(NBTTagCompound scoreboard)
	{
		this.scoreboard.merge(scoreboard);
	}

	public void removeScoreboard(String tag)
	{
		this.scoreboard.removeTag(tag);
	}

	public void setTeamData(NBTTagCompound scoreboard, NBTTagCompound teams)
	{
		NBTTagCompound teamData = scoreboard;
		teamData.merge(teams);
		
		this.teamData = teamData;
	}

	public NBTTagCompound getTeamData()
	{
		return this.teamData;
	}
}
