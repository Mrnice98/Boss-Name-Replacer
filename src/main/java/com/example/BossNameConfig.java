package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("BossName")
public interface BossNameConfig extends Config
{
	@ConfigItem(
		keyName = "Name",
		name = "Options coming soon",
		description = "Config options coming soon"
	)
	default String greeting()
	{
		return "Currently replaces Phantom Muspah and its pets name to 'The Grumbler' ";
	}
}
