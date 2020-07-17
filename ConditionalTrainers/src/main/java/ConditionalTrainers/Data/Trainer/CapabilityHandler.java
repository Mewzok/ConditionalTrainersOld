package ConditionalTrainers.Data.Trainer;

import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler
{
	public static final ResourceLocation TRAINER_CAP = new ResourceLocation("conditionaltrainers", "trainer");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		if(!(event.getObject() instanceof NPCTrainer)) return;
		
		event.addCapability(TRAINER_CAP, new TrainerProvider());
	}
}
