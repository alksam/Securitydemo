package org.example.DTO;

import lombok.*;

@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
public class TokenDTO {
    String token;
    String username;
}
