package ConditionalTrainers.Data.Trainer;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

public class Trainer implements ITrainer
{
	private ArrayList<NBTTagCompound> teams = new ArrayList<NBTTagCompound>();
	private ArrayList<String> scoreboard = new ArrayList<String>();
	private NBTTagCompound teamData = new NBTTagCompound();
	
	public void setTeams(ArrayList<NBTTagCompound> teams)
	{
		this.teams = teams;
	}

	public ArrayList<NBTTagCompound> getTeams()
	{
		return teams;
	}

	public void addTeam(NBTTagCompound team)
	{
		this.teams.add(team);
	}

	public void removeTeam(NBTTagCompound tag)
	{
		this.teams.remove(tag);
	}

	public void setScoreboard(ArrayList<String> scoreboard)
	{
		this.scoreboard = scoreboard;
	}

	public ArrayList<String> getScoreboard()
	{
		return this.scoreboard;
	}

	public void addScoreboard(String scoreboard)
	{
		this.scoreboard.add(scoreboard);
	}

	public void removeScoreboard(String tag)
	{
		this.scoreboard.remove(tag);
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
