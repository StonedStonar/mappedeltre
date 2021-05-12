package no.stonedstonar.deltre.postalApp.ui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import no.stonedstonar.deltre.postalApp.model.PostalFacade;
import no.stonedstonar.deltre.postalApp.model.PostalSystem;
import no.stonedstonar.deltre.postalApp.ui.controllers.Controller;


import java.io.IOException;

/**
 * A class that represents the UI backbone. This classes task is just to load the scene and set the scene.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class PostalApp extends Application {

    //Using singleton since i only need one main application window.
    private static volatile PostalApp app;

    private Stage stage;

    private PostalFacade postalFacade;

    /**
     * Makes an instance of the postal app.
     */
    public PostalApp(){
        postalFacade = new PostalFacade();
        postalFacade.addPostalSystem("Norway", 0L, 9999L, 0L, 9999L);
        app = this;
    }

    /**
     * Gets the app window so the other classes can switch the scenes.
     * @return a postal app object that loads scenes and sets scenes.
     */
    public static PostalApp getApp(){
        if(app == null){
            synchronized (PostalApp.class){
                app = new PostalApp();
            }
        }
        return app;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        PostalSystem postalSystem = postalFacade.getPostalSystemByName("Norway");
        MainWindow mainWindow = new MainWindow(postalSystem);
        setScene(mainWindow);
        try {
            Image image = new Image(getClass().getResource("/images/posticon.png").toExternalForm());
            stage.getIcons().add(image);
        }catch (NullPointerException exception){

        }
        primaryStage.show();
    }

    /**
     * Sets the scene to a new scene if the full name and controller is a valid format.
     * @param window the window you want to change the scene to.
     * @throws IOException gets thrown if the scene is not loaded correctly or is missing.
     */
    public void setScene(Window window) throws IOException {
        Scene scene = window.getScene();
        if (scene == null){
            String nameOfFile = window.getFXMLName();
            Controller controller = window.getController();
            PostalSystem.checkString(nameOfFile, "fxml file name");
            String nameOfFXML = nameOfFile + ".fxml";
            if (controller == null){
                throw new IllegalArgumentException("The controller cannot be empty or null.");
            }

            scene = loadScene(nameOfFXML, controller);
            window.setScene(scene);
        }
        String mainTitle = "Post applikasjon";
        String title = window.getTitleName();
        if ((title != null) && (!title.isEmpty())){
            mainTitle += " - " + title;
        }
        window.getController().updateContent();
        stage.setTitle(mainTitle);
        stage.setScene(scene);

    }

    /**
     * Loads a scene and returns the scene after its loaded.
     * @param fullNameOfFile the full name of the FXML file you want to load with the .fxml part.
     * @param controller the controller you want this scene to have.
     * @return a scene that is loaded and ready to be displayed.
     * @throws IOException gets thrown if the scene is not loaded correctly or is missing.
     */
    private Scene loadScene(String fullNameOfFile, Controller controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource( fullNameOfFile));
        loader.setController(controller);
        Scene newScene = new Scene(loader.load());
        return newScene;
    }

    /**
     * Closes the application.
     */
    public void exitApplication(){
        stage.close();
    }
}
