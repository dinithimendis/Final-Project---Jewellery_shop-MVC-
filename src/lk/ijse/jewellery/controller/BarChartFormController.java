package lk.ijse.jewellery.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import lk.ijse.jewellery.db.DBConnection;
import lk.ijse.jewellery.util.Navigation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BarChartFormController {
    public AnchorPane barChartAnchorPane;
    @FXML
    private AreaChart<?, ?> barChart;

    /*- load all customer base growth -*/
    public void initialize() throws SQLException, ClassNotFoundException {

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2022");

        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement(
                "select OrderDate, Count(*) from jewelery_shop.`order` Group by OrderDate");
        ;
        ResultSet rst = stm.executeQuery();
        String prevDay = "";

        while (rst.next()) {
            String date = rst.getString(1);

            int count = rst.getInt(2);
            series1.getData().add(new XYChart.Data<>(date, count));
            prevDay = date;
        }
        barChart.getData().addAll(series1);
    }

    /*- loading daily income form -*/
    public void clickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("DailyIncomeForm", barChartAnchorPane);
    }

    /*- loading monthly income form -*/
    public void monthlyClickOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("MonthlyIncomeForm", barChartAnchorPane);
    }

    /*- loading yearly income form -*/
    public void yearlyOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("YearlyIncomeForm", barChartAnchorPane);
    }

    /* exit from bar chart form */
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.AdminORCashierUI("AdminHomeForm", barChartAnchorPane);
    }

}
