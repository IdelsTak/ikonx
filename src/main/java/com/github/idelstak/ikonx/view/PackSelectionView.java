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
package com.github.idelstak.ikonx.view;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.action.*;
import java.util.*;
import java.util.function.*;
import javafx.collections.*;
import org.controlsfx.control.*;

final class PackSelectionView {

    private final CheckComboBox<Pack> combo;
    private final ListChangeListener<Pack> listener;

    PackSelectionView(CheckComboBox<Pack> combo, Consumer<Action> actions) {
        this.combo = combo;
        this.listener = change -> {
            while (change.next()) {
                for (var pack : change.getRemoved()) {
                    actions.accept(new Action.PackToggled(pack, false));
                }
                for (var pack : change.getAddedSubList()) {
                    actions.accept(new Action.PackToggled(pack, true));
                }
            }
        };
        combo.getCheckModel().getCheckedItems().addListener(listener);
    }

    void render(Set<Pack> selected) {
        var model = combo.getCheckModel();
        model.getCheckedItems().removeListener(listener);
        try {
            for (var pack : combo.getItems()) {
                if (selected.contains(pack)) {
                    model.check(pack);
                } else {
                    model.clearCheck(pack);
                }
            }
        } finally {
            model.getCheckedItems().addListener(listener);
        }
    }
}
