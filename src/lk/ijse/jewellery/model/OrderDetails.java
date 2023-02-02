package lk.ijse.jewellery.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class OrderDetails {
    private String orderId;
    private String itemCode;
    private double OrderQty;
    private double totalAmount;
    private double discount;

}
