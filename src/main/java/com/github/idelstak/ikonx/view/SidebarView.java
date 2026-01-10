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
import io.reactivex.rxjava3.disposables.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.kordamp.ikonli.javafx.*;
import org.pdfsam.rxjavafx.schedulers.*;

public class SidebarView implements Initializable {

    private final Stage stage;
    private final StateFlow flow;
    private Disposable subscription;
    @FXML
    private TilePane favoritesGrid;
    @FXML
    private VBox historyList;

    public SidebarView(Stage stage, StateFlow flow) {
        this.stage = stage;
        this.flow = flow;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupStage();
        setupActionsSubscription();
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

    private void render(ViewState state) {
        var byDescription = Comparator.comparing(PackIkon::description);
        var favorites = state.favoriteIkons();
        var sortedFavorites = favorites.stream().sorted(byDescription).toList();

        renderFavorites(sortedFavorites);

        var history = state.recentIkons();
        var sortedHistory = history.stream().sorted(byDescription).toList();
        renderHistory(sortedHistory);
    }

    private void renderFavorites(List<PackIkon> favorites) {
        favoritesGrid.getChildren().clear();

        if (favorites.isEmpty()) {
            var placeholder = new Label("No favorites yet");
            placeholder.getStyleClass().add("placeholder-text");
            TilePane.setAlignment(placeholder, Pos.TOP_LEFT);
            favoritesGrid.getChildren().add(placeholder);
            return;
        }

        for (var favorite : favorites) {
            var button = new Button();
            button.getStyleClass().add("favorite-button");
            button.setOnAction(_ -> {
                System.out.println("Show details for: " + favorite.description());
                flow.accept(new Action.ViewIkonDetailsRequested(favorite));
            });

            var tooltip = new Tooltip(favorite.description());
            Tooltip.install(button, tooltip);

            var icon = new StackPane();
            icon.getStyleClass().add("icon-placeholder");
            icon.getChildren().add(new FontIcon(favorite.styledIkon().ikon()));
            button.setGraphic(icon);

            favoritesGrid.getChildren().add(button);
        }
    }

    private void renderHistory(List<PackIkon> history) {
        historyList.getChildren().clear();
        for (var ikon : history) {
            historyList.getChildren().add(createHistoryItemNode(ikon));
        }
    }

    private Node createHistoryItemNode(PackIkon ikon) {
        // Create the main container (Button wrapped in HBox)
        Button historyItem = new Button();
        historyItem.getStyleClass().add("history-item");
        historyItem.setOnAction(_ -> {
            System.out.println("Show details for: " + ikon.description());
            flow.accept(new Action.ViewIkonDetailsRequested(ikon));
        });

        // Icon container
        var iconPlaceholder = new StackPane();
        iconPlaceholder.getStyleClass().add("icon-placeholder");
        iconPlaceholder.getChildren().add(new FontIcon(ikon.styledIkon().ikon()));
        var iconContainer = new StackPane(iconPlaceholder);
        iconContainer.getStyleClass().add("icon-container");

        // Text container
        Label title = new Label(ikon.description());
        title.getStyleClass().add("item-title");

        Label subtitle = new Label(ikon.pack().toString());
        subtitle.getStyleClass().add("item-subtitle");

        VBox textContainer = new VBox(title, subtitle);
        textContainer.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(textContainer, Priority.ALWAYS);

        // Assemble the button content
        HBox buttonContent = new HBox(8, iconContainer, textContainer);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        buttonContent.setMaxWidth(Double.MAX_VALUE);

        historyItem.setGraphic(buttonContent);

        return historyItem;
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
