package io.moonman.emergingtechnology.helpers.custom.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.moonman.emergingtechnology.EmergingTechnology;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Useful JSON reading methods
 */
public class JsonHelper {

    public static final Gson GSON_INSTANCE = new Gson();

    public static String getPathForFile(FMLPreInitializationEvent event, String fileName) {
        return Paths
        .get(event.getModConfigurationDirectory().getAbsolutePath(), EmergingTechnology.MODID, fileName)
        .toString();
    }

    public static JsonArray readFromJson(String filePath) throws IOException {
        return read(filePath).getAsJsonArray();
    }

    public static JsonObject readFromJsonObject(String filePath) throws IOException {
        return read(filePath).getAsJsonObject();
    }

    private static JsonElement read(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = GSON_INSTANCE.fromJson(bufferedReader, JsonElement.class);
        return je;
    }
    
}