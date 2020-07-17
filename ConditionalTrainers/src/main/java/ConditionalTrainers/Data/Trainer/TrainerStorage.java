package ConditionalTrainers.Data.Trainer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < instance.getTeams().size(); i++)
		{
			list.appendTag(instance.getTeams().get(i));
		}
		comp.setTag("Teams", list);
		
		return comp;
	}
	
	@Override
	public void readNBT(Capability<ITrainer> capability, ITrainer instance, EnumFacing side, NBTBase nbt)
	{
		List<NBTTagCompound> list = new ArrayList<NBTTagCompound>();
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < instance.getTeams().size(); i++)
		{
			tagList = ((NBTTagCompound) nbt).getTagList("Teams", Constants.NBT.TAG_LIST);
			list.add(tagList.getCompoundTagAt(i));
			
			// Debug
			System.out.println(list.get(i));
		}
		
		instance.setTeams(list);
	}
}
