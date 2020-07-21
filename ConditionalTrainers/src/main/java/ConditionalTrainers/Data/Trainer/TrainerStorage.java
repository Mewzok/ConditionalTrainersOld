package ConditionalTrainers.Data.Trainer;

import java.util.ArrayList;
import java.util.LinkedList;

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
		
		for(int i = 0; i < instance.getTeams().size(); i++)
		{
			// Save team data
			teamList.appendTag(instance.getTeams().get(i));
		}
		
		for(int i = 0; i < instance.getScoreboard().size(); i++)
		{
			// Save scoreboard data
			NBTTagCompound n = new NBTTagCompound();
			n.setString(Integer.toString(i), instance.getScoreboard().get(i));
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
		NBTTagList scoreList = new NBTTagList();
		LinkedList<NBTTagCompound> teams = new LinkedList<NBTTagCompound>();
		ArrayList<String> scoreboards = new ArrayList<String>();
		
		teamList = comp.getTagList("Teams", Constants.NBT.TAG_COMPOUND);
		scoreList = comp.getTagList("Scoreboards", Constants.NBT.TAG_COMPOUND);
		
		for(int i = 0; i < teamList.tagCount(); i++)
		{
			// Read team data
			teams.add(teamList.getCompoundTagAt(i));
		}
		
		//debug
		System.out.println("size: " + scoreList.tagCount());
		
		for(int i = 0; i < scoreList.tagCount(); i++)
		{
			// Read scoreboard data
			NBTTagCompound n = new NBTTagCompound();
			n = scoreList.getCompoundTagAt(i);
			
			scoreboards.add(n.getString(Integer.toString(i)));
			
			//debug
			System.out.println("scro: " + scoreboards.get(i));
		}
		
		instance.setTeams(teams);
		instance.setScoreboard(scoreboards);
	}
}
