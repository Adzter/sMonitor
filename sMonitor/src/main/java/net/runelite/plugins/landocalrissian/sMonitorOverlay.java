package net.runelite.plugins.landocalrissian;

import net.runelite.api.Client;
import net.runelite.client.ui.DrawManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class sMonitorOverlay extends Overlay {
    private final sMonitorPlugin plugin;

    private final DrawManager drawManager;

    private final Queue<Consumer<Image>> consumers = new ConcurrentLinkedQueue<>();

    @Inject
    private sMonitorOverlay(DrawManager drawManager, sMonitorPlugin plugin) {
        super(plugin);

        this.drawManager = drawManager;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (consumers.isEmpty()) {
            return null;
        }

        Consumer<Image> consumer;
        while ((consumer = consumers.poll()) != null) {
            drawManager.requestNextFrameListener(consumer);
        }

        // Return the panel
        return null;
    }

    void queueForTimestamp(Consumer<Image> screenshotConsumer) {
        consumers.add(screenshotConsumer);
    }
}
