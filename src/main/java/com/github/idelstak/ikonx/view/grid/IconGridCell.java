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
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.control.skin.*;
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.*;

/**
 * Renders a single PackIkon. Its internal layout is directly controlled
 * by Java code based on the ViewMode, not CSS flex-direction.
 */
public class IconGridCell extends IndexedCell<PackIkon> {

    private final HBox listRoot = new HBox();
    private final VBox gridRoot = new VBox();
    private final FontIcon fontIcon = new FontIcon();
    private final VBox textContainer = new VBox();
    private final Label nameLabel = new Label();
    private final Label packLabel = new Label();
    private final IconGrid iconGrid;

    public IconGridCell(IconGrid iconGrid) {
        this.iconGrid = iconGrid;

        getStyleClass().add("icon-grid-cell");
        fontIcon.getStyleClass().add("icon-font");
        textContainer.getStyleClass().add("text-container");
        nameLabel.getStyleClass().add("name-label");
        packLabel.getStyleClass().add("pack-label");

        gridRoot.getStyleClass().add("grid-root");
        listRoot.getStyleClass().add("list-root");

        textContainer.getChildren().addAll(nameLabel, packLabel);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CellSkinBase<>(this);
    }

    @Override
    protected void updateItem(PackIkon item, boolean empty) {
        super.updateItem(item, empty);

        System.out.println("[ICON GRID CELL] updateItem = " + item);

        if (empty || item == null) {
            setGraphic(null);
            getStyleClass().removeAll("grid-mode", "list-mode");
            return;
        }

        fontIcon.setIconCode(item.styledIkon().ikon());
        nameLabel.setText(item.styledIkon().ikon().getDescription());
        packLabel.setText(item.pack().toString().toUpperCase(Locale.ROOT));

        gridRoot.getChildren().clear();
        listRoot.getChildren().clear();
        getStyleClass().removeAll("grid-mode", "list-mode");

        switch (iconGrid.getViewMode()) {
            case ViewMode.Grid _ -> {
                getStyleClass().add("grid-mode");
                gridRoot.getChildren().addAll(fontIcon, textContainer);
                setGraphic(gridRoot);
            }
            case ViewMode.List _ -> {
                getStyleClass().add("list-mode");
                listRoot.getChildren().addAll(fontIcon, textContainer);
                setGraphic(listRoot);
            }
        }
    }
}
