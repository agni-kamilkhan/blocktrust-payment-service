package com.onblocktrust.controllers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractControllerTest {

    final static String END_POINT_PREFIX = "/api";

//    final static String SESSION_USER_ID = "userOidc";
//
//    final static String SESSION_USER_ROLE = "viewer";

    @Order(0)
    @Test
    @Transactional
    public void testProductInitialization() {
    }

}
