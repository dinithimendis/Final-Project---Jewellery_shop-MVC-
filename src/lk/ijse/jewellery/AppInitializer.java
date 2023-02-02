package lk.ijse.jewellery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(
                new Scene(FXMLLoader.load(
                        Objects.requireNonNull(
                                getClass().getResource(
                                        "/lk/ijse/jewellery/view/loadingEffect.fxml")))));
        primaryStage.setTitle("Jewellery Shop");
        primaryStage.show();

    }
}