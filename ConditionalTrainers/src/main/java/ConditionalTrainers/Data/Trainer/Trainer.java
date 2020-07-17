package ConditionalTrainers.Data.Trainer;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public class Trainer implements ITrainer
{
	private List<NBTTagCompound> teams;
	
	public void setTeams(List<NBTTagCompound> teams)
	{
		this.teams = teams;
	}

	public List<NBTTagCompound> getTeams()
	{
		return teams;
	}

	public void addTeam(NBTTagCompound team)
	{
		teams.add(team);
	}

	public void removeTeam(NBTTagCompound team)
	{
		teams.remove(team);
	}
	
}
