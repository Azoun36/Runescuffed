package zynix.runescuffed.UI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.Path;

public class RadialConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("radialmenu.json");

    public static String hotkey = "key.keyboard.r";

    public static void load() {
        File file = CONFIG_PATH.toFile();
        if (!file.exists()) {
            save();
            return;
        }
        try (Reader reader = new FileReader(file)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json.has("hotkey")) {
                hotkey = json.get("hotkey").getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            JsonObject json = new JsonObject();
            json.addProperty("hotkey", hotkey);
            GSON.toJson(json, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}