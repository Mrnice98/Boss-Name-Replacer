package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@PluginDescriptor(
	name = "Boss Name Replacer",
	description = "Replaces Boss names with funny things",
	tags = {"Boss name","Name","Its Will"},
	enabledByDefault = true
)
public class BossNamePlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BossNameConfig config;

	private final Random r = new Random();
	private final List<String> spawnPhrases = Arrays.asList("It's grumblin time!","No pet?","Prepare to be grumbled!","You are no match for The Grumbler!","Grumbler SMASH!","Who dares awaken The Grumbler!");
	private final List<String> killPhrases = Arrays.asList("Ive been grumbled!","Im gunna grumble!","F","Ive been out grumbled","My grumble sense is tingling","The Grumbler has been bested!","Back to the grumble cave i go...","Not my grumble goop!");

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{

		if (event.getMessageNode().getType() == ChatMessageType.GAMEMESSAGE)
		{
			if (event.getMessage().contains("Phantom Muspah"))
			{
				event.getMessageNode().setValue(event.getMessage().replace("Phantom Muspah","Grumbler"));
			}

			if (event.getMessage().contains("Muphin") && config.renamePet())
			{
				event.getMessageNode().setValue(event.getMessage().replace("Muphin","Lil' Grumble"));
			}
		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{

		if (event.getMenuEntry().getTarget().contains("Phantom Muspah"))
		{
			event.getMenuEntry().setTarget(event.getTarget().replace("Phantom Muspah","The Grumbler"));
		}

		if (event.getMenuEntry().getTarget().contains("Muphin") && config.renamePet())
		{
			event.getMenuEntry().setTarget(event.getMenuEntry().getTarget().replace("Muphin","Lil' Grumble"));
		}
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		Widget bossName = client.getWidget(303,9);
		Widget colName = client.getWidget(621,12);
		Widget colNameBig = client.getWidget(621,19);
		Widget colNameSmall = client.getWidget(621,19);

		if (bossName != null && bossName.getText().contains("Phantom Muspah"))
		{
			bossName.setText(bossName.getText().replace("Phantom Muspah","The Grumbler"));
		}

		if (colName != null)
		{
			colName = colName.getChild(27);
			colName.setText(colName.getText().replace("Phantom Muspah","The Grumbler"));
		}

		if (colNameBig != null && colNameSmall != null)
		{
			colNameBig = colNameBig.getChild(0);
			colNameBig.setText(colNameBig.getText().replace("Phantom Muspah","The Grumbler"));

			colNameSmall = colNameSmall.getChild(2);
			colNameSmall.setText(colNameSmall.getText().replace("Phantom Muspah","The Grumbler"));
		}

	}


	@Subscribe
	public void onAnimationChanged(AnimationChanged event)
	{
		if (event.getActor().getName() == null) { return;}

		if (event.getActor().getName().equals("Phantom Muspah") && event.getActor().getHealthRatio() == 0 && config.catchPhrases() && event.getActor().getOverheadCycle() == 0)
		{
			int rand = r.nextInt(spawnPhrases.size());
			event.getActor().setOverheadText(killPhrases.get(rand));
			event.getActor().setOverheadCycle(80);
		}

	}


	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		if (event.getNpc().getName() == null) { return;}

		if (event.getNpc().getName().equals("Phantom Muspah") && config.catchPhrases())
		{
			int rand = r.nextInt(spawnPhrases.size());
			event.getNpc().setOverheadCycle(100);
			event.getNpc().setOverheadText(spawnPhrases.get(rand));
		}
	}


	@Provides
	BossNameConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BossNameConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		log.info("BossNameReplacer Started");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("BossNameReplacer Stopped");
	}


}
