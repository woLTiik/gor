package com.pany.ui.components;

import com.pany.model.User;
import com.pany.ui.MyUI;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DetailWindow extends Window {
    User u;
    VerticalLayout mainLayout = new VerticalLayout();

    public DetailWindow(String personID) {
        this.setClosable(true);
        this.setModal(true);
        this.setContent(mainLayout);
        this.setIcon(VaadinIcons.INFO_CIRCLE);
        this.setCaption("Detail");
        this.setModal(true);
        this.center();
        u = getDetails(personID);
        build(u);
    }

    private User getDetails(String personId) {
        return MyUI.getAnsEJB().getUserDetails(personId);
    }

    private void build(User u) {
        if (u == null) {
            return;
        }
        Image image = new Image();
        image.setSource(new ExternalResource(u.getAvatar()));
        Label name = new Label("Jméno: " + u.getFirstName());
        Label surName = new Label("Příjmení: " + u.getLastName());
        Label email = new Label("Email: " + u.getEmail());
        Label id = new Label("Id: " + u.getDetailID());
        Label company = new Label("Společnost: " + u.getCompany().getName());
        Label location = new Label(
                "Umístění: lat:" + u.getCompany().getLatitude() + ", long: " + u.getCompany().getLongitude());
        Label ipAddress = new Label("Ip adresa: " + u.getCompany().getIpAddress());
        mainLayout.addComponents(image, name, surName, email, id, company, location, ipAddress);
    }

}
