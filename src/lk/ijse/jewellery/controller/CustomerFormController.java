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
import lk.ijse.jewellery.model.Customer;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.util.crudUtil;
import lk.ijse.jewellery.util.validationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerFormController {
    public JFXButton CustomerSaveButton;
    public TableView<Customer> TableContextFull;
    public Label DateLbl;
    public Label TimeLbl;
    public AnchorPane CustomerAnchorPane;
    public TextField txtCustomerID;
    public TextField txtCustomerProvince;
    public TextField txtCustomerName;
    public TextField txtTelNo;
    public TextField txtMrMrs;
    public TextField txtCustomerAddress;
    public TextField txtNic;
    public TableColumn<Customer, String> id;
    public TableColumn<Customer, String> mrMrs;
    public TableColumn<Customer, String> Address;
    public TableColumn<Customer, String> TelNo;
    public TableColumn<Customer, String> Province;
    public TableColumn<Customer, String> NIC;
    public TableColumn<Customer, String> colCustomer_Name;
    public Label customerIdLbl;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();


    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        mrMrs.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCustomer_Name.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        TelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        Province.setCellValueFactory(new PropertyValueFactory<>("province"));
        NIC.setCellValueFactory(new PropertyValueFactory<>("nic"));

        /* load all customers in to the table */
        try {
            loadAllCustomers();
        } catch (SQLException | ClassNotFoundException ignored) {
        }

        CustomerSaveButton.setOnMouseClicked(event -> {
            try {
                btnSaveOnAction();
            } catch (SQLException | ClassNotFoundException ignored) {
            }
        });


        /* regular expression validator */
        map.put(txtCustomerID, Pattern.compile("^(C00-)[0-9]{1,4}$"));
        map.put(txtMrMrs, Pattern.compile("^(mr|mrs)$"));
        map.put(txtCustomerName, Pattern.compile("^[A-z ]{3,20}$"));
        map.put(txtCustomerProvince, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtTelNo, Pattern.compile("^(07([1245678])|091)(-)[0-9]{7}$"));
        map.put(txtCustomerAddress, Pattern.compile("^[A-z]{4,15}$"));
        map.put(txtNic, Pattern.compile("^([0-9]{12}|[0-9]{12}v)$"));

    }

    /* managing all TextField's like removing and adding errors   */
    public void textFields_Key_Released_Customer(KeyEvent keyEvent) {
        validationUtil.validate(map, CustomerSaveButton);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validationUtil.validate(map, CustomerSaveButton);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
            }
        }
    }

    /* load all customer details in to the customerForm ui - > table */
    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM customer");
        ObservableList<Customer> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Customer(
                            result.getString("cusId"),
                            result.getString("title"),
                            result.getString("cusName"),
                            result.getString("address"),
                            result.getString("telNo"),
                            result.getString("province"),
                            result.getString("nic")
                    ));
        }
        TableContextFull.setItems(obList);
        TableContextFull.refresh();
    }

    /* save customer */
    public void btnSaveOnAction() throws SQLException, ClassNotFoundException {
        String id = txtCustomerID.getText();
        String title = txtMrMrs.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String telNo = txtTelNo.getText();
        String province = txtCustomerProvince.getText();
        String nic = txtNic.getText();


        Customer customer = new Customer(id, title, name, address, telNo, province, nic);
        String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            boolean isAdded = crudUtil.execute(sql,
                    customer.getCusId(),
                    customer.getTitle(),
                    customer.getCusName(),
                    customer.getAddress(),
                    customer.getTelNo(),
                    customer.getProvince(),
                    customer.getNic()
            );

            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        loadAllCustomers();
        clearText();
    }

    /* update customer */
    public void UpdateBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Customer customer = new Customer(
                txtCustomerID.getText(),
                txtMrMrs.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                txtTelNo.getText(),
                txtCustomerProvince.getText(),
                txtNic.getText()
        );

        boolean isUpdated = crudUtil.execute("UPDATE customer SET title=? , cusName=? , address=? , telNo=? , province=? , nic=? WHERE cusId=?",
                customer.getTitle(),
                customer.getCusName(),
                customer.getAddress(),
                customer.getTelNo(),
                customer.getProvince(),
                customer.getNic(),
                customer.getCusId()
        );

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Details Updated !").show();
            clearText();
            loadAllCustomers();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }

    }

    /* delete customer */
    public void DeleteBtnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        boolean isDeleted =crudUtil.execute("DELETE FROM customer WHERE cusId=?", txtCustomerID.getText());

        if (isDeleted) {
            clearText();
            loadAllCustomers();
            NotificationController.detailsRemoved();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something went wrong!").show();
        }

    }

    /* type id and search customer using clicking search image */
    public void searchOnAction(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        customerIdLbl.setDisable(false);
        ResultSet resultSet = crudUtil.execute("SELECT * FROM customer WHERE cusId=?", txtCustomerID.getText());

        if (resultSet.next()) {
            txtMrMrs.setText(resultSet.getString(2));
            txtCustomerName.setText(resultSet.getString(3));
            txtCustomerAddress.setText(resultSet.getString(4));
            txtTelNo.setText(resultSet.getString(5));
            txtCustomerProvince.setText(resultSet.getString(6));
            txtNic.setText(resultSet.getString(7));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            loadAllCustomers();
        }
    }

    /* if you want to clear the text fields please use this... */
    private void clearText() {
        txtCustomerID.clear();
        txtCustomerProvince.clear();
        txtCustomerName.clear();
        txtTelNo.clear();
        txtMrMrs.clear();
        txtCustomerAddress.clear();
        txtNic.clear();
    }

    public void save(ActionEvent actionEvent) {
    }

    /* ui management ---------------------------------------------------------------------- */
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", CustomerAnchorPane);
    }

    public void detailsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("CustomerDetailsForm", CustomerAnchorPane);
    }
    /* ui management closed --------------------------------------------------------------- */


    /* type data and press enter to shift next text field -------------------------------- */

    public void shiftToName(ActionEvent actionEvent) {
//        txtCustomerName.requestFocus();
    }

    public void shiftToProvince(ActionEvent actionEvent) {
//        txtCustomerProvince.requestFocus();
    }

    public void shiftToTelNo(ActionEvent actionEvent) {
//        txtTelNo.requestFocus();
    }

    public void shiftToAddress(ActionEvent actionEvent) {
//        txtCustomerAddress.requestFocus();
    }

    public void shiftToNic(ActionEvent actionEvent) {
//        txtNic.requestFocus();
    }

    public void shiftToSave(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        btnSaveOnAction();
    }


}
