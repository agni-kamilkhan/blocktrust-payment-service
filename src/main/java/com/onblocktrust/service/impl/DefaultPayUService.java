package com.onblocktrust.service.impl;

import com.onblocktrust.dtos.PaymentDetail;
import com.onblocktrust.service.PayUService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@ApplicationScoped
public class DefaultPayUService implements PayUService {

    @ConfigProperty(name = "app.payu.merchant-key")
    String merchantKey;

    @ConfigProperty(name = "app.payu.merchant-salt1")
    String merchantSalt1;

    @ConfigProperty(name = "app.payu.merchant-salt2")
    String merchantSalt2;

    @Override
    public PaymentDetail getPaymentDetail() {
        PaymentDetail.PaymentDetailBuilder builder = PaymentDetail.builder();
        builder.key(this.merchantKey);
//        builder.txnId(UUID.randomUUID().toString());
        builder.txnId("OphJ0UmQzCmWq8");
        builder.productInfo("iPhone");
        builder.amount(10);
        builder.email("test@gmail.com");
        builder.phone("9876543210");
        builder.firstName("PayU User");
//        builder.lastName("Last Name");
        builder.successUrl("http://localhost:9003/success");
        builder.failureUrl("http://localhost:9003/failure");
        builder.hashSalt(this.merchantSalt2);

//        builder.key("gtKFFx");
//        builder.txnId("FqbVty6si9PsS8");
//        builder.productInfo("iPhone");
//        builder.amount(10.00);
//        builder.email("test@gmail.com");
//        builder.phone("9876543210");
//        builder.firstName("PayU User");
//        builder.lastName("Name");
//        builder.successUrl("http://localhost:9003/success");
//        builder.failureUrl("http://localhost:9003/failure");
//        builder.hashSalt("eCwWELxi");

        PaymentDetail paymentDetail = builder.build();
        paymentDetail.populateHashInput();
        paymentDetail.setHash(hashIt(paymentDetail.getHashInput()));
        return paymentDetail;
    }

    private static String hashIt(String input) {
        byte[] hashseq = input.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            //throw new RuntimeException(e);
        }
        return hexString.toString();
    }

    private static String hashItY(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] mdbytes = md.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            System.out.println("Hex format : " + sb.toString());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
            System.out.println("Hex format : " + hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String hashItX(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
