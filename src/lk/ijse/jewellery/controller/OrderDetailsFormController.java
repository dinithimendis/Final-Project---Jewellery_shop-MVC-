package lk.ijse.jewellery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.model.OrderDetails;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.util.crudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailsFormController {
    public TextField SearchOrderID;
    public TableColumn<OrderDetails, String> orderIdCol;
    public TableColumn<OrderDetails, String> ItemCodeCol;
    public TableColumn<OrderDetails, String> QtyCol;
    public TableColumn<OrderDetails, String> DiscountCol;
    public TableColumn<OrderDetails, String> PriceCol;
    public AnchorPane OrderDetailsFormAnchorPane;
    public TableView<OrderDetails> ODetailFormTbl;
    public TextField itemCodeTxt;
    public TextField qtyTxt;
    public TextField discountTxt;
    public TextField priceTxt;


    public void initialize() {
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("OrderQty"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));

        try {
            loadAllOrderDetails();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

    }

    /* loading all order details */
    private void loadAllOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM orderDetails");
        ObservableList<OrderDetails> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new OrderDetails(
                            result.getString("orderId"),
                            result.getString("itemCode"),
                            result.getDouble("OrderQty"),
                            result.getDouble("totalAmount"),
                            result.getDouble("discount")
                    ));
        }
        ODetailFormTbl.setItems(obList);
        ODetailFormTbl.refresh();
    }

    /* searching order details  */
    public void search(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = crudUtil.execute("SELECT * FROM orderDetails WHERE orderId=?", SearchOrderID.getText());

        if (resultSet.next()) {
            itemCodeTxt.setText(resultSet.getString(2));
            qtyTxt.setText(resultSet.getString(3));
            priceTxt.setText(resultSet.getString(4));
            discountTxt.setText(resultSet.getString(5));

            NotificationController.searchResultFound();
        } else {
            NotificationController.searchResultNotFound();
            loadAllOrderDetails();
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", OrderDetailsFormAnchorPane);
    }
}
