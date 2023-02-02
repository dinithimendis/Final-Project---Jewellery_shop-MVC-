package lk.ijse.jewellery.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Order {
    private String orderId;
    private String cusId;
    private String OrderDate;
    private String OrderTime;

}
