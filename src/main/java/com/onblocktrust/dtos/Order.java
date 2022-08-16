package com.onblocktrust.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Order {

    private String id;

    private float amount;

}
