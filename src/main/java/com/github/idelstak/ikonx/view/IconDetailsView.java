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
import javafx.animation.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;
import org.kordamp.ikonli.javafx.*;
import org.pdfsam.rxjavafx.schedulers.*;

public class IconDetailsView implements Initializable {

    private final Stage stage;
    private final Flow flow;
    private Disposable subscription;
    private PackIkon currentIkon;
    @FXML
    private VBox detailsRoot;
    @FXML
    private Button closeButton;
    @FXML
    private FontIcon icon;
    @FXML
    private Label title;
    @FXML
    private Label subtitle;
    @FXML
    private Label packLabel;
    @FXML
    private Label licenseLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Button copyButton;

    public IconDetailsView(Stage stage, Flow flow) {
        this.stage = stage;
        this.flow = flow;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupInitialPosition();
        setupStage();
        setupActionsSubscription();
        setupCopyButton();
        setupCloseButton();
    }

    private void setupInitialPosition() {
        detailsRoot.setVisible(false);
        detailsRoot.setTranslateX(detailsRoot.getPrefWidth());
    }

    private void setupStage() {
        stage.setOnCloseRequest(_ -> this.dispose());
    }

    private void setupActionsSubscription() {
        Platform.runLater(() -> {
            subscription = flow.observe().observeOn(JavaFxScheduler.platform()).subscribe(this::render);
        });
    }

    private void setupCopyButton() {
        copyButton.setOnAction(_ -> {
            if (currentIkon != null) {
                flow.accept(new Action.CopyIkonRequested(currentIkon));
            }
            flow.accept(new Action.HideIkonDetailsRequested());
        });
    }

    private void setupCloseButton() {
        closeButton.setOnAction(_ -> {
            flow.accept(new Action.HideIkonDetailsRequested());
        });
    }

    private void render(ViewState state) {
        var detailsDisplay = state.detailsDisplay();

        Optional<PackIkon> maybeIkon = switch (detailsDisplay) {
            case IkonDetailsDisplay.ShowRequested show ->
                Optional.of(show.ikon());
            case IkonDetailsDisplay.HideRequested _ ->
                Optional.empty();
            case IkonDetailsDisplay.Failed _ ->
                Optional.empty();
        };

        maybeIkon.ifPresentOrElse(this::show, this::slideOut);
    }

    private void show(PackIkon ikon) {
        currentIkon = ikon;

        title.setText(ikon.description());
        subtitle.setText(ikon.styledIkon().style().displayName());
        packLabel.setText(ikon.pack().toString().toUpperCase(Locale.ROOT));
        licenseLabel.setText("Apache");
        authorLabel.setText("Team Kordamp");
        icon.setIconCode(ikon.styledIkon().ikon());

        slideIn();
    }

    private void slideIn() {
        detailsRoot.setVisible(true);

        var animation = new TranslateTransition(Duration.millis(320), detailsRoot);
        animation.setFromX(detailsRoot.getWidth());
        animation.setToX(0);
        animation.play();
    }

    private void slideOut() {
        var animation = new TranslateTransition(Duration.millis(180), detailsRoot);
        animation.setFromX(0);
        animation.setToX(detailsRoot.getWidth());
        animation.setOnFinished(_ -> detailsRoot.setVisible(false));
        animation.play();
    }

    private void dispose() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
