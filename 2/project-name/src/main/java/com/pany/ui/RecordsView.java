package com.pany.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.pany.model.Record;
import com.pany.model.RecordMonitor;
import com.pany.ui.components.Avatar;
import com.pany.ui.components.InspectButton;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * View used for showing view
 *
 * @author Sobek
 *
 */
public class RecordsView extends CustomComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Grid<Record> recordsGrid;
    NativeSelect<Integer> pageNumber;
    NativeSelect<Integer> resultSize;
    Label totalResultsCount;

    public RecordsView() throws JSONException, IOException {
        super();
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout filtersLayout = new HorizontalLayout();
        pageNumber = new NativeSelect<Integer>();
        pageNumber.setCaption("Číslo stránky");
        List<Integer> pageNumbers = new ArrayList<Integer>();
        pageNumbers.add(1);
        pageNumbers.add(2);
        pageNumbers.add(3);
        pageNumbers.add(4);
        pageNumber.setItems(pageNumbers);

        resultSize = new NativeSelect<Integer>();
        List<Integer> pageSizes= new ArrayList<Integer>();
        pageSizes.add(10);
        pageSizes.add(20);
        pageSizes.add(50);
        pageSizes.add(100);
        resultSize.setItems(pageSizes);

        resultSize.setCaption("Počet záznamů");
        totalResultsCount = new Label();
        totalResultsCount.setCaption("Celkový počet záznamů: ");
        filtersLayout.addComponent(pageNumber);
        filtersLayout.addComponent(resultSize);
        filtersLayout.addComponent(totalResultsCount);
        Button findResults = new Button("Vyhledat");
        findResults.addClickListener(e -> {
            try {
                findResults(resultSize.getValue(),pageNumber.getValue());
            } catch (NumberFormatException e1) {
                e1.printStackTrace();
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        filtersLayout.addComponent(findResults);
        layout.addComponent(filtersLayout);
        recordsGrid = buildGrid();
        layout.addComponent(recordsGrid);
        findResults(20, 1);
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
    	totalResultsCount.setValue(RecordMonitor.getTotalRecords() + "");
        recordsGrid.setItems(RecordMonitor.getRecords());
    }

    private CustomComponent buildDetailButton(Record r) {
        return new InspectButton(r.getId());
    }

    private CustomComponent buildAvatar(Record r) {
        return new Avatar(r.getAvatar());
    }

}
