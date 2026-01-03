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

import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.view.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class Ikonx extends Application {

    private IconView controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/fxml/icon-view.fxml"));
        loader.setControllerFactory(param -> {
            controller = new IconView(new StateFlow());
            return controller;
        });
        var root = loader.<Parent>load();
        var scene = new Scene(root);

        primaryStage.setOnCloseRequest(_ -> {
            if (controller != null) {
                controller.dispose();
            }
            Platform.exit();
            System.exit(0); // ensures all threads die
        });

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle("IkonX - for ikonli v. 12.4.0");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
