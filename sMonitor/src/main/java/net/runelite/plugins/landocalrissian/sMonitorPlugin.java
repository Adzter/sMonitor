package net.runelite.plugins.landocalrissian;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.WorldService;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ColorUtil;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.concurrent.ScheduledExecutorService;

@PluginDescriptor(
        name = "sMonitor",
        description = "Logging of service activities",
        tags = {"logger", "admin", "lando calrissian"},
        enabledByDefault = true
)
@Slf4j
public class sMonitorPlugin extends Plugin implements KeyListener {
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private sMonitorOverlay overlay;

    @Inject
    private sMonitorConfig config;

    // Provides our config
    @Provides
    sMonitorConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(sMonitorConfig.class);
    }

    @Inject
    private KeyManager keyManager;


    @Inject
    public WorldService worldService;

    private Instant worldHopDelay = null;
    private Instant changeWorldDelay = null;
    private int worldHopTime = 2;

    private ScheduledExecutorService executorService;
    private boolean ServiceRunning = false;
    private Instant agilityTimeout = null;
    private int skillingBreakDelay = -1;
    public String header = "";

    private final int[] SUMMER_PIES_ID = new int[]{7218,7220};
    private final WorldPoint SEERS_TELEPORT_POINT = new WorldPoint(2705, 3463, 0);

    private int lastAgilityXP = 0;

    private boolean stamPotsRemaining = false;
    private boolean AgilityHelperActive = false;
    private boolean CanAdvanceStep = false;
    private boolean isAlching = false;
    private boolean delaySummerPie = false;
    private int skipTicks = -1;
    private int skipXTicks = 0;

    @Override
    protected void startUp() {
        keyManager.registerKeyListener(this);
        overlayManager.add(overlay);
        header = ColorUtil.wrapWithColorTag("| ", Color.yellow) + ColorUtil.wrapWithColorTag("Agility Helper", Color.green) + ColorUtil.wrapWithColorTag(" | ", Color.yellow);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        keyManager.unregisterKeyListener(this);
    }

    @Subscribe
    private void onGameTick(GameTick event)
    {
        if(client.getLocalPlayer() == null) {

        }
        log.info("running");
    }

    @Subscribe
    public void onWidgetLoaded(WidgetLoaded event){

    }

    @Subscribe
    public void onStatChanged(StatChanged event){

    }

    @Subscribe
    private void onGameStateChanged(GameStateChanged event){

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(client.getGameState().equals(GameState.LOGGED_IN) && client.getCanvas().hasFocus() && executorService != null) {
            if (config.toggleKey().matches(keyEvent)) {
                if (!AgilityHelperActive) {
                    log.info("start logging");
                } else {
                    log.info("end logging");
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public boolean IsActive()
    {
        return true;
    }
}
