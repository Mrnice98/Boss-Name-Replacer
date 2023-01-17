package com.example;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("BossName")
public interface BossNameConfig extends Config {

    @ConfigItem(
            keyName = "RenamePet",
            name = "Rename Pet",
            description = "toggles the renaming of a bosses pet",
            position = 0
    )
    default boolean renamePet()
    {
        return true;
    }

    @ConfigItem(
            keyName = "CatchPhrases",
            name = "Catch Phrases",
            description = "toggles the boss saying its catch phrases",
            position = 2
    )
    default boolean catchPhrases()
    {
        return true;
    }

}
