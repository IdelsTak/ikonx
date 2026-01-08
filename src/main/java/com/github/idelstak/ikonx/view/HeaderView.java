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

import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.view.grid.*;
import io.reactivex.rxjava3.disposables.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.*;
import org.pdfsam.rxjavafx.schedulers.*;

public class HeaderView implements Initializable {

    private final Stage stage;
    private final Flow flow;
    private Disposable subscription;
    @FXML
    private ToggleButton toggleViewButton;
    @FXML
    private Tooltip toggleViewTip;
    @FXML
    private ComboBox<?> copyFormatComboBox;
    @FXML
    private TextField searchInput;
    @FXML
    private Button filterButton;
    @FXML
    private Label packCountLabel;
    @FXML
    private Text titleVersion;

    public HeaderView(Stage stage, Flow flow) {
        this.stage = stage;
        this.flow = flow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupStage();
        setupActionsSubscription();
        setupViewToggle();
        setupSearchInput();
        setupFilterButton();
    }

    private void setupStage() {
        stage.setOnCloseRequest(_ -> this.dispose());
    }

    private void setupActionsSubscription() {
        Platform.runLater(() -> {
            subscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
        });
    }

    private void setupViewToggle() {
        toggleViewButton.setOnAction(_ -> flow.accept(new Action.ViewModeToggled()));
    }

    private void setupSearchInput() {
        searchInput.textProperty().addListener((_, _, text) ->
          flow.accept(new Action.SearchChanged(text)));
    }

    private void setupFilterButton() {
        filterButton.setOnAction(_ -> flow.accept(new Action.FilterPacksRequested()));
    }

    private void render(ViewState state) {
        var version = switch (state.version()) {
            case AppVersion.Ready(String appValue, String _) ->
                appValue;
            case AppVersion.Failed _ ->
                "(version unavailable)";
            case AppVersion.Unknown _ ->
                "";
        };
        titleVersion.setText("v" + version);

        var isGridView = state.viewMode() instanceof ViewMode.Grid;
        toggleViewButton.setSelected(isGridView);
        toggleViewButton.getStyleClass().removeAll("grid", "list");
        toggleViewButton.getStyleClass().add(isGridView ? "grid" : "list");
        var prefix = "View icons in a ";
        var targetView = isGridView ? "list" : "grid";
        toggleViewTip.setText(prefix + targetView);

        var iconsCount = state.displayedIkons().size();
        searchInput.setPromptText("Search %d icons...".formatted(iconsCount));

        var packsCount = state.selectedPacks().size();
        packCountLabel.setText("Packs (%d)".formatted(packsCount));
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
