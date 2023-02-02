package lk.ijse.jewellery.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Login {
    private String loginId;
    private String username;
    private String password;

}
