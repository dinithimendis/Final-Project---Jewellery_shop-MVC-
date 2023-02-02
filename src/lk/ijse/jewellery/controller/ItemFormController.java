package lk.ijse.jewellery.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.model.Item;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.util.crudUtil;
import lk.ijse.jewellery.util.validationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemFormController {
    public AnchorPane ManagerItemDetailContext;
    public JFXButton SaveButton;
    public TableView<Item> FullTable;
    public TableColumn<Item, String> ItemTableCol;
    public TableColumn<Item, String> DescriptionCol;
    public TableColumn<Item, String> QtyCol;
    public TableColumn<Item, String> unitPriceCol;
    public TableColumn<Item, String> categoryCol;
    public TableColumn<Item, String> typeCol;
    public Label dateLabel;
    public Label TimeLabel;
    public TextField txtCategory;
    public TextField txtUnitPrice;
    public TextField txtDescription;
    public TextField txtQty;
    public TextField txtType;
    public TextField txtItemCode;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {
        ItemTableCol.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        unitPriceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        try {
            loadAllItems();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        SaveButton.setOnMouseClicked(event -> {
            try {
                saveOnAction();
            } catch (SQLException | ClassNotFoundException ignored) {
            }
        });

        map.put(txtItemCode, Pattern.compile("^(I00-)[0-9]{1,4}$"));
        map.put(txtUnitPrice, Pattern.compile("^([0-9]{2,6}.[0-9]{1,2})$"));
        map.put(txtDescription, Pattern.compile("^[A-z]{3,20}$"));
        map.put(txtType, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtCategory, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtQty, Pattern.compile("^[1-9 ]{1,20}$"));
    }

    /* save item */
    public void saveOnAction() throws SQLException, ClassNotFoundException {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String category = txtCategory.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String type = txtType.getText();

        Item item = new Item(itemCode, description, category, qty, unitPrice, type);

        System.out.println(item);

        try {
            String sql = "INSERT INTO item VALUES (?, ?, ?, ?, ?, ?)";

            boolean ifAdded = crudUtil.execute(sql,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getCategory(),
                    item.getQty(),
                    item.getUnitPrice(),
                    item.getType()
            );

            System.out.println(ifAdded);

            if (ifAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        }
        loadAllItems();
    }

    /* load all items */
    private void loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =  crudUtil.execute("SELECT * FROM item");
        ObservableList<Item> obList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            obList.add(
                    new Item(
                            resultSet.getString("itemCode"),
                            resultSet.getString("description"),
                            resultSet.getString("category"),
                            Integer.parseInt(resultSet.getString("qty")),
                            Double.parseDouble(resultSet.getString("unitPrice")),
                            resultSet.getString("type")
                    ));
        }
        FullTable.setItems(obList);
        FullTable.refresh();
    }

    /* update item */
    public void UpdateButtonOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtCategory.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtUnitPrice.getText()),
                txtType.getText()
        );

        boolean isUpdated = crudUtil.execute("UPDATE item SET description=? , category=? , qty=? , unitPrice=? , type=? WHERE itemCode=?",
                item.getDescription(),
                item.getCategory(),
                item.getQty(),
                item.getUnitPrice(),
                item.getType(),
                item.getItemCode()
        );

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Item Details Updated !").show();
            clearTextField();
            loadAllItems();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }
    }

    /* delete item */
    public void DeleteBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isDeleted = crudUtil.execute("DELETE FROM item WHERE itemCode=?", txtItemCode.getText());

        if (isDeleted) {
            NotificationController.detailsRemoved();
            loadAllItems();
            clearTextField();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }
    }

    /* clearing text fields */
    public void clearTextField() {
        txtCategory.clear();
        txtUnitPrice.clear();
        txtDescription.clear();
        txtQty.clear();
        txtType.clear();
        txtItemCode.clear();
    }

    /* search items */
    public void searchOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM item WHERE itemCode=?", txtItemCode.getText());

        if (result.next()) {
            txtDescription.setText(result.getString(2));
            txtCategory.setText(result.getString(3));
            txtQty.setText(result.getString(4));
            txtUnitPrice.setText(result.getString(5));
            txtType.setText(result.getString(6));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            loadAllItems();
        }
    }

    /* exit from item form */
    public void ExitBtnOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("AdminHomeForm", ManagerItemDetailContext);
    }

    /* adding and removing text field effects */
    public void textFields_Key_Released_Item(KeyEvent keyEvent) {
        validationUtil.validate(map, SaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, SaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    public void save(ActionEvent actionEvent) {
    }


    //    requestFocus options -----------------------------------------------------------------------------------------
    public void shiftToQty(ActionEvent actionEvent) {
        txtQty.requestFocus();
    }

    public void shiftToDescription(ActionEvent actionEvent) {
        txtDescription.requestFocus();
    }

    public void shiftToType(ActionEvent actionEvent) {
        txtCategory.requestFocus();
    }

    public void shiftToSaveBtn(ActionEvent actionEvent) {
        SaveButton.requestFocus();
    }

    public void shiftToCategory(ActionEvent actionEvent) {
        txtCategory.requestFocus();
    }

    public void shiftToUnitPrice(ActionEvent actionEvent) {
        txtUnitPrice.requestFocus();
    }

    //------------------------------------------------------------------------------------------------------------------
}
