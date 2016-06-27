package com.coherentlogic.coherent.data.model.core.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class allows the developer to add some text and then log it (at info level) and also print it to System.out. The
 * message to System.out can be ignored by setting the ignoreWelcomeMessageFlag system property to true.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public class WelcomeMessage {

    private static final Logger log = LoggerFactory.getLogger(WelcomeMessage.class);

    private final List<String> text;

    public static final String IGNORE_WELCOME_MESSAGE_FLAG = "ignoreWelcomeMessageFlag";

    private final Boolean ignoreWelcomeMessageFlag;

    public WelcomeMessage () {
        this (new ArrayList<String> ());
    }

    public WelcomeMessage (List<String> text) {

        this.text = text;

        String flag = System.getProperty(IGNORE_WELCOME_MESSAGE_FLAG);

        ignoreWelcomeMessageFlag = (flag != null) ? Boolean.valueOf(flag) : Boolean.FALSE;
    }

    public WelcomeMessage addText (String... text) {

        for (String next : text)
            this.text.add(next);

        return this;
    }

    public List<String> getText() {
        return text;
    }

    public WelcomeMessage display () {

        text.forEach(
            next -> {

                log.info(next);

                if (!ignoreWelcomeMessageFlag)
                    System.out.println(next);
            }
        );

        return this;
    }

    @Override
    public String toString() {
        return "WelcomeMessage [text=" + text + "]";
    }

    public static void main (String[] unused) {

        new WelcomeMessage()
            .addText("********************************************************************************")
            .addText("*** All work and no play makes Jack a dull boy.                              ***")
            .addText("********************************************************************************")
        .display();
    }
}
