package org.felixcjy.domain.dto;

import lombok.Data;

/**
 * @author: Felix(蔡济阳)
 * @since : 2025/7/12 14:42
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
