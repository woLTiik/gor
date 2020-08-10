package com.pany.ui;

import java.io.IOException;

import org.json.JSONException;

import com.pany.model.Record;
import com.pany.model.RecordMonitor;
import com.pany.ui.components.Avatar;
import com.pany.ui.components.InspectButton;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * View used for showing view
 *
 * @author Sobek
 *
 */
public class RecordsView extends CustomComponent {
    Grid<Record> recordsGrid;
    TextField pageNumber;
    TextField resultSize;

    public RecordsView() {
        super();
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout filtersLayout = new HorizontalLayout();
        pageNumber = new TextField();
        pageNumber.setCaption("Číslo stránky");
        resultSize = new TextField();
        resultSize.setCaption("Počet záznamů");
        filtersLayout.addComponent(pageNumber);
        filtersLayout.addComponent(resultSize);
        Button findResults = new Button("Vyhledat");
        findResults.addClickListener(e -> {
            try {
                findResults(Integer.parseInt(resultSize.getValue()), Integer.parseInt(pageNumber.getValue()));
            } catch (NumberFormatException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (JSONException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        filtersLayout.addComponent(findResults);
        layout.addComponent(filtersLayout);
        recordsGrid = buildGrid();
        layout.addComponent(recordsGrid);
        setCompositionRoot(layout);
    }

    private Grid<Record> buildGrid() {
        Grid<Record> grid = new Grid<Record>();
        grid.setWidth("100%");
        // id - will be hidden
        grid.addColumn(Record::getId).setId("id");
        grid.getColumn("id").setCaption("Identifikátor");
        // detail button
        grid.addComponentColumn(this::buildDetailButton).setId("detail");
        // first name
        grid.addColumn(Record::getFirstName).setCaption("Jméno");
        // last name
        grid.addColumn(Record::getLastName).setCaption("Příjmení");
        // avatar
        grid.addComponentColumn(this::buildAvatar).setId("avatar");

        return grid;
    }

    private void findResults(int resultSize, int pageNumber) throws JSONException, IOException {
        MyUI.getAnsEJB().getRecords(resultSize, pageNumber);
    }

    public void update() {
        recordsGrid.setItems(RecordMonitor.getRecords());
    }

    private CustomComponent buildDetailButton(Record r) {
        return new InspectButton(r.getId());
    }

    private CustomComponent buildAvatar(Record r) {
        return new Avatar(r.getAvatar());
    }

}
