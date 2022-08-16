package com.onblocktrust.service;

import com.onblocktrust.dtos.RazorPayOrder;

import java.io.IOException;

public interface RazorPayService {

    RazorPayOrder getCurrentOrder() throws IOException;

}
