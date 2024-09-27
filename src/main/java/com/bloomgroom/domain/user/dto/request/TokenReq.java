package com.bloomgroom.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class TokenReq {

    private String accessToken;
}
