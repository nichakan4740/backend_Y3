package sit.int221.oasip.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.oasip.enums.Roles;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDTO {
    private String name;
    private String email;
    private Roles role;
}
