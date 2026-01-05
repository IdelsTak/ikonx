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
package com.github.idelstak.ikonx.view.grid;

import com.github.idelstak.ikonx.icons.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.util.*;

/**
 * A virtualized grid control for displaying PackIkon instances.
 * Horizontal and vertical gaps are now configurable properties.
 */
public final class IconGrid extends Control {

    private final ObjectProperty<ObservableList<PackIkon>> items = new SimpleObjectProperty<>(this, "items", FXCollections.observableArrayList());
    private final ObjectProperty<Callback<IconGrid, IconGridCell>> cellFactory = new SimpleObjectProperty<>(this, "cellFactory");
    private final DoubleProperty cellWidth = new SimpleDoubleProperty(this, "cellWidth", 120);
    private final DoubleProperty cellHeight = new SimpleDoubleProperty(this, "cellHeight", 110);
    private final DoubleProperty listRowHeight = new SimpleDoubleProperty(this, "listRowHeight", 52);
    private final ObjectProperty<ViewMode> viewMode = new SimpleObjectProperty<>(this, "viewMode", ViewMode.GRID);
    private final DoubleProperty horizontalGap = new SimpleDoubleProperty(this, "horizontalGap", 12);
    private final DoubleProperty verticalGap = new SimpleDoubleProperty(this, "verticalGap", 12);

    public IconGrid() {
        getStyleClass().add("icon-grid");
    }

    // --- Item Properties ---
    public ObjectProperty<ObservableList<PackIkon>> itemsProperty() {
        return items;
    }

    public void setItems(ObservableList<PackIkon> value) {
        items.set(value);
    }

    public ObservableList<PackIkon> getItems() {
        return items.get();
    }

    // --- Cell Factory ---
    public ObjectProperty<Callback<IconGrid, IconGridCell>> cellFactoryProperty() {
        return cellFactory;
    }

    public void setCellFactory(Callback<IconGrid, IconGridCell> value) {
        cellFactory.set(value);
    }

    public Callback<IconGrid, IconGridCell> getCellFactory() {
        return cellFactory.get();
    }

    // --- Cell Dimensions ---
    public DoubleProperty cellWidthProperty() {
        return cellWidth;
    }

    public double getCellWidth() {
        return cellWidth.get();
    }

    public void setCellWidth(double width) {
        cellWidth.set(width);
    }

    public DoubleProperty cellHeightProperty() {
        return cellHeight;
    }

    public double getCellHeight() {
        return cellHeight.get();
    }

    public void setCellHeight(double height) {
        cellHeight.set(height);
    }

    public DoubleProperty listRowHeightProperty() {
        return listRowHeight;
    }

    public double getListRowHeight() {
        return listRowHeight.get();
    }

    public void setListRowHeight(double height) {
        listRowHeight.set(height);
    }

    // --- View Mode ---
    public ObjectProperty<ViewMode> viewModeProperty() {
        return viewMode;
    }

    public void setViewMode(ViewMode value) {
        viewMode.set(value);
    }

    public ViewMode getViewMode() {
        return viewMode.get();
    }

    // --- Gap Properties ---
    public DoubleProperty horizontalGapProperty() {
        return horizontalGap;
    }

    public double getHorizontalGap() {
        return horizontalGap.get();
    }

    public void setHorizontalGap(double gap) {
        horizontalGap.set(gap);
    }

    public DoubleProperty verticalGapProperty() {
        return verticalGap;
    }

    public double getVerticalGap() {
        return verticalGap.get();
    }

    public void setVerticalGap(double gap) {
        verticalGap.set(gap);
    }
    private String stylesheet;

    @Override
    public String getUserAgentStylesheet() {
        if (stylesheet == null) {
            stylesheet = getClass().getResource("/styles/icon-grid.css").toExternalForm();
        }

        return stylesheet;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new IconGridSkin(this);
    }

    public enum ViewMode {
        GRID,
        LIST
    }
}
