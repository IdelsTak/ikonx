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

import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import org.controlsfx.control.*;

public class MockIconView implements IconView {

    private String searchText = "";
    private ObservableList<Pack> selectedPacks = FXCollections.observableArrayList();
    private ObservableList<Pack> packs = FXCollections.observableArrayList();
    private TableView<List<PackIkon>> iconsTable = new TableView<>();
    private CheckComboBox<Pack> packCombo = new CheckComboBox<>();
    private ToggleButton selectAllToggle = new ToggleButton();

    @Override
    public String getSearchText() {
        return searchText;
    }

    @Override
    public ObservableList<Pack> getSelectedPacks() {
        return selectedPacks;
    }

    @Override
    public void setSearchText(String text) {
        this.searchText = text;
    }

    @Override
    public void selectAllPacks() {
        selectedPacks.setAll(packs);
    }

    @Override
    public void deselectAllPacks() {
        selectedPacks.clear();
    }

    @Override
    public void setPacks(List<Pack> packs) {
        this.packs.setAll(packs);
    }

    @Override
    public void setTableItems(Collection<List<PackIkon>> items) {
        iconsTable.getItems().setAll(items);
    }

    @Override
    public TableView<List<PackIkon>> getIconsTable() {
        return iconsTable;
    }

    @Override
    public CheckComboBox<Pack> getPackComboBox() {
        return packCombo;
    }

    @Override
    public ToggleButton getSelectAllToggle() {
        return selectAllToggle;
    }
}
