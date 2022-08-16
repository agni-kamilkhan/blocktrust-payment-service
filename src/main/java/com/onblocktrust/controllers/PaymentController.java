package com.onblocktrust.controllers;

import com.onblocktrust.dtos.PaymentDetail;
import com.onblocktrust.service.PayUService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Kamil Khan
 */

@Slf4j
@Path("/api/payments")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
public class PaymentController {

    @Inject
    PayUService payUService;

    @GET
    @Path("payment-detail")
    public PaymentDetail getPaymentDetail() {
        return payUService.getPaymentDetail();
    }

}
