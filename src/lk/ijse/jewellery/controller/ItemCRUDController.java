package lk.ijse.jewellery.controller;

import lk.ijse.jewellery.model.Item;
import lk.ijse.jewellery.util.crudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCRUDController {

    public static ArrayList<String> getItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT itemCode FROM item");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()) {
            codeList.add(result.getString(1));
        }
        return codeList;
    }

    public static Item getItem(String code) throws SQLException, ClassNotFoundException {
        ResultSet result = crudUtil.execute("SELECT * FROM item WHERE itemCode=?", code);
        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getDouble(5),
                    result.getString(6)
            );
        }
        return null;
    }

}
