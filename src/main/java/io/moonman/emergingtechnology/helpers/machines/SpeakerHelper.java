package io.moonman.emergingtechnology.helpers.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import scala.actors.threadpool.Arrays;

public class SpeakerHelper {

    public static void handleCommand(String speakerName, int commandId) {

        String message = "Sorry, I didn't understand that.";

        switch (commandId) {
            case 1:
                message = "Hi there!";
                break;
            default:
                break;
        }

        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(
                new TextComponentString(message + " (" + commandId + ")"));
    }

    public static int getCommandIdFromMessage(String message) {
        int commandId = 0;

        message = message.replaceAll("[^A-za-z ]", "");

        for (String greeting : getGreetingStrings()) {
            if (messageEqualsWatchword(message, greeting)) {
                commandId = 1;
            }
        }

        return commandId;
    }

    private static List<String> getGreetingStrings() {
        ArrayList<String> greetings = new ArrayList<String>();

        greetings.add("hello");
        greetings.add("hi");
        greetings.add("hey");
        greetings.add("yo");
        greetings.add("sup");
        greetings.add("whassup");
        greetings.add("good day");
        greetings.add("good morning");
        greetings.add("good evening");

        return greetings;
    }

    private static boolean messageEqualsWatchword(String message, String watchword) {
        return message.replace(" ","").equalsIgnoreCase(watchword.replace(" ",""));
    }

    private static boolean messageContainsWatchword(String message, String watchword) {
        return message.toLowerCase().contains(watchword.toLowerCase());
    }
}