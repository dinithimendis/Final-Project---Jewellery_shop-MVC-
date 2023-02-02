package lk.ijse.jewellery.model;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CustomerDetails {
    private String cusId;
    private String empId;
    private Date date;
    private Time time;

}
