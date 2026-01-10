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
import com.github.idelstak.ikonx.mvu.state.view.*;
import io.reactivex.rxjava3.disposables.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.pdfsam.rxjavafx.schedulers.*;

public class OverlayView implements Initializable {

    private final Stage stage;
    private final Flow flow;
    private Disposable subscription;
    @FXML
    private Pane overlayPane;

    public OverlayView(Stage stage, Flow flow) {
        this.stage = stage;
        this.flow = flow;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupStage();
        setupActionsSubscription();
        setupOverlayAction();
    }

    private void setupStage() {
        stage.setOnCloseRequest(_ -> this.dispose());
    }

    private void setupActionsSubscription() {
        Platform.runLater(() -> {
            subscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
        });
    }

    private void setupOverlayAction() {
        overlayPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                flow.accept(new Action.HideIkonDetailsRequested());
            }
        });
    }

    private void render(ViewState state) {
        var detailsDisplay = state.detailsDisplay();

        switch (detailsDisplay) {
            case IkonDetailsDisplay.ShowRequested _ -> {
                overlayPane.setVisible(true);
                overlayPane.setManaged(true);
            }
            case IkonDetailsDisplay.HideRequested _ -> {
                overlayPane.setVisible(false);
                overlayPane.setManaged(false);
            }
            case IkonDetailsDisplay.Failed _ -> {
            }
        }
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
