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
package com.github.idelstak.ikonx.view.grid;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import io.reactivex.rxjava3.disposables.*;
import java.util.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.kordamp.ikonli.javafx.*;
import org.pdfsam.rxjavafx.schedulers.*;

final class CellPane extends StackPane {

    private final Stage stage;
    private final Flow flow;
    private Disposable actionsSubscription;
    private Disposable stateSubscription;
    private PackIkon currentIkon;
    private ViewState currentState;
    private final BorderPane content = new BorderPane();
    private final StackPane iconWrapper = new StackPane();
    private final FontIcon icon = new FontIcon();
    private final VBox textWrapper = new VBox();
    private final Label iconName = new Label();
    private final Label iconPack = new Label();
    private final Button favorite = new Button();
    private final Region favoriteIcon = new Region();
    private final Button details = new Button();

    CellPane(Stage stage, Flow flow) {
        this.stage = stage;
        this.flow = flow;

        initComponents();
        setupStage();
        setupActionsSubscription();
        setupCopyAction();
        setupFavoriteAction();
    }

    void renderIkon(PackIkon ikon) {
        currentIkon = ikon;

        iconName.setText(ikon.description());
        iconPack.setText(ikon.pack().toString().toUpperCase(Locale.ROOT));
        icon.setIconCode(ikon.styledIkon().ikon());

        render(currentState);
    }

    private void initComponents() {
        content.getStyleClass().add("content-pane");

        iconWrapper.getStyleClass().add("icon-wrapper");
        icon.getStyleClass().add("icon-placeholder");
        iconWrapper.getChildren().add(icon);

        textWrapper.getStyleClass().add("text-wrapper");
        iconName.getStyleClass().add("icon-name");
        iconPack.getStyleClass().add("icon-pack");
        textWrapper.getChildren().addAll(iconName, iconPack);

        content.setCenter(iconWrapper);
        content.setBottom(textWrapper);

        favorite.getStyleClass().add("action-button");
        favoriteIcon.getStyleClass().add("favorite-icon-unselected");
        favorite.setGraphic(favoriteIcon);
        StackPane.setAlignment(favorite, Pos.TOP_RIGHT);
        StackPane.setMargin(favorite, new Insets(8, 8, 0, 0));

        details.getStyleClass().add("action-button");
        details.setGraphic(new Region() {
            {
                getStyleClass().add("details-icon");
            }
        });
        StackPane.setAlignment(details, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(details, new Insets(0, 8, 8, 0));

        getChildren().addAll(content, favorite, details);
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
            actionsSubscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
            stateSubscription = flow.observe().subscribe(state -> currentState = state);
        });
    }

    private void setupCopyAction() {
        super.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && currentIkon != null) {
                flow.accept(new Action.CopyIkonRequested(currentIkon));
            }
        });
    }

    private void setupFavoriteAction() {
        favorite.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            event.consume();
            if (currentIkon == null) {
                return;
            }
            flow.accept(new Action.FavoriteIkonToggled(currentIkon));
        });
    }

    private void dispose() {
        if (actionsSubscription != null && !actionsSubscription.isDisposed()) {
            actionsSubscription.dispose();
        }
        if (stateSubscription != null && !stateSubscription.isDisposed()) {
            stateSubscription.dispose();
        }
    }

    private void render(ViewState state) {
        if (currentIkon == null || state == null) {
            return;
        }

        renderFavorite(state.favoriteIkons());
        renderViewMode(state.viewMode());
    }

    private void renderFavorite(Set<PackIkon> favoriteIkons) {
        var isFavorite = currentIkon != null && favoriteIkons.contains(currentIkon);

        favorite.getStyleClass().remove("favorite-selected");
        favoriteIcon.getStyleClass().removeAll("favorite-icon-selected", "favorite-icon-unselected");

        if (isFavorite) {
            favorite.getStyleClass().add("favorite-selected");
            favoriteIcon.getStyleClass().add("favorite-icon-selected");
        } else {
            favoriteIcon.getStyleClass().add("favorite-icon-unselected");
        }
    }

    private void renderViewMode(ViewMode mode) {
        content.getStyleClass().remove("list-view");
        iconWrapper.getStyleClass().remove("list-view");
        textWrapper.getStyleClass().remove("list-view");

        content.getChildren().clear();

        if (mode instanceof ViewMode.List) {
            content.getStyleClass().add("list-view");
            iconWrapper.getStyleClass().add("list-view");
            textWrapper.getStyleClass().add("list-view");

            content.setLeft(iconWrapper);
            content.setCenter(textWrapper);
        } else {
            content.setCenter(iconWrapper);
            content.setBottom(textWrapper);
        }
    }
}
