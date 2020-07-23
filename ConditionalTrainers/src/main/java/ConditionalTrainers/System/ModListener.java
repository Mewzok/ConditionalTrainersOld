package ConditionalTrainers.System;

import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.battles.SetBattleAIEvent;
import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import com.pixelmonmod.pixelmon.api.events.PokeballImpactEvent;
import com.pixelmonmod.pixelmon.api.events.ThrowPokeballEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModListener
{
	@SubscribeEvent
	public void onBattleStarted(BattleStartedEvent e)
	{
		Utility.setAppropriateTeams(e.bc);
	}
}
