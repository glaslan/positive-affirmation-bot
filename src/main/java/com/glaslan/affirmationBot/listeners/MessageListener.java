package com.glaslan.affirmationBot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    private String[] affirmations = {
            "I'm sure you are doing your best",                     // 0
            "you can get through your difficulties",
            "you are enough",
            "you deserve some you time",
            "you deserve love and care",
            "your peace matters",                                   // 5
            "be open to opportunities",
            "you are strong"
    };

    private int lastAffirmation;

    public String getAffirmation(int num) {
        return affirmations[num];
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();
        if (author.isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.contains("affirmationer") && content.contains("please"))
        {
            MessageChannel channel = event.getChannel();
            int randomNum;
            do {
               randomNum = (int) (Math.random() * affirmations.length);
            } while (randomNum == lastAffirmation);
            lastAffirmation = randomNum;
            channel.sendMessage(author.getName() + ", " + getAffirmation(randomNum)).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
