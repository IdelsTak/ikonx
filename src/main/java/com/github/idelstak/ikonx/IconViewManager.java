/*
 * MIT License
 *
 * Copyright (c) 2026 Hiram K
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.idelstak.ikonx;

import com.dlsc.gemsfx.*;
import java.util.*;
import java.util.logging.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import org.controlsfx.control.*;

public class IconViewManager implements IconView {

    private static final Logger LOG = Logger.getLogger(IconViewManager.class.getName());
    private static final int FILTER_LEN = 2;
    private final List<PackIkon> packIkons;
    @FXML
    private SearchTextField searchField;
    @FXML
    private CheckComboBox<Pack> packCombo;
    @FXML
    private ToggleButton selectAllToggle;
    @FXML
    private Tooltip selectTip;
    @FXML
    private TableView<List<PackIkon>> iconsTable;
    @FXML
    private StatusBar statusBar;

    public IconViewManager() {
        packIkons = new ArrayList<>();
    }

    /**
     * Types the given text into the search field.
     *
     * @param text The text to type.
     */
    public void typeIntoSearchField(String text) {
        searchField.setText(text);
    }

    /**
     * Clicks the select all toggle button.
     */
    public void clickSelectAllToggle() {
        selectAllToggle.fire();
    }

    @Override
    public String getSearchText() {
        return searchField.getText();
    }

    @Override
    public ObservableList<Pack> getSelectedPacks() {
        return packCombo.getCheckModel().getCheckedItems();
    }

    @Override
    public void setSearchText(String text) {
        searchField.setText(text);
    }

    @Override
    public void selectAllPacks() {
        packCombo.getCheckModel().checkAll();
    }

    @Override
    public void deselectAllPacks() {
        packCombo.getCheckModel().clearChecks();
    }

    @Override
    public void setPacks(List<Pack> packs) {
        packCombo.getItems().setAll(packs);
    }

    @Override
    public void setTableItems(Collection<List<PackIkon>> items) {
        iconsTable.getItems().setAll(items);
    }

    @Override
    public TableView<List<PackIkon>> getIconsTable() {
        return iconsTable;
    }

    @Override
    public CheckComboBox<Pack> getPackComboBox() {
        return packCombo;
    }

    @Override
    public ToggleButton getSelectAllToggle() {
        return selectAllToggle;
    }

    @FXML
    protected void initialize() {
        setupSearchField();
        setupPackComboBox();
        setupSelectAllToggle();
        setupIconsTable();

        List<Pack> packs = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Pack::toString))
          .toList();
        packs.forEach(this::setPackIkons);

        updateData(null);
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
          updateData(newValue));
    }

    private void setupPackComboBox() {
        packCombo.setTitle("Selected icon packs: ");
        packCombo.setShowCheckedCount(true);

        Comparator<Pack> packComparator = Comparator.comparing(Pack::toString);
        List<Pack> packs = Arrays.stream(Pack.values())
          .sorted(packComparator)
          .toList();
        LOG.log(Level.INFO, "sorted pack values: {0}", packs);

        setPacks(packs);
        Platform.runLater(() -> packCombo.getCheckModel().check(0));

        packCombo.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends Pack> change) ->
        {
            while (change.next()) {
                packIkons.clear();
                ObservableList<? extends Pack> checkedPacks = change.getList();

                Platform.runLater(() -> {
                    if (selectAllToggle.isSelected()) {
                        selectAllToggle.setSelected(checkedPacks.size() == packCombo.getItems().size());
                    }

                    updateSelectTipText();
                });

                checkedPacks.forEach(this::setPackIkons);

                updateData(getSearchText());
            }
        });
    }

    private void setupSelectAllToggle() {
        selectAllToggle.setOnAction(event -> {
            if (selectAllToggle.isSelected()) {
                Platform.runLater(this::selectAllPacks);
            } else {
                Pack firstPack = packCombo.getItems().get(0);
                Platform.runLater(() -> {
                    deselectAllPacks();
                    packCombo.getCheckModel().check(firstPack);
                });
            }

            updateSelectTipText();
        });

        updateSelectTipText();
    }

    private void setupIconsTable() {
        iconsTable.setPlaceholder(new Text("No result found"));

        iconsTable.getItems().addListener((ListChangeListener.Change<? extends List<PackIkon>> change) ->
        {
            while (change.next()) {
                statusBar.setText("%d icons".formatted(change.getList().size()));
            }
        });

        iconsTable.getSelectionModel().setCellSelectionEnabled(true);

        for (int i = 0; i < iconsTable.getColumns().size(); i++) {
            @SuppressWarnings("unchecked")
            TableColumn<List<PackIkon>, PackIkon> col = (TableColumn<List<PackIkon>, PackIkon>) iconsTable.getColumns().get(i);
            int colIndex = i;

            col.setCellValueFactory(cb -> {
                List<PackIkon> row = cb.getValue();
                PackIkon item = row.size() > colIndex ? row.get(colIndex) : null;
                return new SimpleObjectProperty<>(item);
            });

            col.setCellFactory(cb -> new FontIconCell());
            col.getStyleClass().add("align-center");
        }
    }

    private void updateSelectTipText() {
        selectTip.setText(selectAllToggle.isSelected() ? "Select first" : "Select all");
    }

    private void setPackIkons(Pack pack) {
        packIkons.addAll(Arrays.stream(pack.getIkons()).map(ikon -> new PackIkon(pack, ikon)).toList());
    }

    private void updateData(String filterString) {
        List<PackIkon> displayedIcons = filterString == null || filterString.isBlank() || filterString.length() < FILTER_LEN
                                        ? packIkons.stream().toList()
                                          : packIkons.stream().filter(packIkon ->
            containsString(packIkon.ikon().getDescription(), filterString)).toList();

        Collection<List<PackIkon>> data = partitionList(displayedIcons, iconsTable.getColumns().size());

        setTableItems(data);
    }

    private <T> Collection<List<T>> partitionList(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        if (list.isEmpty()) {
            return partitions;
        }

        int length = list.size();
        int numOfPartitions = length / size + ((length % size == 0) ? 0 : 1);

        for (int i = 0; i < numOfPartitions; i++) {
            int from = i * size;
            int to = Math.min((i * size + size), length);
            partitions.add(list.subList(from, to));
        }
        return partitions;
    }

    private boolean containsString(String s1, String s2) {
        return s1.toLowerCase(Locale.ROOT).contains(s2.toLowerCase(Locale.ROOT));
    }
}
