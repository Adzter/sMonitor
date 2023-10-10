package net.runelite.plugins.landocalrissian;

import java.util.regex.Pattern;

public class sMonitorConst {

    /* Discord constants */
    public final static String SMONITOR_WEBHOOK_URL = "https://discord.com/api/webhooks/1161102986530013304/JyvggvOI51t4N7-cwCKOl_GKcP3EzgQWqnx1980oeKg2fAvylR9FAHjsakVvAhW2B1IU";


    /* Trading constants */
    public static final String ACCEPTED_TRADE_MSG = "Accepted trade.";
    public static final String DECLINED_TRADE_MSG = "Other player declined trade.";
    public static final String DECLINE_MSG = "Decline";

    public static final int PLAYER_TRADE_CONFIRMATION_GROUP_ID = 334;
    public static final int PLAYER_TRADE_CONFIRMATION_TRADING_WITH = 30;
    public static final int PLAYER_TRADE_CONFIRMATION_TRADE_MODIFIED_THEM = 31;
    public static final Pattern TRADING_WITH_PATTERN = Pattern.compile("Trading [W|w]ith:(<br>|\\s)(.*)");
}
