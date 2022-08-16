package com.onblocktrust.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RazorPayOrder {

    String id;

    String currency;

    float amount;
}
