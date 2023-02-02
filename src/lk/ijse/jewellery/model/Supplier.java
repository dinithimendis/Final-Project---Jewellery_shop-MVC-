package lk.ijse.jewellery.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Supplier {
    private String supId;
    private String name;
    private String nic;
    private String address;
    private String telNo;
    private String companyName;

}
