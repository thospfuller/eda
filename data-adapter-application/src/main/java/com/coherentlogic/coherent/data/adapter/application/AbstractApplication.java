package com.coherentlogic.coherent.data.adapter.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.coherentlogic.coherent.data.adapter.core.builders.AbstractQueryBuilder;
import com.coherentlogic.coherent.data.adapter.core.exceptions.IORuntimeException;
import com.coherentlogic.coherent.data.adapter.core.exceptions.InvalidURIException;
import com.coherentlogic.coherent.data.adapter.core.factories.TypedFactory;

import com.jamonapi.MonKey;
import com.jamonapi.MonKeyImp;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import groovy.lang.Binding;

/**
 * The front-end for the FRED Client that allows users to directly work with the
 * {@link com.coherentlogic.fred.client.core.builders.QueryBuilder}. 
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
//@SpringBootApplication
//@EnableAutoConfiguration
////@ComponentScan(basePackages="com.coherentlogic.fred.client")//<Q extends CacheableQueryBuilder<?, Object>>
public abstract class AbstractApplication extends JFrame implements CommandLineRunner {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(AbstractApplication.class);

    private static final String LOG = "log";

    private final String title;

    private final JTextArea outputTextArea = new JTextArea();

    private final JButton runScriptButton = new JButton("Run script");

    private final ButtonGroup requestMenuItemsGroup = new ButtonGroup();

    private final JMenu examplesMenu = new JMenu("Examples");

    private final List<JMenuItem> exampleMenuItems = new ArrayList<JMenuItem> ();

    private final Map<ButtonModel, JRadioButtonMenuItem> radioButtonMap =
        new HashMap<ButtonModel, JRadioButtonMenuItem> ();

    private GroovyEngine groovyEngine;

    @Autowired
    private ApplicationContext applicationContext;

    private final ObjectStringifier objectStringifier = new ObjectStringifier ();

    private final MonKey monKey = new MonKeyImp(
        "Query the web services and return an instance of a domain class.",
        TimeUnit.MILLISECONDS.toString());

    private final Map<String, GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>>>
        groovyExampleBeanMap
            = new HashMap<String, GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>>> ();

    protected AbstractApplication () {
        this ("Example Demonstration Application");
    }

    protected AbstractApplication (String title) {
        this.title = title;
    }

    /**
     * @see #initialize()
     */
    void initializeMenu (JTextArea inputTextArea) {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        menuBar.add(examplesMenu);

        groovyExampleBeanMap.forEach(
            (key, groovyExampleBean) -> {
                JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(key);

                exampleMenuItems.add(menuItem);

                examplesMenu.add(menuItem);

                menuItem.addActionListener(
                    new MenuItemSelectedActionListener (
                        groovyExampleBeanMap,
                        inputTextArea
                    )
                );

                if (getDefaultExampleKey ().equals(key))
                    menuItem.setSelected(true);

                requestMenuItemsGroup.add(menuItem);

                radioButtonMap.put(menuItem.getModel(), menuItem);
            }
        );

        addHelpAbout (menuBar);
    }

    private void addHelpAbout (JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        addAPIMenuItem (helpMenu);

        JMenuItem mntmAbout = new JMenuItem("About");
        helpMenu.add(mntmAbout);

        mntmAbout.addActionListener(
            new ActionListener () {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    AboutDialog dialog;

                    try {
                        dialog = new AboutDialog ();
                    } catch (IOException ioException) {
                        throw new IORuntimeException(
                            "Unable to create the AboutDialog.", ioException);
                    }
                    dialog.setVisible(true);
                }
            }
        );
    }

    private void addAPIMenuItem (JMenu helpMenu) {

        final String destination = "http://bit.ly/1G3dwJG";

        JMenuItem apiJavadocs = new JMenuItem("API Javadocs");

        apiJavadocs.setForeground(Color.blue);

        helpMenu.add(apiJavadocs);

        apiJavadocs.addActionListener(
            new ActionListener () {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    // This is for tracking purposes and will direct the user
                    // to http://coherentlogic.com/fredJavaDoc/
                    try {
                        AboutDialog.open(destination);
                    } catch (URISyntaxException uriSyntaxException) {
                        throw new InvalidURIException(
                            "Unable to open the destination: " + destination,
                            uriSyntaxException
                        );
                    }
                }
            }
        );
    }

    protected abstract String getDefaultExampleKey ();
//    {
//        return "Hello Example";
//    }

    protected abstract void loadResources (
        ApplicationContext applicationContext,
        Map<String, GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>>>
        groovyExampleBeanMap,
        GroovyEngine groovyEngine
    ) throws Exception;

