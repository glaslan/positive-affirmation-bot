package com.glaslan.affirmationBot;

import com.glaslan.affirmationBot.listeners.CommandListener;
import com.glaslan.affirmationBot.listeners.MessageListener;
import kotlin.io.encoding.Base64;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Bot {

    private final ShardManager shardManager;

    public Bot() throws LoginException {
        String token = System.getenv("AFFIRMATIONERTOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.watching("For Positive Vibes"));
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        builder.addEventListeners(new MessageListener());
        shardManager = builder.build();
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
        } catch (LoginException e) {
            System.out.println("Login Error: Provided bot token is invalid");
        }
    }
}
