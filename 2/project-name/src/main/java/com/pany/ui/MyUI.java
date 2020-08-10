package com.pany.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.pany.ejb.BroadcastListener;
import com.pany.ejb.Broadcaster;
import com.pany.ejb.RecordsEjbInterface;
import com.pany.ui.components.ErrorWindow;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Push
@Theme("mytheme")
public class MyUI extends UI implements BroadcastListener {
    /**
     *
     */
    private static final long serialVersionUID = -805538037904553422L;

    /**
     * View that show records :)
     */
    private RecordsView recordsView;

    /**
     * Logger
     */
    public static final Logger LOGGER = Logger.getLogger(MyUI.class.getName());

    /**
     * EJB
     */
    private static RecordsEjbInterface recordsEjb;

    private int currentWidth;

    private int currentHeight;

    /**
     *
     * @return reference to EJB
     */
    public static RecordsEjbInterface getAnsEJB() {
        return recordsEjb;
    }

    /**
     *
     * @return an instance of UI
     */
    public static MyUI getCurrent() {
        return (MyUI) UI.getCurrent();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        recordsView = new RecordsView();
        recordsView.setSizeFull();
        setContent(recordsView);
        Broadcaster.register(this);
        setErrorHandler((ErrorHandler) event -> {
            Throwable throwable = event.getThrowable();
            MyUI.LOGGER.log(Level.SEVERE, "Neočekávaná výjimka", event.getThrowable());
            ErrorWindow window = new ErrorWindow(throwable);
            MyUI.getCurrent().getUI().getUI().addWindow(window);
        });
    }

    @WebServlet(urlPatterns = "/*", name = "MyServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false, heartbeatInterval = 20)
    public static class MyUIServlet extends VaadinServlet {
        /**
         *
         */
        private static final long serialVersionUID = 9183610930653981892L;
        @EJB
        private RecordsEjbInterface recEJB;

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            MyUI.recordsEjb = recEJB;
        }
    }

    /**
     * used for
     *
     * unregister UI from {@link Broadcaster} when user closes window or* connection
     * is lost
     */

    @Override
    public void close() {
        Broadcaster.unregister(this);
        if (this != null) {
            try {
                super.close();
            } catch (Exception e) {
                MyUI.LOGGER.warning("Could not close UI");
            }
        }
    }

    /**
     * @return the currentWidth
     */
    public int getCurrentWidth() {
        if (currentWidth == 0) {
            currentWidth = getPage().getBrowserWindowWidth();
        }
        return currentWidth;
    }

    /**
     * @return the currentHeight
     */
    public int getCurrentHeight() {
        if (currentHeight == 0) {
            currentHeight = getPage().getBrowserWindowHeight();
        }
        return currentHeight;
    }

    @Override
    public void update() {
        recordsView.update();

    }

}
