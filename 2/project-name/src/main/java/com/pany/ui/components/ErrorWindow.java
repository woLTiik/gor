package com.pany.ui.components;

import java.util.Date;

import com.pany.ui.MyUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Class that shows error message for user if any unhandled error occurs, raised
 * by errorHandler in AnsUi {@link AnsUi}
 *
 * @author Sobek
 *
 */
public class ErrorWindow extends Window {

    /**
     *
     */
    private static final long serialVersionUID = 3714950346926891711L;

    boolean withDetails = false;

    VerticalLayout mainLayout = new VerticalLayout();

    Throwable throwable;

    VerticalLayout detailsLayout = new VerticalLayout();

    Label detailsLabel;

    public ErrorWindow(Throwable throwable) {
        super();
        this.throwable = throwable;

        this.setClosable(true);
        this.setModal(true);
        this.addStyleName("error-msg");
        this.setContent(mainLayout);
        this.setIcon(VaadinIcons.CLOSE_CIRCLE);
        this.setCaption("Neočekávaná chyba");
        this.setModal(true);
        build();
    }

    private void build() {
        detailsLayout.addStyleName("auto-overflow");
        detailsLabel = new Label();
        StringBuilder sb = new StringBuilder();
        sb.append("Time: " + new Date().toGMTString() + "<br>");
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append(element.toString());
            sb.append("<br/>");
        }
        detailsLabel.setValue(sb.toString());
        detailsLabel.setContentMode(ContentMode.HTML);
        Button close = new Button("Zavřít");
        close.setIcon(VaadinIcons.CLOSE);
        close.addClickListener(e -> {
            this.close();
        });
        Label title = new Label();
        title.addStyleName("grid-text");

        title.setCaption("Popis chyby");
        title.setIcon(VaadinIcons.INFO_CIRCLE);
        mainLayout.addComponent(title);

        Button showDetails = new Button("Zobrazit detaily");
        showDetails.setIcon(VaadinIcons.PLUS);
        showDetails.addClickListener(e -> {
            withDetails = !withDetails;
            if (withDetails) {
                showDetails.setCaption("Schovat detaily");
                showDetails.setIcon(VaadinIcons.MINUS);
                detailsLayout.addComponent(detailsLabel);
                detailsLayout.setHeight((MyUI.getCurrent().getCurrentHeight() - 500) + "px");
            } else {
                showDetails.setCaption("Zobrazit detaily");
                showDetails.setIcon(VaadinIcons.PLUS);
                detailsLayout.removeAllComponents();
                detailsLayout.setHeight("10px");
            }
            this.center();
        });
        mainLayout.addComponent(showDetails);
        mainLayout.addComponent(detailsLayout);
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponent(close);
        mainLayout.addComponent(hLayout);
        this.center();
    }
}
