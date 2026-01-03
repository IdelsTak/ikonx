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
import java.util.*;
import javafx.scene.control.*;

final class TableSelectionFix {

    private final TableView<List<PackIkon>> table;

    TableSelectionFix(TableView<List<PackIkon>> table) {
        this.table = table;
    }

    void render(Runnable itemsUpdate) {
        var sm = table.getSelectionModel();
        var selected = sm.getSelectedCells().isEmpty() ? null : sm.getSelectedCells().get(0);
        
        itemsUpdate.run();
        
        if (selected != null) {
            var row = selected.getRow();
            var col = selected.getColumn();
            if (row < table.getItems().size() && col < table.getColumns().size()) {
                sm.clearSelection();
                sm.select(row, table.getColumns().get(col));
            }
        }
    }
}
