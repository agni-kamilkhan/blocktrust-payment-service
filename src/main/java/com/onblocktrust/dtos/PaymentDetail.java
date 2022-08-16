package com.onblocktrust.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentDetail {

    private String key;

    private String txnId;

    private String productInfo;

    private double amount;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private String successUrl;

    private String failureUrl;

    private String hash;

    private String hashSalt;

    private String hashInput;

    public void populateHashInput() {
        StringBuilder sb = new StringBuilder();
        //sb.append("sha512(");
        sb.append(this.key);
        sb.append("|");
        sb.append(this.txnId);
        sb.append("|");
        //sb.append(this.amount);
        sb.append("10.00");
        sb.append("|");
        sb.append(this.productInfo);
        sb.append("|");
        sb.append(this.firstName);
        sb.append("|");
        sb.append(this.email);
        sb.append("|");

        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append("|");

        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append("|");

        sb.append(this.hashSalt);
        //sb.append(")");
        this.hashInput = sb.toString();
    }

}
