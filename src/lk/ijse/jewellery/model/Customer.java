package lk.ijse.jewellery.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Customer {
    private String cusId;
    private String title;
    private String cusName;
    private String address;
    private String telNo;
    private String province;
    private String nic;


}
