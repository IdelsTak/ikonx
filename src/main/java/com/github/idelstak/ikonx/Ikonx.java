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
package com.github.idelstak.ikonx;

import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.view.*;
import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class Ikonx extends Application {

    private AppMeta meta;

    public Ikonx() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/fxml/icon-view.fxml"));
        loader.setControllerFactory(_ -> new IconView(new StateFlow(new IconClipboard(), meta)));
        // var loader = new FXMLLoader(getClass().getResource("/fxml/ikonx-view.fxml"));
        // loader.setControllerFactory(_ -> new IkonxView());

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
        var initialMeta = AppMeta.empty();
        var props = properties();
        initialMeta = version(initialMeta, props, "version");
        initialMeta = ikonli(initialMeta, props, "ikonli.version");
        return initialMeta.icons(icons());
    }

    private AppMeta version(AppMeta meta, Properties props, String key) {
        var value = props.getProperty(key);
        return value == null
                 ? meta.withoutAppVersion()
                 : meta.appVersion(value);
    }

    private AppMeta ikonli(AppMeta meta, Properties props, String key) {
        var value = props.getProperty(key);
        return value == null
                 ? meta.withoutIkonliVersion()
                 : meta.ikonliVersion(value);
    }

    private Properties properties() {
        var props = new Properties();
        try (var stream = getClass().getResourceAsStream("/version.properties")) {
            if (stream != null) {
                props.load(stream);
            }
        } catch (IOException ignored) {
        }
        return props;
    }

    private List<Image> icons() {
        return List.of(
          icon("/logos/logo16.png"),
          icon("/logos/logo32.png"),
          icon("/logos/logo48.png"),
          icon("/logos/logo64.png"),
          icon("/logos/logo128.png")
        ).stream().flatMap(Optional::stream).toList();
    }

    private Optional<Image> icon(String path) {
        try (var stream = getClass().getResourceAsStream(path)) {
            if (stream != null) {
                return Optional.of(new Image(stream));
            }
        } catch (IOException ignored) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
