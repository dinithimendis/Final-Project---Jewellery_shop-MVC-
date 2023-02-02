package lk.ijse.jewellery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.model.Order;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.util.crudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderFormController {

    public TableView<Order> OrdersTbl;
    public TableColumn<Order, String> orderIdCol;
    public TableColumn<Order, String> customerIdCol;
    public TableColumn<Order, String> DateCol;
    public TableColumn<Order, String> TimeCol;
    public TextField CustomerSearch;
    public AnchorPane OrderFormAnchorPane;
    public TextField orderIdTxt;
    public TextField dateTxt;
    public TextField customerIdTxt;
    public TextField TimeTxt;

    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        DateCol.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        TimeCol.setCellValueFactory(new PropertyValueFactory<>("OrderTime"));

        try {
            loadAllOrders();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

    }

    private void loadAllOrders() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM `order`");
        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Order(
                            result.getString("orderId"),
                            result.getString("cusId"),
                            result.getString("OrderDate"),
                            result.getString("OrderTime")
                    ));
        }
        OrdersTbl.setItems(obList);
        OrdersTbl.refresh();
    }


    public void save(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", OrderFormAnchorPane);
    }

    /* search any orders */
    public void searchOrders(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = crudUtil.execute("SELECT * FROM `order` WHERE orderId=?", CustomerSearch.getText());

        if (resultSet.next()) {
            orderIdTxt.setText(resultSet.getString(1));
            customerIdTxt.setText(resultSet.getString(2));
            dateTxt.setText(resultSet.getString(3));
            TimeTxt.setText(resultSet.getString(4));
            NotificationController.searchResultFound();
        } else {
            NotificationController.searchResultNotFound();
            loadAllOrders();
        }
    }

}
