package lk.ijse.jewellery.controller;

import lk.ijse.jewellery.model.Customer;
import lk.ijse.jewellery.util.crudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerCRUDController {

    public static ArrayList<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT cusId FROM customer");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()) {
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Customer getCustomer(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM customer WHERE cusId=?", id);
        if (result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7)
            );
        }
        return null;
    }

}
