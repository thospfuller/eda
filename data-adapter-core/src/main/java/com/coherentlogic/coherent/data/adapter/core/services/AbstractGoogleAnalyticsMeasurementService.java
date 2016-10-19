package com.coherentlogic.coherent.data.model.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class is used to send events to Google Analytics via the Measurement API.
 *
 * @see <a href="https://developers.google.com/analytics/devguides/collection/protocol/v1/devguide">Working with the
 *  Measurement Protocol</a>
 *
 * @see <a href="https://ga-dev-tools.appspot.com/hit-builder/">Hit Builder</a>
 *
 * @author <a href="https://www.linkedin.com/in/thomasfuller">Thomas P. Fuller</a>
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class AbstractGoogleAnalyticsMeasurementService {

    private static final Logger log = LoggerFactory.getLogger(AbstractGoogleAnalyticsMeasurementService.class);

    static final String GOOGLE_ANALYTICS_TRACKING_KEY = "GOOGLE_ANALYTICS_TRACKING";

    public boolean shouldTrack () {

        String gatValue = System.getProperty(GOOGLE_ANALYTICS_TRACKING_KEY);

        return (gatValue == null || Boolean.parseBoolean(gatValue));
    }

    /**
     * Method calls the Google Analytics Measurement API with framework usage information.
     */
    public abstract void fireGAFrameworkUsageEvent ();
}
