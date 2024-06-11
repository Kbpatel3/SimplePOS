package util;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class SceneManager {
    /** Static reference to the primary stage */
    private static Stage stage;

    /** Static reference to the scenes */
    private static Map<String, Scene> scenes = new HashMap<>();

    /**
     * Set the primary stage
     * @param primaryStage the primary stage
     */
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    /**
     * Add a scene to the scene map
     * @param name the name of the scene
     * @param scene the scene reference
     */
    public static void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    /**
     * Switch to a scene by name
     * @param name the name of the scene
     */
    public static void switchScene(String name) {
        if (scenes.containsKey(name)) {
            stage.setScene(scenes.get(name));
            stage.show();
        }
    }
}
