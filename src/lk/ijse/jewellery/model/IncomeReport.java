package lk.ijse.jewellery.model;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class IncomeReport {
    private String date;
    private int numberOfOrders;
    private int numberOfSoldItem;
    private double finalCost;

    public IncomeReport(String date, int numberOfSoldItem) {
        this.date = date;
        this.numberOfSoldItem = numberOfSoldItem;
    }

}
