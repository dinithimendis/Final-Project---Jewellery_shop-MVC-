package lk.ijse.jewellery.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;

import java.io.IOException;

public class HomepageFormController {

    public JFXTextField userName;
    public JFXPasswordField password;
    public AnchorPane homePageAnchorPane;
    public Label lblInvalidUsername;
    public Label lblInvalidPassword;
    public JFXButton BtnLog;

    /* login management  */
    public void loginOnAction(ActionEvent actionEvent) {

        final String name_001 = "Admin";
        final String pwd_001 = "1234";
        final String name_002 = "Cashier";
        final String pwd_002 = "1234";

        try {
            if (userName.getText().equals(name_001) && password.getText().equals(pwd_001)) {

                NotificationController.loginSuccessful();
                Navigation.SetUiCloseUnderTheAnchorpane("AdminHomeForm", homePageAnchorPane);

            } else if (userName.getText().equals(name_002) && password.getText().equals(pwd_002)) {

                NotificationController.loginSuccessful();
                Navigation.SetUiCloseUnderTheAnchorpane("EmployeeHomeForm", homePageAnchorPane);

            } else {

                NotificationController.loginUnSuccessful();

            }

        } catch (IOException ignored) {
        }

    }


    public void passwordOnKeyType(KeyEvent keyEvent) {
    }

    public void passwordOnAction(ActionEvent actionEvent) {
        BtnLog.fire();
    }

    public void userNameOnKeyType(KeyEvent keyEvent) {
    }

    public void userNameOnAction(ActionEvent actionEvent) {
        userName.requestFocus();
    }


}
