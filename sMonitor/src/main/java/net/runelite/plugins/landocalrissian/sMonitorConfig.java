package net.runelite.plugins.landocalrissian;

import net.runelite.client.config.*;
import net.runelite.http.api.worlds.WorldRegion;

import java.awt.event.KeyEvent;

@ConfigGroup("DZAgilityConfig")
public interface sMonitorConfig extends Config {
    @ConfigSection(
            name = "Agility Helper Config",
            description = "",
            position = 0
    )
    String agilityTitle = "Agility Helper Config";

    @ConfigItem(
            keyName = "displayInfo",
            name = "Display Info Panel",
            position = 0,
            description = "Enable to display info regarding the Agility Helper",
            section = agilityTitle
    )
    default boolean displayInfo() { return true; }

    @ConfigItem(
            keyName = "toggleKey",
            name = "Toggle Key",
            description = "Toggles the Agility Helper on / off",
            position = 2,
            section = agilityTitle
    )
    default Keybind toggleKey()
    {
        return new Keybind(KeyEvent.VK_NUMPAD1, 0);
    }

    @ConfigItem(
            keyName = "timeout",
            name = "Agility Timeout",
            description = "How long to run the agility helper before timing out automatically",
            position = 3,
            section = agilityTitle
    )
    @Range(min = 5, max = 500)
    default int timeout(){ return 10; }

    @ConfigItem(
            keyName = "pickupMarks",
            name = "Pickup Marks",
            description = "Determine if the helper should automatically pickup marks",
            position = 4,
            section = agilityTitle
    )
    default boolean pickupMarks()
    {
        return true;
    }

    @ConfigItem(
            keyName = "leaguesGP",
            name = "League GP Support",
            description = "Determines if it will also pick up GP for leagues",
            position = 5,
            section = agilityTitle
    )
    default boolean leaguesGP()
    {
        return false;
    }

    @ConfigItem(
            keyName = "delayedPickup",
            name = "Ardy - Delay Pickup Marks",
            description = "Determine if the helper should wait until the last five minutes before picking up marks",
            position = 6,
            section = agilityTitle
    )
    default boolean delayedPickup()
    {
        return false;
    }




    @ConfigSection(
            name = "World Hop Config",
            description = "",
            position = 1,
            closedByDefault = true
    )
    String worldHopTitle = "World Hop Config";

    @ConfigItem(
            keyName = "hopWorlds",
            name = "Auto World Hop",
            description = "Determine if the helper should automatically hop worlds",
            position = 0,
            section = worldHopTitle
    )
    default boolean hopWorlds()
    {
        return false;
    }

    @ConfigItem(
            keyName = "worldRegion",
            name = "World Region",
            description = "Determine what region to hop to",
            position = 1,
            section = worldHopTitle
    )
    default WorldRegion worldRegion()
    {
        return WorldRegion.UNITED_KINGDOM;
    }

    @ConfigItem(
            keyName = "hopMinDelay",
            name = "Hop Min Delay",
            description = "The minimum hop frequency delay represented in minutes",
            position = 2,
            section = worldHopTitle
    )
    @Units(Units.MINUTES)
    @Range(min = 10, max = 60)
    default int hopMinDelay(){ return 15; }

    @ConfigItem(
            keyName = "hopMaxDelay",
            name = "Hop Max Delay",
            description = "The maximum hop frequency delay represented in minutes",
            position = 3,
            section = worldHopTitle
    )
    @Units(Units.MINUTES)
    @Range(min = 15, max = 75)
    default int hopMaxDelay(){ return 25; }



    @ConfigSection(
            name = "Additional Config",
            description = "",
            position = 2,
            closedByDefault = true
    )
    String agilMoreTitle = "Additional Config";

    @ConfigItem(
            keyName = "skillBreaks",
            name = "Skilling Breaks",
            description = "Stops the bot from running randomly for a period of time before resuming again",
            position = 0,
            section = agilMoreTitle
    )
    default boolean skillBreaks(){ return true; }

    @ConfigItem(
            keyName = "restoreStamina",
            name = "Restore Stamina",
            description = "Determine if the helper should automatically restore stamina",
            position = 1,
            section = agilMoreTitle
    )
    default boolean restoreStamina()
    {
        return false;
    }

    @ConfigItem(
            keyName = "restoreStaminaThreshold",
            name = "Stamina Threshold",
            description = "When the Stamina threshold is reached, it will restore stamina.",
            position = 2,
            section = agilMoreTitle
    )
    @Range(min = 40, max = 75)
    default int restoreStaminaThreshold(){ return 60; }

    @ConfigItem(
            keyName = "autoHeal",
            name = "Auto Eat",
            description = "Determine if the helper should automatically heal the player",
            position = 3,
            section = agilMoreTitle
    )
    default boolean autoHeal()
    {
        return false;
    }

    @ConfigItem(
            keyName = "foodID",
            name = "Eat - Food IDs",
            description = "Enter the ID of the food to heal with",
            position = 4,
            section = agilMoreTitle
    )
    default String foodID()
    {
        return "";
    }

    @ConfigItem(
            keyName = "hpTheshold",
            name = "Eat - HP Threshold",
            description = "When the HP threshold is reached, it will eat food.",
            position = 5,
            section = agilMoreTitle
    )
    @Range(min = 5, max = 80)
    default int hpThreshold(){ return 20; }

    @ConfigItem(
            keyName = "seersTP",
            name = "Enable Seers TP",
            description = "Will automatically use the seers teleport at the end of the course",
            position = 6,
            section = agilMoreTitle
    )
    default boolean seersTP()
    {
        return false;
    }

    @ConfigItem(
            keyName = "magicImbue",
            name = "Cast Magic Imbue",
            description = "Will automatically cast magic imbue during the agility course",
            position = 7,
            section = agilMoreTitle
    )
    default boolean magicImbue()
    {
        return false;
    }

    @ConfigItem(
		    keyName = "alch",
		    name = "Cast Alchemy",
		    description = "Will automatically alch during the agility course",
		    position = 8,
            section = agilMoreTitle
	)
	default boolean alch()
	{
		return false;
	}

	@ConfigItem(
		    keyName = "alchItems",
		    name = "Alch - Alchemy Items",
		    description = "List of items to alch",
		    position = 9,
            section = agilMoreTitle
	)
	default String alchItems()
	{
		return "";
	}

    @ConfigItem(
            keyName = "summerPie",
            name = "Auto Summer Pie",
            description = "Will automatically use summer pies within your inventory",
            position = 10,
            section = agilMoreTitle
    )
    default boolean summerPie()
    {
        return false;
    }

    @ConfigItem(
            keyName = "specSummerPie",
            name = "Summer Pie Maintain Level",
            description = "Set the level you wish to maintain when using summer pies to boost",
            position = 11,
            section = agilMoreTitle
    )
    default boolean specificSummerPie()
    {
        return false;
    }

    @ConfigItem(
            keyName = "sumPieLevel",
            name = "Summer Pie Level Threshold",
            description = "When your agility level is lower or equal to the summer pie level, it'll consume a summer pie",
            position = 12,
            section = agilMoreTitle
    )
    @Range(min = 1, max = 95)
    default int sumPieLevel(){ return 1; }

    @ConfigItem(
            keyName = "avoidPortals",
            name = "Prif - Avoid Portals",
            description = "Determine if the helper should avoid using portals at all",
            position = 13,
            section = agilityTitle
    )
    default boolean avoidPortals()
    {
        return false;
    }
}
