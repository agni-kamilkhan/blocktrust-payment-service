package com.onblocktrust.controllers;

import com.onblocktrust.dtos.PaymentDetail;
import com.onblocktrust.dtos.RazorPayOrder;
import com.onblocktrust.service.PayUService;
import com.onblocktrust.service.RazorPayService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.IOException;

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

}
