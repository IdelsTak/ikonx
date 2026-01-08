/*
 * The MIT License
 * Copyright © 2026 Hiram K. <https://github.com/IdelsTak>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.idelstak.ikonx.view;

import com.dlsc.gemsfx.*;
import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.mvu.state.icons.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import io.reactivex.rxjava3.disposables.*;
import java.util.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.stage.*;
import org.controlsfx.control.*;
import org.pdfsam.rxjavafx.schedulers.*;

public class IconView {

    private final Flow flow;
    private Disposable subscription;
    private PackSelectionView packSelection;
    private TableSelectionFix tableFix;
    private Stage stage;
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

    public IconView(Flow flow) {
        this.flow = flow;
    }

    public void render(ViewState state) {
        if (stage != null) {
            var title = switch (state.version()) {
                case AppVersion.Ready(String appValue, String ikonliValue) ->
                    "IkonX v%s - for ikonli v%s".formatted(appValue, ikonliValue);
                case AppVersion.Failed _ ->
                    "IkonX (version unavailable)";
                case AppVersion.Unknown _ ->
                    "IkonX";
            };

            stage.setTitle(title);

            List<Image> images = switch (state.stageIcons()) {
                case StageIcons.Ready(List<Image> readyImages) ->
                    List.copyOf(readyImages);
                case StageIcons.Failed _ ->
                    List.of();
                case StageIcons.Unknown _ ->
                    List.of();
            };

            stage.getIcons().setAll(images);
        }
        searchField.setText(state.searchText());
        packSelection.render(state.selectedPacks());
        selectAllToggle.setSelected(state.selectedPacks().size() == Pack.values().length);
        selectTip.setText(selectAllToggle.isSelected() ? "Deselect all" : "Select all");
        tableFix.render(() -> {
            iconsTable.getItems().setAll(partition(state.displayedIkons(), iconsTable.getColumns().size()));
        });
        statusBar.setText(state.statusMessage());
    }

    @FXML
    protected void initialize() {
        setupStage();
        setupTable();
        setupPacks();
        setupInputs();
        setupActionsSubscription();
    }

    private void setupStage() {
        Platform.runLater(() -> {
            stage = (Stage) searchField.getScene().getWindow();
            stage.setOnCloseRequest(_ -> {
                dispose();
                Platform.exit();
                System.exit(0);
            });
        });
    }

    private void setupInputs() {
        searchField.textProperty().addListener((_, _, text) ->
          flow.accept(new Action.SearchChanged(text))
        );
        selectAllToggle.setOnAction(_ -> flow.accept(new Action.SelectAllPacksToggled()));
    }

    private void setupActionsSubscription() {
        Platform.runLater(() -> {
            subscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
        });
    }

    private void setupPacks() {
        packCombo.setTitle("Selected icon packs: ");
        packCombo.setShowCheckedCount(true);
        var packs = Arrays.stream(Pack.values()).sorted(Comparator.comparing(Pack::toString)).toList();
        packCombo.getItems().setAll(packs);
        packSelection = new PackSelectionView(packCombo, flow::accept);
    }

    private void setupTable() {
        tableFix = new TableSelectionFix(iconsTable);
        iconsTable.getSelectionModel().setCellSelectionEnabled(true);
        iconsTable.setPlaceholder(new Text("No result found"));
        for (var i = 0; i < iconsTable.getColumns().size(); i++) {
            @SuppressWarnings("unchecked")
            var col = (TableColumn<List<PackIkon>, PackIkon>) iconsTable.getColumns().get(i);
            var index = i;
            col.setCellValueFactory(cb -> {
                var row = cb.getValue();
                var item = row != null && row.size() > index ? row.get(index) : null;
                return new SimpleObjectProperty<>(item);
            });
            col.setCellFactory(_ -> new FontIconCell(flow::accept));
            col.getStyleClass().add("align-center");
        }
    }

    private <T> List<List<T>> partition(List<T> list, int size) {
        var parts = new ArrayList<List<T>>();
        for (var i = 0; i < list.size(); i += size) {
            parts.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return parts;
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
