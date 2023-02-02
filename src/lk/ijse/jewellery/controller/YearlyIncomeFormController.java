package lk.ijse.jewellery.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.model.IncomeReport;
import lk.ijse.jewellery.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class YearlyIncomeFormController implements Initializable {

    public TableView<IncomeReport> tblReport;
    public TableColumn<IncomeReport, String> colYear;
    public TableColumn<IncomeReport, String> colOrderCount;
    public TableColumn<IncomeReport, String> colItemSoldQty;
    public TableColumn<IncomeReport, String> colCost;
    public AnchorPane yearlyContext;
    ObservableList<IncomeReport> obList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colYear.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCount.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemSoldQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));

        try {
            tblReport.setItems(loadYearlyIncomeReport());
        } catch (
                SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* generating year wise income */
    private ObservableList<IncomeReport> loadYearlyIncomeReport() throws SQLException, ClassNotFoundException {

        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT extract(YEAR FROM (OrderDate)) ,sum(orderDetails.OrderQty),count(`order`.orderId),sum(orderDetails.totalAmount) FROM `order` INNER JOIN orderDetails ON `order`.orderId = orderDetails.orderId GROUP BY extract(YEAR FROM (OrderDate));").executeQuery();

        obList = FXCollections.observableArrayList();

        while (rst.next()) {
            String date = rst.getString(1);
            int countOrderId = rst.getInt(3);
            int numberOfSoldItem = rst.getInt(2);
            double sumOfTotal = rst.getDouble(4);

            obList.add(new IncomeReport(date, countOrderId, numberOfSoldItem, sumOfTotal));
        }
        return obList;
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("BarChartFormController", yearlyContext);
    }
}