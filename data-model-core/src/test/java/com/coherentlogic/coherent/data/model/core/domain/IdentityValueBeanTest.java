package com.coherentlogic.coherent.data.model.core.domain;

import static com.coherentlogic.coherent.data.model.core.util.Constants.VALUE;
import static com.coherentlogic.coherent.data.model.core.util.TestUtils.testSetterMethod;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.data.model.core.util.Action;
import com.coherentlogic.coherent.data.model.core.util.Flag;

/**
 * Unit test for the {@link IdentityValueBean} class.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class IdentityValueBeanTest {

    private final Flag flag = new Flag ();

    private IdentityValueBean identityValueBean = null;

    @Before
    public void setUp() throws Exception {
        identityValueBean = new IdentityValueBean ();
    }

    @After
    public void tearDown() throws Exception {
        identityValueBean = null;
        flag.setValue(false);
    }

    @Test
    public void testSetValue() {

    	final String blah = "blah";

        testSetterMethod (
            identityValueBean,
            flag,
            VALUE,
            null,
            blah, // Just pick a random string.
            new Action<IdentityValueBean> () {
                @Override
                public void execute(IdentityValueBean data) {
                    identityValueBean.setValue(blah);
                }
            }
        );
    }
}
