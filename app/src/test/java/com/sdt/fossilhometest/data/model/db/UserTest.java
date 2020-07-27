package com.sdt.fossilhometest.data.model.db;

import com.sdt.fossilhometest.data.model.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testEquals() {
        User user = new User();
        User user1 = new User();
        assertEquals(user, user1);

        user.setUserId(1);
        user1.setUserId(2);
        assertNotEquals(user, user1);
    }

    @Test
    public void testHashCode() {
        User user = new User();
        Integer hashCode = user.hashCode();
        assertNotEquals(hashCode, null);
    }
}