package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Route("")
public class MainView extends VerticalLayout {

    private final Grid<DataModel> grid1 = new Grid<>();
    private final Grid<DataModel> grid2 = new Grid<>();
    private ListDataProvider<DataModel> dataProvider;
    private ListDataProvider<DataModel> dataProvider2;
    private final EntityService service;

    @Autowired
    public MainView(EntityService service) {
        this.service = service;

        initializeDataProviders();

        // Set up Tabs
        Tab tab1 = new Tab("CRUD Grid");
        Tab tab2 = new Tab("Filtered Grid");
        Tabs tabs = new Tabs(tab1, tab2);

        // Create a layout for the tab content
        VerticalLayout tabContent = new VerticalLayout();
        setContent(tabContent);

        tabs.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().equals(tab1)) {
                tabContent.removeAll();
                tabContent.add(createFirstTabContent(service));
            } else {
                tabContent.removeAll();
                tabContent.add(createSecondTabContent(service));
            }
        });

        // Initial content
        tabContent.add(createFirstTabContent(service));

        // Add the tabs and the tab content to the layout
        add(tabs, tabContent);
    }

    private void initializeDataProviders(){
        List<DataModel> data = service.findAll();
        dataProvider = new ListDataProvider<>(data);
        dataProvider2 = new ListDataProvider<>(data);
    }

    private void setContent(VerticalLayout content) {
        removeAll();
        add(content);
    }

    private VerticalLayout createFirstTabContent(EntityService service) {
        VerticalLayout layout = new VerticalLayout();

        // Configure Grid for CRUD operations
        grid1.addColumn(DataModel::getMsCode).setHeader("MsCode").setSortable(true);
        grid1.addColumn(DataModel::getYear).setHeader("Year").setSortable(true);
        grid1.addColumn(DataModel::getEstCode).setHeader("EstCode").setSortable(true);
        grid1.addColumn(DataModel::getEstimate).setHeader("Estimate").setSortable(true);
        grid1.addColumn(DataModel::getSE).setHeader("SE").setSortable(true);
        grid1.addColumn(DataModel::getLowerCIB).setHeader("Lower CIB").setSortable(true);
        grid1.addColumn(DataModel::getUpperCIB).setHeader("Upper CIB").setSortable(true);
        grid1.addColumn(DataModel::getFlag).setHeader("Flag").setSortable(true);
        grid1.setItems(dataProvider);

        // Double-click listener for rows
        grid1.addItemDoubleClickListener(event -> {
            DataModel entity = event.getItem();
            openEditDialog(entity, service);
        });

        layout.add(grid1);
        return layout;
    }

    private VerticalLayout createSecondTabContent(EntityService service) {
        VerticalLayout layout = new VerticalLayout();

        // Configure Grid for filtered display
        grid2.setItems(dataProvider2);
        grid2.addColumn(DataModel::getYear).setHeader("Year").setSortable(true);
        grid2.addColumn(DataModel::getEstCode).setHeader("EstCode").setSortable(true);
        grid2.addColumn(DataModel::getEstimate).setHeader("Estimate").setSortable(true);
        grid2.addColumn(DataModel::getSE).setHeader("SE").setSortable(true);
        grid2.addColumn(DataModel::getLowerCIB).setHeader("Lower CIB").setSortable(true);
        grid2.addColumn(DataModel::getUpperCIB).setHeader("Upper CIB").setSortable(true);
        grid2.addColumn(DataModel::getFlag).setHeader("Flag").setSortable(true);

        // Dropdown menu for filtering
        ComboBox<String> msCodeComboBox = new ComboBox<>("Select MsCode");
        List<String> msCodes = service.findAll().stream()
                .map(DataModel::getMsCode)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        msCodeComboBox.setItems(msCodes);
        msCodeComboBox.addValueChangeListener(event -> {
            String selectedMsCode = event.getValue();
            if (selectedMsCode != null) {
                dataProvider2.setFilter(DataModel::getMsCode, msCode -> msCode.equals(selectedMsCode));
            } else {
                dataProvider2.clearFilters();
            }
        });

        layout.add(msCodeComboBox, grid2);
        return layout;
    }

    private void openEditDialog(DataModel entity, EntityService service) {
        Dialog dialog = new Dialog();
        VerticalLayout dialogLayout = new VerticalLayout();

        TextField msCodeField = new TextField("MsCode");
        msCodeField.setValue(entity.getMsCode() != null ? entity.getMsCode() : "");

        TextField yearField = new TextField("Year");
        yearField.setValue(entity.getYear() != null ? entity.getYear() : "");

        TextField estCodeField = new TextField("EstCode");
        estCodeField.setValue(entity.getEstCode() != null ? entity.getEstCode() : "");

        TextField estimateField = new TextField("Estimate");
        estimateField.setValue(entity.getEstimate() != null ? entity.getEstimate().toString() : "0");

        TextField seField = new TextField("SE");
        seField.setValue(entity.getSE() != null ? entity.getSE().toString() : "0");

        TextField lowerCIBField = new TextField("LowerCIB");
        lowerCIBField.setValue(entity.getLowerCIB() != null ? entity.getLowerCIB().toString() : "0");

        TextField upperCIBField = new TextField("UpperCIB");
        upperCIBField.setValue(entity.getUpperCIB() != null ? entity.getUpperCIB().toString() : "0");

        TextField flagField = new TextField("Flag");
        flagField.setValue(entity.getFlag() != null ? entity.getFlag() : "");

        Button saveButton = new Button("Save", e -> {
            // Save changes to the entity
            entity.setMsCode(msCodeField.getValue());
            entity.setYear(yearField.getValue());
            entity.setEstCode(estCodeField.getValue());
            entity.setEstimate(Float.parseFloat(estimateField.getValue()));
            entity.setSE(Float.parseFloat(seField.getValue()));
            entity.setLowerCIB(Float.parseFloat(lowerCIBField.getValue()));
            entity.setUpperCIB(Float.parseFloat(upperCIBField.getValue()));
            entity.setFlag(flagField.getValue());
            service.save(entity);
            dataProvider.refreshAll();
            dialog.close();
        });

        Button cancelButton = new Button("Cancel", e -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);

        dialogLayout.add(msCodeField, yearField, estCodeField, estimateField, seField, lowerCIBField, upperCIBField, flagField, buttons);
        dialog.add(dialogLayout);
        dialog.open();
    }
}
