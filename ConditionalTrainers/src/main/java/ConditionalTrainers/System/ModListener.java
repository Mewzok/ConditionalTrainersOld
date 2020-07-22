package ConditionalTrainers.System;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModListener
{
	@SubscribeEvent
	public void onBattleStarted(BattleStartedEvent e)
	{
		Utility.setAppropriateTeams(e.bc);
	}
}
