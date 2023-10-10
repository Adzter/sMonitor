package net.runelite.plugins.landocalrissian;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.discord.DiscordService;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageCapture;
import net.runelite.client.util.ImageUploadStyle;
import net.runelite.plugins.landocalrissian.utils.DiscordWebhook;
import net.runelite.plugins.landocalrissian.utils.DiscordWebhookBody;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import okhttp3.OkHttpClient;
import java.util.regex.Matcher;
import java.util.concurrent.ScheduledExecutorService;

import static net.runelite.http.api.RuneLiteAPI.GSON;

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
    private ImageCapture imageCapture;

    @Inject
    private ScheduledExecutorService executor;

    @Inject
    ClientThread clientThread;

    @Inject
    private OkHttpClient okHttpClient;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private sMonitorOverlay overlay;

    @Inject
    private DiscordService discord;

    @Inject
    private KeyManager keyManager;

    private DiscordWebhook webhook = new DiscordWebhook(sMonitorConst.SMONITOR_WEBHOOK_URL );


    private Image tradeImage;
    private String trader;

    @Override
    protected void startUp() {
        keyManager.registerKeyListener(this);
        overlayManager.add(overlay);
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
    }

    @SneakyThrows
    @Subscribe
    public void onStatChanged(StatChanged event){
        webhook.setContent( "Level up **" + event.getSkill() + "** to **" + event.getLevel() + "**" );
        webhook.execute();
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {

        ChatMessageType chatType = event.getType();
        if (chatType == ChatMessageType.TRADE) {
            String msg = event.getMessage();
            switch (msg) {
                case sMonitorConst.ACCEPTED_TRADE_MSG:
                    saveScreenshot();
                    break;
                case sMonitorConst.DECLINED_TRADE_MSG:
                    clearScreenshot();
            }
        }
    }

    private void takeScreenshot() {
        overlay.queueForTimestamp (image -> {
            Widget nameWidget = client.getWidget(sMonitorConst.PLAYER_TRADE_CONFIRMATION_GROUP_ID, sMonitorConst.PLAYER_TRADE_CONFIRMATION_TRADING_WITH);
            trader = "unknown";
            if (nameWidget != null) {
                Matcher m = sMonitorConst.TRADING_WITH_PATTERN.matcher(nameWidget.getText());
                if (m.matches()) {
                    trader = m.group(2);
                }
            }

            tradeImage = image;
        });
    }

    private void saveScreenshot() {
        String otherRsn = trader;
        Image image = tradeImage;



        executor.submit(() -> {
            BufferedImage screenshot = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = screenshot.getGraphics();
            int gameOffsetX = 0;
            int gameOffsetY = 0;

            graphics.drawImage(image, gameOffsetX, gameOffsetY, null);
            imageCapture.takeScreenshot(screenshot, otherRsn, "sMonitor", false, ImageUploadStyle.NEITHER);

            DiscordWebhookBody discordWebhookBody = new DiscordWebhookBody();
            discordWebhookBody.setContent("Trade Success");

            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("payload_json", GSON.toJson(discordWebhookBody));

            requestBodyBuilder.addFormDataPart("file", "image.png",
                    RequestBody.create(MediaType.parse("image/png"), convertImageToByteArray(screenshot)));

            RequestBody requestBody = requestBodyBuilder.build();
            Request request = new Request.Builder()
                    .url(sMonitorConst.SMONITOR_WEBHOOK_URL)
                    .post(requestBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    log.debug("Error submitting webhook", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    response.close();
                }
            });
        });
    }

    @SneakyThrows
    private static byte[] convertImageToByteArray(BufferedImage bufferedImage)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void clearScreenshot() {
        tradeImage = null;
        trader = null;
    }

    @Subscribe
    public void onWidgetLoaded(final WidgetLoaded event) {
        int groupId = event.getGroupId();
        Runnable task = null;
        switch (groupId) {
            case sMonitorConst.PLAYER_TRADE_CONFIRMATION_GROUP_ID:
                task = this::takeScreenshot;
                break;
        }

        if (task != null) {
            clientThread.invokeLater(task);
        }
    }

    @Subscribe
    private void onGameStateChanged(GameStateChanged event){

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @SneakyThrows
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(client.getGameState().equals(GameState.LOGGED_IN) && client.getCanvas().hasFocus()) {

            if( keyEvent.getKeyChar() == 'm' ) {

                String name = discord.getCurrentUser().username;
                webhook.setContent( "Logged in as " + client.getLocalPlayer().getName());
                webhook.execute();
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
