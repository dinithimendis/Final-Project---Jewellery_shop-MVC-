package lk.ijse.jewellery.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.util.Navigation;

import java.io.IOException;

public class WelcomeFormController {

    public AnchorPane welcomeAnchorPane;

    public void continueOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("HomePageForm", welcomeAnchorPane);
    }
}
