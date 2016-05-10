package com.djulia.session;

import org.junit.Test;

import static org.junit.Assert.*;

public class CredentialsTest {

    @Test
    public void testCreate() throws Exception {
        Credentials credentials = Credentials.builder().username("username").password("password").build();
        assertNotNull(credentials);
//        assertEquals(credentials, Credentials.create("username", "password"));
//        assertNotEquals(credentials, Credentials.create("jimbo", "zkjxc"));
//        assertNotEquals(credentials, Credentials.create("jimbo", "zkjxc"));
//
//        System.out.println("credentials.hashCode() = " + credentials.hashCode());
//        System.out.println("credentials.hashCode() = " + Credentials.create("jimbo", "zkjxc").hashCode());
    }
}