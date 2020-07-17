package ConditionalTrainers.Data.Trainer;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

public interface ITrainer
{
	// Sets a list of teams for the particular NPC
	public void setTeams(List<NBTTagCompound> teams);
	
	// Gets a list of all teams for the particular NPC
	public List<NBTTagCompound> getTeams();
	
	// Adds a team to the list
	public void addTeam(NBTTagCompound team);
	
	// Removes a team from the list
	public void removeTeam(NBTTagCompound team);
}
