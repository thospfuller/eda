package com.coherentlogic.coherent.data.adapter.core.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coherentlogic.coherent.data.adapter.core.services.AbstractGoogleAnalyticsMeasurementService;

/**
 * Unit test for the {@link AbstractGoogleAnalyticsMeasurementService} class.
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class AbstractGoogleAnalyticsMeasurementServiceTest {

    private AbstractGoogleAnalyticsMeasurementService googleAnalyticsMeasurementService = null;

    @Before
    public void setUp() throws Exception {
        googleAnalyticsMeasurementService = new AbstractGoogleAnalyticsMeasurementService () {
            @Override
            public void fireGAFrameworkUsageEvent() {
                throw new RuntimeException ("MNYI");
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        googleAnalyticsMeasurementService = null;
    }

    @Test
    public void testShouldTrackTrue() {

        System.setProperty(AbstractGoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY, "true");

        assertTrue (googleAnalyticsMeasurementService.shouldTrack());
    }

    @Test
    public void testShouldTrackFalse() {

        System.setProperty(AbstractGoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY, "false");

        assertFalse (googleAnalyticsMeasurementService.shouldTrack());
    }

    @Test
    public void testShouldTrackNull() {

        System.clearProperty(AbstractGoogleAnalyticsMeasurementService.GOOGLE_ANALYTICS_TRACKING_KEY);

        assertTrue (googleAnalyticsMeasurementService.shouldTrack());
    }
}
