package com.sdt.fossilhometest.data.model.db;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReputationHistoryTest {

    @Test
    public void testEquals() {
        ReputationHistory rh1 = new ReputationHistory();
        ReputationHistory rh2 = new ReputationHistory();
        assertEquals(rh1, rh2);

        rh1.setUserId(1);
        rh1.setCreationDate(123456);
        rh1.setPostId(123);

        rh2.setUserId(2);
        rh2.setCreationDate(1234567);
        rh2.setPostId(456);
        assertNotEquals(rh1, rh2);
    }

    @Test
    public void testHashCode() {
        ReputationHistory rh = new ReputationHistory();
        Integer hashCode = rh.hashCode();
        assertNotEquals(hashCode, null);
    }
}