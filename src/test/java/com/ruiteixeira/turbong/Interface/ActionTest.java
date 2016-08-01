package com.ruiteixeira.turbong.Interface;

import junit.framework.TestCase;

public class ActionTest extends TestCase {
    public void testIsValid_True() throws Exception {
        Action action = new Action("{type:test}");
        assertTrue(action.isValid());
    }

    public void testIsValid_NullAction_False() throws Exception {
        Action action = new Action(null);
        assertFalse(action.isValid());
    }

    public void testIsValid_InvalidAction_False() throws Exception {
        Action action = new Action("{type;test}");
        assertFalse(action.isValid());
    }

    public void testGetValueOf_Correct() throws Exception {
        Action action = new Action("{type:test}");
        assertEquals("test", action.getValueOf("type"));
    }

    public void testGetValueOf_NullAction_Null() throws Exception {
        Action action = new Action(null);
        assertEquals(null, action.getValueOf("type"));
    }

    public void testGetValueOf_NonExistent_Null() throws Exception {
        Action action = new Action("{type:test}");
        assertEquals(null, action.getValueOf("test"));
    }

    public void testGetValueOf_NullKey_Null() throws Exception {
        Action action = new Action("{type:test}");
        assertEquals(null, action.getValueOf(null));
    }

    public void testAddObject_Correct() throws Exception {
        Action action = new Action("{test:type}");
        action.addObject("type", "test");
        assertEquals("test", action.getValueOf("type"));
    }

    public void testAddObject_AddToNull_Correct() throws Exception {
        Action action = new Action(null);
        action.addObject("type", "test");
        assertEquals("test", action.getValueOf("type"));
    }

    public void testAddObject_AddNullToNull_Correct() throws Exception {
        Action action = new Action("{}");
        action.addObject(null, null);
        assertTrue(action.isValid());
    }

    public void testToString_Correct() throws Exception {
        Action action = new Action("{type:test}");
        assertEquals("{\"type\":\"test\"}", action.toString());
    }

    public void testToString_NullAction() throws Exception {
        Action action = new Action(null);
        assertEquals(null, action.toString());
    }
}