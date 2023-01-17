package com.example;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

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

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{

		if (event.getMessageNode().getType() == ChatMessageType.GAMEMESSAGE)
		{
			if (event.getMessage().contains("Phantom Muspah"))
			{
				event.getMessageNode().setValue(event.getMessage().replace("Phantom Muspah","Grumbler"));
			}

			if (event.getMessage().contains("Muphin"))
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

		if (event.getMenuEntry().getTarget().contains("Muphin"))
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

	@Provides
	BossNameConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BossNameConfig.class);
	}
}
