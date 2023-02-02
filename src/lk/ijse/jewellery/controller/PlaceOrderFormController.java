package lk.ijse.jewellery.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.model.Customer;
import lk.ijse.jewellery.model.Item;
import lk.ijse.jewellery.model.Order;
import lk.ijse.jewellery.model.OrderDetails;
import lk.ijse.jewellery.util.Navigation;
import lk.ijse.jewellery.util.NotificationController;
import lk.ijse.jewellery.util.crudUtil;
import lk.ijse.jewellery.view.tm.CartTM;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class PlaceOrderFormController {
    public TextField NameTxt;
    public TextField AddressTxt;
    public ComboBox<String> CustomerIDCombo;
    public TextField CityTxt;
    public TextField UnitPriceTxt;
    public TextField QtyOnHandTxt;
    public TextField DescriptionTxt;
    public TextField QtyTxt;
    public TextField DiscountTxt;
    public ComboBox<String> ItemCodeCombo;
    public Label OrderID;
    public TableView<CartTM> PlaceOrderTbl;
    public TableColumn<OrderDetails, String> OrderIDCol;
    public TableColumn<OrderDetails, String> ItemCodeCol;
    public TableColumn<OrderDetails, String> QtyCol;
    public TableColumn<OrderDetails, String> UnitPriceCol;
    public TableColumn<OrderDetails, String> DiscountCol;
    public TableColumn<OrderDetails, String> TotalCol;
    public JFXButton RemoveBtn;
    public Label TotalLbl;
    public Label DateLbl;
    public Label TimeLbl;
    public AnchorPane placeOrderAnchorPane;
    public int count;
    int cartSelectedRowForRemove = -1;
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();

    public void initialize() {

        OrderIDCol.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
        ItemCodeCol.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        QtyCol.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        UnitPriceCol.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        DiscountCol.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        TotalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));


        loadDateAndTimeForPlaceOrderForm();
        setItemCodes();
        setOrderId();
        setCustomerIds();
        //--------------------

        CustomerIDCombo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails(newValue);
                });

        ItemCodeCombo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails(newValue);
                });

        PlaceOrderTbl.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> {
                    cartSelectedRowForRemove = (int) newValue;

                });

    }


    /* setting item code's to combo box  */
    private void setItemCodes() {
        try {
            ItemCodeCombo.setItems(FXCollections.observableArrayList(ItemCRUDController.getItemCodes()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* setting customer id's to combo box  */
    private void setCustomerIds() {
        try {
            ResultSet result = crudUtil.execute("SELECT cusId FROM customer");
            ArrayList<String> ids = new ArrayList<>();
            while (result.next()) {
                ids.add(result.getString(1));
            }
            ObservableList<String> cIdObList =
                    FXCollections.observableArrayList(
                            ids);
            CustomerIDCombo.setItems(cIdObList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * if you select any kind of customer id loading their details into text fields
     */
    public void setCustomerDetails(String selectedCustomerId) {
        try {
            Customer c = CustomerCRUDController.getCustomer(selectedCustomerId);
            if (c != null) {

                NameTxt.setText(c.getCusName());
                AddressTxt.setText(c.getAddress());
                CityTxt.setText(c.getProvince());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * if you select any kind of item id loading their details into text fields
     */
    public void setItemDetails(String selectedItemCode) {
        try {
            Item i = ItemCRUDController.getItem(selectedItemCode);
            if (i != null) {
                DescriptionTxt.setText(i.getDescription());
                QtyOnHandTxt.setText(String.valueOf((i.getQty())));
                UnitPriceTxt.setText(String.valueOf(i.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * date and time thread
     */
    private void loadDateAndTimeForPlaceOrderForm() {
        //Set Date
        DateLbl.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //Set Time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            LocalTime currentTime = LocalTime.now();
            TimeLbl.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    /* set order id */
    private void setOrderId() {
        try {
            OrderID.setText(new OrderCRUDController().getOrderId());
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }


    public void playMouseEnterAnimation(MouseEvent mouseEvent) {
    }

    /* conforming orders */
    public void ConfirmOrderOnAction() {
        ArrayList<OrderDetails> details = new ArrayList();
        for (CartTM tm : tmList
        ) {
            details.add(
                    new OrderDetails(
                            tm.getOrderId(),
                            tm.getItemCode(),
                            tm.getQty(), tm.getTotal(),
                            tm.getDiscount()
                    ));
            tm.getItemCode();

        }
        Order order = new Order(
                OrderID.getText(),
                CustomerIDCombo.getValue(),
                DateLbl.getText(),
                TimeLbl.getText()
        );

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved = crudUtil.execute("INSERT INTO `order` VALUES(?,?,?,?)",
                    order.getOrderId(), order.getCusId(), order.getOrderDate(), order.getOrderTime());
            if (isOrderSaved) {
                boolean isDetailsSaved = new OrderCRUDController().saveOrderDetails(details);
                if (isDetailsSaved) {
                    connection.commit();
                    NotificationController.AddedDetailsSuccessFully();
                } else {
                    connection.rollback();
                    NotificationController.cantAddedDetailsSuccessFully();
                }
            } else {
                connection.rollback();
                NotificationController.cantAddedDetailsSuccessFully();
            }
        } catch (SQLException | ClassNotFoundException ignored) {
        } finally {

            try {
                assert connection != null;
                connection.setAutoCommit(true);
            } catch (SQLException ignored) {
            }
        }
        setOrderId();
    }

    /* if exists previous item codes  */
    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    /* calculate total */
    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : tmList
        ) {
            total += tm.getTotal();
        }
        TotalLbl.setText(String.valueOf(total));
    }

    /* removing or clearing tm */
    public void RemoveOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
        } else {
            tmList.remove(cartSelectedRowForRemove);
            if (tmList.isEmpty()) {
            }
            NotificationController.detailsRemoved();
            calculateTotal();
            PlaceOrderTbl.refresh();
        }
    }

    /* adding to the cart */
    public void AddToCartOnAction(ActionEvent actionEvent) {
        try {
            double unitPrice = Double.parseDouble(UnitPriceTxt.getText());
            double discount = Double.parseDouble(DiscountTxt.getText());
            double qty = Double.parseDouble((QtyTxt.getText()));
            double Cost = ((unitPrice * qty) * discount / 100);
            double totalCost = (unitPrice * qty) - Cost;

            CartTM isExists = isExists(ItemCodeCombo.getValue());

            if (isExists != null) {
                for (CartTM temp : tmList
                ) {
                    if (temp.equals(isExists)) {
                        temp.setTotal(temp.getTotal() + totalCost);
                        temp.setQty((temp.getQty() + qty));
                    }
                }
            } else {

                CartTM tm = new CartTM(

                        OrderID.getText(),
                        ItemCodeCombo.getValue(),
                        DescriptionTxt.getText(),
                        qty,
                        unitPrice,
                        discount,
                        totalCost
                );

                tmList.add(tm);
                PlaceOrderTbl.setItems(tmList);
            }
            PlaceOrderTbl.refresh();
            calculateTotal();
        } catch (Exception ignored) {
        }
    }

    /* clearing details */
    public void ClearOrderOnAction() {
        tmList.clear();
        PlaceOrderTbl.refresh();
        TotalLbl.setText("0.00/=");
        NameTxt.clear();
        AddressTxt.clear();
        CityTxt.clear();
        UnitPriceTxt.clear();
        QtyOnHandTxt.clear();
        DescriptionTxt.clear();
        QtyTxt.clear();
        DiscountTxt.clear();
    }

    public void discountShiftOnAction(ActionEvent actionEvent) {
    }

    public void playMouseExitedAnimation(MouseEvent mouseEvent) {
    }

    public void exitOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", placeOrderAnchorPane);
    }


    /* conform order */
    public void confirmOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ConfirmOrderOnAction();
        ClearOrderOnAction();

        try {
            count = (Integer.parseInt(QtyOnHandTxt.getText()) - Integer.parseInt(QtyTxt.getText()));
            QtyOnHandTxt.setText(Integer.toString(count));

            Item it = new Item();
            it.setQty(count);


            it.setItemCode(ItemCodeCombo.getValue());
            it.setQty(count);
            crudUtil.execute("UPDATE Item SET qty=? WHERE ItemCode = ?", it.getQty(), it.getItemCode());
        } catch (NumberFormatException ignored) {
        }
    }

    /* print bill on action */
    public void printBillOnAction(ActionEvent actionEvent) {
        ObservableList<CartTM> tableRecords = PlaceOrderTbl.getItems();

        try {

            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/reports/Prison.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