//    protected void loadResources (
//        ApplicationContext applicationContext,
//        Map<String, GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>>>
//            groovyExampleBeanMap,
//        GroovyEngine groovyEngine
//    ) throws Exception {
//
//        ClassPathResource helloResource = (ClassPathResource) applicationContext.getBean("helloExampleResource");
//
//        StringWriter helloStringWriter = new StringWriter ();
//
//        InputStream helloIn = helloResource.getInputStream();
//
//        IOUtils.copy(helloIn, helloStringWriter);
//
//        String helloExample = helloStringWriter.toString();
//
//        groovyExampleBeanMap.put(
//            "Hello Example",
//            new GroovyExampleBean<TypedFactory<AbstractQueryBuilder<String,Object>>>(
//                helloExample,
//                null,
//                groovyEngine
//            )
//        );
//
//        ClassPathResource worldResource = (ClassPathResource) applicationContext.getBean("worldExampleResource");
//
//        StringWriter worldStringWriter = new StringWriter ();
//
//        InputStream worldIn = worldResource.getInputStream();
//
//        IOUtils.copy(worldIn, worldStringWriter);
//
//        String worldExample = worldStringWriter.toString();
//
//        groovyExampleBeanMap.put(
//            "World Example",
//            new GroovyExampleBean<TypedFactory<AbstractQueryBuilder<String,Object>>>(
//                worldExample,
//                null,
//                groovyEngine
//            )
//        );
//    }

    String getSelectedExampleMenuItemText () {

        String result = null;

        for (JMenuItem nextMenuItem : exampleMenuItems) {

            if (nextMenuItem.isSelected()) {
                result = nextMenuItem.getText();
                break;
            }
        }

        if (result == null)
            throw new IllegalStateException("At least one menu item must be selected.");

        log.info("result: " + result);

        return result;
    }

    /**
     * Method configures the Swing components that are added to this object's
     * JFrame.
     */
    @PostConstruct
    public void initialize () {

        Binding binding = new Binding ();

        groovyEngine = new GroovyEngine (binding);

        groovyEngine.setVariable(LOG, log);

        try {
            loadResources(applicationContext, groovyExampleBeanMap, groovyEngine);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(9999);
        }

        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JPanel parent = new JPanel();
        parent.setLayout(new BorderLayout()); 

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        parent.add(panel);

        getContentPane().add(parent, BorderLayout.CENTER);
        setExtendedState(Frame.MAXIMIZED_BOTH); 

        JLabel enterYourQueryLabel = new JLabel("Enter your query here:");

        panel.add(enterYourQueryLabel);

        final JTextArea inputTextArea = new JTextArea();

        JScrollPane inputTextAreaScrollPane = new JScrollPane(inputTextArea);

        inputTextAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        inputTextAreaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        initializeMenu(inputTextArea);

        String defaultExampleKey = getDefaultExampleKey();

        GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>> defaultExampleValue =
            groovyExampleBeanMap.get(defaultExampleKey);

        inputTextArea.setText(defaultExampleValue.getScriptText());

        inputTextArea.setColumns(80);
        inputTextArea.setRows(40);
        panel.add(inputTextAreaScrollPane);

        panel.add(runScriptButton);

        JLabel outputAppearsBelowLabel = new JLabel("The output appears below:");

        panel.add(outputAppearsBelowLabel);

        outputTextArea.setColumns(80);
        outputTextArea.setRows(40);

        JScrollPane outputTextAreaScrollPane = new JScrollPane(outputTextArea);

        outputTextAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(outputTextAreaScrollPane);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();

        Rectangle bounds = env.getMaximumWindowBounds();

        setBounds(bounds);

        runScriptButton.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                    // A bit sloppy.
                    String key = getSelectedExampleMenuItemText ();

                    GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>> groovyExampleBean
                        = groovyExampleBeanMap.get(key);

                    String scriptText = inputTextArea.getText();

                    log.info("scriptText:\n\n" + scriptText);

                    Object result = null;

                    Monitor monitor = MonitorFactory.start(monKey);

                    try {
                        result = groovyExampleBean.execute(panel, scriptText);
                    } catch (Throwable throwable) {

                        log.error("Evaluation failed for the script:\n\n" + scriptText, throwable);

                        String errorMessage = throwable.getMessage();

                        JOptionPane.showMessageDialog(
                            panel,
                            (errorMessage == null || "".equals(errorMessage))
                                ? "No error message provided -- see the log, which should have more details."
                                : errorMessage,
                            "Evaluation failed!",
                            JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    } finally {
                        monitor.stop();
                        log.info ("JAMon report: " + monitor);
                    }

                    log.info("result: " + result);

                    if (result != null) {
                        String stringifiedResult = objectStringifier.toString(result);

                        String fullResult =
                            "// Note that null values are not indicative of a problem, per se, for \n" +
                            "// example the PrimaryKey is only ever assigned when the object has been \n" +
                            "// saved to a database and since this does not happen in this example.\n\n\n" +
                            stringifiedResult;

                        outputTextArea.setText(fullResult);
                    }
                }
            }
        );
    }

    /**
     * The main method uses the Spring application context to get an instance of
     * {@link AbstractApplication} and then displays this object.
     */
    @Override
    public void run(String... args) throws Exception {
        setVisible(true);
    }

    public static void main (String[] unused) throws InterruptedException {

        try {

            SpringApplicationBuilder builder =
                new SpringApplicationBuilder (AbstractApplication.class);

            builder
                .web(false)
                .headless(false)
                .registerShutdownHook(true)
                .run(unused);

        } catch (Throwable thrown) {
            log.error("ExampleApplication.main caught an exception.", thrown);
        }

        Thread.sleep(Long.MAX_VALUE);

        System.exit(-9999);
    }
}

/**
 * An {@link java.awt.event ActionListener} implementation that adds a given example to the inputTextArea when the user
 * selects a given {@link javax.swing.JMenuItem}.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
class MenuItemSelectedActionListener implements ActionListener {

    private final Map<String, GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>>>
        groovyExampleBeanMap;

    private final JTextArea inputTextArea;

    public MenuItemSelectedActionListener(
        Map<String, GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>>>
            groovyExampleBeanMap,
        JTextArea inputTextArea
    ) {
        this.groovyExampleBeanMap = groovyExampleBeanMap;
        this.inputTextArea = inputTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        JMenuItem menuItem = (JMenuItem) actionEvent.getSource();

        String key = menuItem.getText();

        GroovyExampleBean<? extends TypedFactory<AbstractQueryBuilder<String, Object>>> groovyExampleBean
            = groovyExampleBeanMap.get(key);

        String example = groovyExampleBean.getScriptText();

        inputTextArea.setText(example);
    }
}
