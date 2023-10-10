package net.runelite.plugins.landocalrissian;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

public class sMonitorOverlay extends Overlay {
    private final sMonitorPlugin plugin;

    private final sMonitorConfig config;

    public PanelComponent panelComponent = new PanelComponent();

    @Inject
    public sMonitorOverlay(sMonitorPlugin plugin, sMonitorConfig config) {
        super(plugin);

        setPosition(OverlayPosition.BOTTOM_LEFT);
        setLayer(OverlayLayer.ABOVE_SCENE);

        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if(panelComponent != null) {
            // Clear the panel
            panelComponent.getChildren().clear();

            if (config.displayInfo()) {
                // Set the title of the panel
                TitleComponent title = TitleComponent.builder()
                        .text("sMonitor")
                        .color(Color.GREEN)
                        .build();
                panelComponent.getChildren().add(title);

                panelComponent.getChildren().add(LineComponent.builder()
                        .left("Active:")
                        .right("" + plugin.IsActive())
                        .rightColor(plugin.IsActive() ? Color.GREEN : Color.RED)
                        .build());

                panelComponent.setPreferredSize(new Dimension(
                        150,
                        panelComponent.getBounds().height)
                );

            }

            // Return the panel
            return panelComponent.render(graphics);
        }

        return null;
    }
}
