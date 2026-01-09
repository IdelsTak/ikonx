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
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.skin.*;
import javafx.scene.layout.*;

public final class IconGridRow extends IndexedCell<Integer> {

    private final IconGrid iconGrid;
    private final HBox root = new HBox();
    private final List<IkonGridCell> cells = new ArrayList<>();

    IconGridRow(IconGrid iconGrid) {
        this.iconGrid = iconGrid;
        setGraphic(root);
        getStyleClass().add("icon-grid-row");
    }

    @Override
    public void updateIndex(int i) {
        super.updateIndex(i); // must call super
        updateItem(i, i < 0 || i >= iconGrid.getItems().size());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CellSkinBase<>(this);
    }

    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            root.getChildren().clear();
            return;
        }

        root.getChildren().clear();
        IconGridSkin skin = (IconGridSkin) iconGrid.getSkin();
        root.setSpacing(skin.getHorizontalGap());
        root.setPadding(new Insets(
          (iconGrid.getVerticalGap() / 2),
          (iconGrid.getHorizontalGap() / 2),
          (iconGrid.getVerticalGap() / 2),
          iconGrid.getHorizontalGap() / 2
        ));

        int rowIndex = getIndex();
        int columnCount = skin.getColumnCount();
        int startIndex = rowIndex * columnCount;

        for (int i = 0; i < columnCount; i++) {
            var cell = (i >= cells.size()) ? iconGrid.getCellFactory().call(iconGrid)
                     : cells.get(i);
            if (i >= cells.size()) {
                cells.add(cell);
            }

            int itemIndex = startIndex + i;
            if (itemIndex < iconGrid.getItems().size()) {
                PackIkon ikon = iconGrid.getItems().get(itemIndex);
                cell.updateItem(ikon, false);
                cell.setVisible(true);

                switch (iconGrid.getViewMode()) {
                    case ViewMode.Grid _ -> {
                        cell.setMinSize(iconGrid.getCellWidth(), iconGrid.getCellHeight());
                        cell.setPrefSize(iconGrid.getCellWidth(), iconGrid.getCellHeight());
                        cell.setMaxSize(iconGrid.getCellWidth(), iconGrid.getCellHeight());
                    }
                    case ViewMode.List _ -> {
                        cell.setMinSize(iconGrid.getCellWidth(), iconGrid.getListRowHeight());
                        cell.setPrefSize(iconGrid.getWidth() * 0.95, iconGrid.getListRowHeight());
                        cell.setMaxSize(iconGrid.getWidth() * 0.95, iconGrid.getListRowHeight());
                    }
                }

                root.getChildren().add(cell);
            }
        }
    }
}
