package lk.ijse.jewellery.controller;

import lk.ijse.jewellery.model.OrderDetails;
import lk.ijse.jewellery.util.crudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCRUDController {
    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det : details
        ) {

            boolean isDetailsSaved = crudUtil.execute("INSERT INTO orderDetails VALUES(?,?,?,?,?)",
                    det.getOrderId(), det.getItemCode(), det.getOrderQty(), det.getDiscount(), det.getTotalAmount());
            if (isDetailsSaved) {

                if (!(boolean)crudUtil.execute("UPDATE ITEM SET itemCode=itemCode-? WHERE itemCode = ?", det.getItemCode(), (int) det.getOrderQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = crudUtil.execute("SELECT orderId FROM  `order` ORDER BY orderId DESC LIMIT 1");
        if (set.next()) {
            int tempId = Integer.parseInt(set.getString(1).split("-")[1]);

            tempId = tempId + 1;
            if (tempId <= 9) {
                return "O-00" + tempId;
            } else if (tempId <= 99) {
                return "O-0" + tempId;
            } else {
                return "O-" + tempId;
            }
        } else {
            return "O-001";
        }

    }
}
