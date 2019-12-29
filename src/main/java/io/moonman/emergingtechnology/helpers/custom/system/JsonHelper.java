package io.moonman.emergingtechnology.helpers.custom.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Useful JSON reading methods
 */
public class JsonHelper {

    public static final Gson GSON_INSTANCE = new Gson();

    public static JsonArray readFromJson(String filePath) throws IOException {

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = GSON_INSTANCE.fromJson(bufferedReader, JsonElement.class);
        return je.getAsJsonArray();
    }

    public static JsonObject readFromJsonObject(String filePath) throws IOException {

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        JsonElement je = GSON_INSTANCE.fromJson(bufferedReader, JsonElement.class);
        return je.getAsJsonObject();
    }
}