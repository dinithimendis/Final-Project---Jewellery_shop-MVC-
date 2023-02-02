package lk.ijse.jewellery.controller;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lk.ijse.jewellery.util.Navigation;

import java.io.IOException;

public class EmployeeHomeFormController {


    public AnchorPane EmployeeHomeFormAnchorPane;

    /* click to drop the order */
    public void placeOrderBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("PlaceOrder", EmployeeHomeFormAnchorPane);
    }

    /* exit from employee form */
    public void exitFromEmployeeBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("HomePageForm", EmployeeHomeFormAnchorPane);
    }

    /* go to customer form */
    public void customerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("CustomerForm", EmployeeHomeFormAnchorPane);
    }

    /* view orders */
    public void orderBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("OrderForm", EmployeeHomeFormAnchorPane);
    }

    /* applying animations */
    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof Button) {
            Button btn1 = (Button) mouseEvent.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), btn1);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            btn1.setEffect(glow);
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
    }

    /* view reports  */
    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("dailyIncomeToCashier", EmployeeHomeFormAnchorPane);
    }

    /* view order details */
    public void orderDetailsBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("OrderDetailsForm", EmployeeHomeFormAnchorPane);
    }
}
