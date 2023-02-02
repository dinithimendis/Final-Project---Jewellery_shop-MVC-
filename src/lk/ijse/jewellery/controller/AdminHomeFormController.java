package lk.ijse.jewellery.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.jewellery.util.Navigation;

import java.io.IOException;

public class AdminHomeFormController {

    public AnchorPane adminSideAnchorPane;

    /* go to employee form */
    public void employeeBtnOnAction(ActionEvent actionEvent) throws IOException, NullPointerException {
        Navigation.AdminORCashierUI("EmployeeForm", adminSideAnchorPane);
    }

    /* goto item form */
    public void itemBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("ItemForm", adminSideAnchorPane);
    }

    /* go to supplier management */
    public void supplierBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("SupplierForm", adminSideAnchorPane);
    }

    /* exit from admin form */
    public void exitBtnOnAction(ActionEvent actionEvent) throws IOException, NullPointerException {
        Navigation.AdminORCashierUI("HomePageForm", adminSideAnchorPane);
    }

    /* view reports */
    public void reportsBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("BarChartFormController", adminSideAnchorPane);
    }

    /* applying animations */
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button icon = (Button) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.AQUAMARINE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }

        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    /* removing animations */
    public void playMouseExitedAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button btn = (Button) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(180), btn);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            btn.setEffect(null);
        }

        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }
}
