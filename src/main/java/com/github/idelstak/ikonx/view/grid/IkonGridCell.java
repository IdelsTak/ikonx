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
import com.github.idelstak.ikonx.mvu.*;
import javafx.scene.control.*;
import javafx.scene.control.skin.*;
import javafx.stage.*;

public final class IkonGridCell extends Cell<PackIkon> {

    private final IconGrid grid;
    private final Stage stage;
    private final Flow flow;
    private CellPane root;

    public IkonGridCell(IconGrid grid, Stage stage, Flow flow) {
        this.grid = grid;
        this.stage = stage;
        this.flow = flow;

        super.getStyleClass().add("grid-cell");

        root = new CellPane(stage, flow);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CellSkinBase<>(this);
    }

    @Override
    protected void updateItem(PackIkon item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setGraphic(null);
            super.setText(null);
            return;
        }

        root.renderIkon(item);
        setGraphic(root);
    }
}
