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
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.view.*;
import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class Ikonx extends Application {

    private AppMeta meta;

    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/fxml/icon-view.fxml"));
        loader.setControllerFactory(_ -> new IconView(new StateFlow(new IconClipboard(), meta)));
        var root = loader.<Parent>load();
        var scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        meta = meta();
    }

    private AppMeta meta() {
        try (var is = getClass().getResourceAsStream("/version.properties")) {
            if (is == null) {
                return null;
            }
            var p = new Properties();
            p.load(is);
            var app = p.getProperty("version", null);
            var ikonli = p.getProperty("ikonli.version", null);
            return new AppMeta(Optional.ofNullable(app), Optional.ofNullable(ikonli));
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
