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

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.mvu.state.view.*;
import io.reactivex.rxjava3.disposables.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.pdfsam.rxjavafx.schedulers.*;

public final class PacksFilterView implements Initializable {

    private final Stage stage;
    private final Flow flow;
    private Disposable subscription;
    @FXML
    private VBox filterDropdown;
    @FXML
    private ToggleButton toggleAllButton;
    @FXML
    private VBox packsListVBox;
    @FXML
    private FlowPane stylesListFlowPane;

    public PacksFilterView(Stage stage, Flow flow) {
        this.stage = stage;
        this.flow = flow;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupStage();
        setupActionsSubscription();
        setupToggleAllButton();
        setupHideOnClick();
    }

    private void setupStage() {
        stage.setOnCloseRequest(_ -> {
            this.dispose();
            Platform.exit();
            System.exit(0);
        });
    }

    private void setupActionsSubscription() {
        Platform.runLater(() -> {
            subscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
        });
    }

    private void setupToggleAllButton() {
        toggleAllButton.setOnAction(_ -> flow.accept(new Action.SelectAllPacksToggled()));
    }

    private void setupHideOnClick() {
        filterDropdown.parentProperty().addListener((_, _, parent) -> {
            if (parent != null) {
                parent.setOnMouseClicked(event -> {
                    if (!filterDropdown.getBoundsInParent().contains(event.getX(), event.getY())) {
                        flow.accept(new Action.FilterPacksSucceeded());
                    }
                });
            }
        });
    }

    private void render(ViewState state) {
        var filter = state.filter();
        
        if (filter instanceof PacksFilter.Show) {
            filterDropdown.setVisible(true);
            filterDropdown.setManaged(true);
        } else {
            filterDropdown.setVisible(false);
            filterDropdown.setManaged(false);
        }

        if (filterDropdown.isVisible()) {
            var catalog = state.ikonCatalog();
            var allPacksSelected = state.selectedPacks().size() == catalog.orderedPacks().size();
            toggleAllButton.setSelected(allPacksSelected);
            toggleAllButton.setText(allPacksSelected ? "Deselect All" : "Select All");

            populatePacksList(catalog, state.selectedPacks());
            populateStylesList(catalog, state.selectedStyles());
        }
    }

    private void populatePacksList(IkonCatalog catalog, Set<Pack> selectedPacks) {
        packsListVBox.getChildren().clear();
        for (var pack : catalog.orderedPacks()) {
            CheckBox checkBox = new CheckBox(pack.toString());
            checkBox.setSelected(selectedPacks.contains(pack));
            checkBox.getStyleClass().add("pack-checkbox");

            checkBox.setOnAction(_ -> flow.accept(new Action.PackToggled(pack)));

            // Wrap in a Label to make the whole area clickable, like the HTML version
            Label label = new Label(null, checkBox);
            label.getStyleClass().add("pack-label");
            label.setMaxWidth(Double.MAX_VALUE);

            packsListVBox.getChildren().add(label);
        }
    }

    private void populateStylesList(IkonCatalog catalog, Set<Style> selectedStyles) {
        stylesListFlowPane.getChildren().clear();

        for (var style : catalog.orderedStyles()) {
            var button = new ToggleButton(style.displayName());
            button.getStyleClass().add("style-button");
            button.setOnAction(_ -> toggleStyle(style));
            button.setSelected(selectedStyles.contains(style));

            stylesListFlowPane.getChildren().add(button);
        }
    }

    private void toggleStyle(Style style) {
        var action = style instanceof Style.All
                   ? new Action.SelectAllPackStylesToggled()
                   : new Action.PackStyleToggled(style);
        flow.accept(action);
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
