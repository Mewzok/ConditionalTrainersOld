package ConditionalTrainers.Data.Trainer;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TrainerProvider implements ICapabilitySerializable<NBTBase>
{
	@CapabilityInject(ITrainer.class)
	public static final Capability<ITrainer> TRAINER_CAP = null;
	
	private ITrainer instance = TRAINER_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == TRAINER_CAP;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		return capability == TRAINER_CAP ? TRAINER_CAP.<T> cast(this.instance) : null;
	}
	
	@Override
	public NBTBase serializeNBT()
	{
		return TRAINER_CAP.getStorage().writeNBT(TRAINER_CAP, this.instance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		TRAINER_CAP.getStorage().readNBT(TRAINER_CAP, this.instance, null, nbt);
	}
}
