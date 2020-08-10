package com.pany.ui.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;

public class Avatar extends CustomComponent {

    public Avatar(String avatarUrl) {
        super();
        Image image = new Image();
        image.setSource(new ExternalResource(avatarUrl));
        image.setHeight("50px");
        image.setWidth("50px");
        setCompositionRoot(image);
    }
}
