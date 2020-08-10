package com.pany.ui.components;

import com.pany.ui.MyUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;

public class InspectButton extends CustomComponent {

    public InspectButton(String personID) {
        Button b = new Button();
        b.setIcon(VaadinIcons.INFO);
        b.addClickListener(e -> {
            DetailWindow window = new DetailWindow(personID);
            MyUI.getCurrent().getUI().getUI().addWindow(window);
        });
        setCompositionRoot(b);
    }
}
