package ConditionalTrainers.Data.Trainer;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TrainerStorage implements IStorage<ITrainer>
{
	@Override
	public NBTBase writeNBT(Capability<ITrainer> capability, ITrainer instance, EnumFacing sde)
	{
		NBTTagCompound comp = new NBTTagCompound();
		
		comp.setTag("Teams", instance.getTeams());
		comp.setTag("Scoreboards", instance.getScoreboard());
		comp.setTag("TeamData", instance.getTeamData());
		
		return comp;
	}
	
	@Override
	public void readNBT(Capability<ITrainer> capability, ITrainer instance, EnumFacing side, NBTBase nbt)
	{
		NBTTagCompound comp = (NBTTagCompound) nbt;
		
		instance.setTeams(comp.getCompoundTag("Teams"));
		instance.setScoreboard(comp.getCompoundTag("Scoreboards"));
		instance.setTeamData(instance.getTeams(), instance.getScoreboard());
	}
}
