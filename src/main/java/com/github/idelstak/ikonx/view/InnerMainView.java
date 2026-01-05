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
package com.github.idelstak.ikonx.view;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.net.*;
import java.util.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.layout.*;

public class InnerMainView implements Initializable {

    @FXML
    private BorderPane innerMainLayout;
    private IconGrid iconGrid;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("[INNER MAIN VIEW] init...");

        setupIconGrid();

        Platform.runLater(() -> {
            var allIkons = Arrays.stream(Pack.values())
              .flatMap(p -> Arrays.stream(p.getIkons()).map(i -> new PackIkon(p, i)))
              .toList();
            var icons = FXCollections.observableArrayList(allIkons);
            System.out.println("[INNER MAIN VIEW] icons count = " + icons.size());
            iconGrid.setItems(icons.sorted());
            innerMainLayout.setCenter(iconGrid);
        });
    }

    private void setupIconGrid() {
        iconGrid = new IconGrid();
        iconGrid.setCellFactory(IconGridCell::new);
        iconGrid.setCellWidth(220);
        iconGrid.setCellHeight(120);
        iconGrid.setListRowHeight(52);
    }
}
