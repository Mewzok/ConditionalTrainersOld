package ConditionalTrainers.Data.Trainer;

import java.util.ArrayList;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

public class TrainerStorage implements IStorage<ITrainer>
{
	@Override
	public NBTBase writeNBT(Capability<ITrainer> capability, ITrainer instance, EnumFacing sde)
	{
		NBTTagCompound comp = new NBTTagCompound();
		NBTTagList teamList = new NBTTagList();
		NBTTagList scoreList = new NBTTagList();
		
		for(int i = 0; i < instance.getScoreboard().size(); i++)
		{
			// Save team data
			teamList.appendTag(instance.getTeams().get(i));
			
			// Save scoreboard data
			NBTTagCompound n = new NBTTagCompound();
			n.setString("i", instance.getScoreboard().get(i));
			scoreList.appendTag(n);
		}
		
		comp.setTag("Teams", teamList);
		comp.setTag("Scoreboards", scoreList);
		
		return comp;
	}
	
	@Override
	public void readNBT(Capability<ITrainer> capability, ITrainer instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound comp = (NBTTagCompound) nbt;
		NBTTagList teamList = new NBTTagList();
		ArrayList<NBTTagCompound> teams = new ArrayList<NBTTagCompound>();
		ArrayList<String> scoreboards = new ArrayList<String>();
		
		teamList = comp.getTagList("Teams", 9);
		
		for(int i = 0; i < comp.getCompoundTag("Scoreboards").getSize(); i++)
		{
			// Read team data
			teams.add(teamList.getCompoundTagAt(i));
			
			// Read scoreboard data
			scoreboards.add(comp.getCompoundTag("Scoreboards").getString(Integer.toString(i)));
		}
		
		instance.setTeams(teams);
		instance.setScoreboard(scoreboards);
	}
}
