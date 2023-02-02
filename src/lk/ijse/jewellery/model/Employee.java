package lk.ijse.jewellery.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Employee {
    private String empId;
    private String name;
    private String nic;
    private double salary;
    private String telNo;
    private String address;
    private String jobRole;

}
