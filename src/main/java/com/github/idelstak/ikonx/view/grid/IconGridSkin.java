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
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.skin.*;

final class IconGridSkin extends SkinBase<IconGrid> {

    private final GridVirtualFlow virtualFlow;
    private final ListChangeListener<PackIkon> itemsListener;
    private final WeakListChangeListener<PackIkon> weakItemsListener;
    private int columnCount = 1;

    IconGridSkin(IconGrid control) {
        super(control);

        virtualFlow = new GridVirtualFlow();
        virtualFlow.setCellFactory(_ -> new IconGridRow(control));
        getChildren().add(virtualFlow);

        itemsListener = _ -> updateItemCount();
        weakItemsListener = new WeakListChangeListener<>(itemsListener);

        if (control.getItems() != null) {
            control.getItems().addListener(weakItemsListener);
        }

        registerChangeListener(control.itemsProperty(), _ -> {
            if (Objects.nonNull(control.getItems())) {
                control.getItems().addListener(weakItemsListener);
            }
            updateItemCount();
        });

        registerChangeListener(control.widthProperty(), _ -> updateItemCount());
        registerChangeListener(control.viewModeProperty(), _ -> updateItemCount());
        registerChangeListener(control.cellWidthProperty(), _ -> updateItemCount());
        registerChangeListener(control.cellHeightProperty(), _ -> updateItemCount());
        registerChangeListener(control.horizontalGapProperty(), _ -> updateItemCount());
        registerChangeListener(control.verticalGapProperty(), _ -> updateItemCount());
        registerChangeListener(control.listRowHeightProperty(), _ -> updateItemCount());
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        virtualFlow.resizeRelocate(x, y, w, h);
    }

    int getColumnCount() {
        return columnCount;
    }

    double getHorizontalGap() {
        return getSkinnable().getHorizontalGap();
    }

    private void updateItemCount() {
        IconGrid grid = getSkinnable();
        if (grid.getItems() == null || grid.getItems().isEmpty()) {
            virtualFlow.setCellCount(0);
            return;
        }

        double availableWidth = grid.getWidth() - grid.getInsets().getLeft() - grid.getInsets().getRight();
        columnCount = switch (grid.getViewMode()) {
            case ViewMode.Grid _ ->
                Math.max(
                1,
                (int) Math.floor((availableWidth + grid.getHorizontalGap()) / (grid.getCellWidth() + grid.getHorizontalGap()))
                );
            case ViewMode.List _ ->
                1;
        };

        int rowCount = (int) Math.ceil((double) grid.getItems().size() / columnCount);

        double offset = 4;
        double verticalGap = grid.getVerticalGap();
        double rowHeight = switch (grid.getViewMode()) {
            case ViewMode.Grid _ ->
                grid.getCellHeight() + verticalGap;
            case ViewMode.List _ ->
                grid.getListRowHeight() + verticalGap + offset;
        };

        virtualFlow.setFixedCellSize(rowHeight);
        virtualFlow.setCellCount(rowCount);
        virtualFlow.requestLayout();
    }

    private class GridVirtualFlow extends VirtualFlow<IconGridRow> {

        @Override
        public void recreateCells() {
            super.recreateCells();
        }

        @Override
        public void rebuildCells() {
            super.rebuildCells();
        }

        @Override
        public void reconfigureCells() {
            super.reconfigureCells();
        }
    }
}
