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
package com.github.idelstak.ikonx.view;

import com.dlsc.gemsfx.SearchTextField;
import com.github.idelstak.ikonx.icons.Pack;
import com.github.idelstak.ikonx.icons.PackIkon;
import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.action.Action;
import com.github.idelstak.ikonx.mvu.state.ViewState;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.*;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.StatusBar;

public class IconView {

    private final Flow stateFlow;
    private Disposable subscription;
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

    public IconView(Flow stateFlow) {
        this.stateFlow = stateFlow;
    }

    public void render(ViewState state) {
        Platform.runLater(() -> {
            if (!searchField.getText().equals(state.searchText())) {
                searchField.setText(state.searchText());
            }

            for (var pack : packCombo.getItems()) {
                var shouldBeChecked = state.selectedPacks().contains(pack);
                var isChecked = packCombo.getCheckModel().isChecked(pack);
                if (shouldBeChecked && !isChecked) {
                    packCombo.getCheckModel().check(pack);
                } else if (!shouldBeChecked && isChecked) {
                    packCombo.getCheckModel().clearCheck(pack);
                }
            }

            var allSelected = state.selectedPacks().size() == Pack.values().length;
            selectAllToggle.setSelected(allSelected);
            selectTip.setText(allSelected ? "Deselect all" : "Select all");

            var partitioned = partitionList(state.displayedIcons(), iconsTable.getColumns().size());
            var sm = iconsTable.getSelectionModel();
            var selectedPos = sm.getSelectedCells().isEmpty() ? null : sm.getSelectedCells().get(0);
            iconsTable.getItems().setAll(partitioned);
            if (selectedPos != null) {
                int row = selectedPos.getRow();
                int col = selectedPos.getColumn();
                if (row < iconsTable.getItems().size() && col < iconsTable.getColumns().size()) {
                    sm.clearSelection();
                    sm.select(row, iconsTable.getColumns().get(col));
                }
            }

            statusBar.setText(state.statusMessage());
        });
    }

    public void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    @FXML
    protected void initialize() {
        subscription = stateFlow.observe().subscribe(this::render);

        iconsTable.getSelectionModel().setCellSelectionEnabled(true);
        iconsTable.setPlaceholder(new Text("No result found"));

        setupTableColumns();
        setupPackCombo();
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        searchField.textProperty().addListener((_, _, newVal) ->
          stateFlow.accept(new Action.SearchChanged(newVal))
        );

        selectAllToggle.setOnAction(event ->
          stateFlow.accept(new Action.SelectAllToggled(selectAllToggle.isSelected()))
        );

        packCombo.getCheckModel().getCheckedItems().addListener((ListChangeListener<Pack>) change ->
        {
            while (change.next()) {
                for (var removed : change.getRemoved()) {
                    stateFlow.accept(new Action.PackToggled(removed, false));
                }
                for (var added : change.getAddedSubList()) {
                    stateFlow.accept(new Action.PackToggled(added, true));
                }
            }
        });
    }

    private void setupPackCombo() {
        packCombo.setTitle("Selected icon packs: ");
        packCombo.setShowCheckedCount(true);

        var packs = Arrays.stream(Pack.values()).sorted(Comparator.comparing(Pack::toString)).toList();
        packCombo.getItems().setAll(packs);
    }

    private void setupTableColumns() {
        for (var i = 0; i < iconsTable.getColumns().size(); i++) {
            @SuppressWarnings("unchecked")
            var col = (TableColumn<List<PackIkon>, PackIkon>) iconsTable.getColumns().get(i);
            var colIndex = i;

            col.setCellValueFactory(cb -> {
                var row = cb.getValue();
                var item = (row != null && row.size() > colIndex) ? row.get(colIndex) : null;
                return new SimpleObjectProperty<>(item);
            });
            col.setCellFactory(_ -> new FontIconCell(stateFlow::accept));
            col.getStyleClass().add("align-center");
        }
    }

    private <T> List<List<T>> partitionList(List<T> list, int size) {
        var partitions = new ArrayList<List<T>>();
        if (list.isEmpty()) {
            return partitions;
        }

        var numPartitions = (list.size() + size - 1) / size;
        for (var i = 0; i < numPartitions; i++) {
            var from = i * size;
            var to = Math.min((i + 1) * size, list.size());
            partitions.add(list.subList(from, to));
        }
        return partitions;
    }
}
