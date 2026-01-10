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
import com.github.idelstak.ikonx.mvu.state.*;
import io.reactivex.rxjava3.disposables.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import org.pdfsam.rxjavafx.schedulers.*;

public class FooterView implements Initializable {

    private final Stage stage;
    private final Flow flow;
    private Disposable subscription;
    @FXML
    private Circle statusIndicator;
    @FXML
    private Label statusLabel;
    @FXML
    private Label iconsCountLabel;

    public FooterView(Stage stage, Flow flow) {
        this.stage = stage;
        this.flow = flow;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupStage();
        setupActionsSubscription();
    }

    private void setupStage() {
        stage.setOnCloseRequest(_ -> this.dispose());
    }

    private void setupActionsSubscription() {
        Platform.runLater(() -> {
            subscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
        });
    }

    private void render(ViewState state) {
        var activityState = state.status();
        statusIndicator.setVisible(!(activityState instanceof ActivityState.Idle));
        statusIndicator.getStyleClass().removeAll("loading", "success", "error");
        statusIndicator.getStyleClass().add(switch (activityState) {
            case ActivityState.Idle _ ->
                "";
            case ActivityState.Loading _ ->
                "loading";
            case ActivityState.Success _ ->
                "success";
            case ActivityState.Error _ ->
                "error";
        });

        var message = state.statusMessage();
        statusLabel.setText(message);

        var iconsCount = state.displayedIkons().size();
        iconsCountLabel.setText("%d icons available".formatted(iconsCount));
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
