package com.onblocktrust.controllers;

import com.onblocktrust.dtos.PaymentDetail;
import com.onblocktrust.dtos.RazorPayOrder;
import com.onblocktrust.service.PayUService;
import com.onblocktrust.service.RazorPayService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.Map;

/**
 * @author Kamil Khan
 */

@Slf4j
@Path("/api/razorpay")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
public class RazorPayController {

    @Inject
    RazorPayService razorPayService;

    @GET
    @Path("current-order")
    public RazorPayOrder getCurrentOrder() throws IOException {
        return razorPayService.getCurrentOrder();
    }

//    @GET
//    @Path("callback")
//    public Map<String, String> callback(@QueryParam("razorpay_payment_id") String paymentId) throws IOException {
//        log.info("Razor Payment Id : {}", paymentId);
//        return Map.of("razorpay_payment_id", paymentId);
//    }
//
//    @GET
//    @Path("cancel")
//    public Map<String, String> cancel() throws IOException {
//        return Map.of("razorpay_payment_id", "null");
//    }

}
