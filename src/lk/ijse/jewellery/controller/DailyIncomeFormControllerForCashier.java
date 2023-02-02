package lk.ijse.jewellery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DailyIncomeFormControllerForCashier implements Initializable {
    public TableView<IncomeReport> tblReport;
    public TableColumn<IncomeReport, String> colDate;
    public TableColumn<IncomeReport, String> colOrderCost;
    public TableColumn<IncomeReport, String> colItemQty;
    public TableColumn<IncomeReport, String> colCost;
    public AnchorPane dailyIncomeReportContext;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderCost.setCellValueFactory(new PropertyValueFactory<>("numberOfOrders"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("numberOfSoldItem"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("finalCost"));

        try {

            tblReport.setItems(loadDailyIncomeReport());
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            pieChartData.add(new PieChart.Data("Iphone 5S", 13));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * load all reports details
     */
    private ObservableList<IncomeReport> loadDailyIncomeReport() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT `order`.OrderDate,count(`order`.orderId),sum(orderDetails.totalAmount) FROM `order` INNER JOIN orderDetails ON `order`.orderId = orderDetails.orderId GROUP BY OrderDate").executeQuery();
        ObservableList<IncomeReport> obList = FXCollections.observableArrayList();
        ArrayList<IncomeReport> data = getCountItems();

        int i = 0;
        while (resultSet.next()) {

            String date = resultSet.getString(1);
            int countOrderId = resultSet.getInt(2);
            double sumOfTotal = resultSet.getDouble(3);

            obList.add(new IncomeReport(date, countOrderId, data.get(i).getNumberOfSoldItem(), sumOfTotal));
            i++;
        }
        return obList;
    }

    /**
     * calculate item count
     */
    private ArrayList<IncomeReport> getCountItems() throws SQLException, ClassNotFoundException {
        ResultSet rest = DBConnection.getInstance().getConnection().prepareStatement("SELECT DISTINCT(`order`.OrderDate),sum(orderDetails.OrderQty) FROM `Order` INNER JOIN orderDetails ON  `order`.orderId = orderDetails.orderId GROUP BY `order`.OrderDate").executeQuery();
        ArrayList<IncomeReport> temp = new ArrayList<>();

        while (rest.next()) {

            temp.add(new IncomeReport(rest.getString(1), rest.getInt(2)));
        }

        return temp;
    }

    public void backToOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("EmployeeHomeForm", dailyIncomeReportContext);
    }
}
