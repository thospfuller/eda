package com.coherentlogic.coherent.data.model.core.util;

import org.junit.Test;

/**
 * Unit test for the {@link Utils} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class UtilsTest {

    @Test(expected=NullPointerException.class)
    public void testAssertNotNullThrowsNPE() {
        Utils.assertNotNull("isNull", null);
    }

    @Test
    public void testAssertNotNullDoesNotThrowNPE() {
        Utils.assertNotNull("isNotNull", "is not null");
    }
}
