package com.api.client;

import com.intuit.karate.junit5.Karate;

public class ClientTest {

    @Karate.Test
    Karate testClient() {
        return Karate.run("client").relativeTo(getClass());
    }
}
