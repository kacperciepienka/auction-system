package pl.auction_system.dto;

import lombok.Data;
import pl.auction_system.model.AccType;

@Data
public class UserAdminDto {
    private Long id;
    private String username;
    private String email;
    private String name;
    private AccType accType;
}
