package ConditionalTrainers.Data.Trainer;

import java.util.ArrayList;
import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;

public class Trainer implements ITrainer
{
	private LinkedList<NBTTagCompound> teams = new LinkedList<NBTTagCompound>();
	private ArrayList<String> scoreboard = new ArrayList<String>();
	private NBTTagCompound teamData = new NBTTagCompound();
	
	public void setTeams(LinkedList<NBTTagCompound> teams)
	{
		this.teams = teams;
	}

	public LinkedList<NBTTagCompound> getTeams()
	{
		return teams;
	}

	public void addTeam(NBTTagCompound team)
	{
		this.teams.add(team);
	}

	public void removeTeam(int i)
	{
		NBTTagCompound n = this.teams.remove(i);
		System.out.println(n + " deleted.");
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
