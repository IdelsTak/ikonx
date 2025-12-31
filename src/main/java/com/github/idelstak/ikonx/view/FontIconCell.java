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
import com.github.idelstak.ikonx.mvu.action.*;
import java.util.*;
import java.util.function.*;
import javafx.scene.control.*;
import org.kordamp.ikonli.javafx.*;

final class FontIconCell extends TableCell<List<PackIkon>, PackIkon> {

    private final Label root = new Label();
    private final FontIcon fontIcon = new FontIcon();
    private final Consumer<Action> dispatch;

    FontIconCell(Consumer<Action> dispatch) {
        super();
        this.dispatch = dispatch;

        root.setContentDisplay(ContentDisplay.TOP);
        root.setGraphic(fontIcon);
        root.setGraphicTextGap(10);
        root.getStyleClass().addAll("icon-label", "text-small");

        fontIcon.iconColorProperty().bind(root.textFillProperty());

        root.setOnMouseClicked(event -> {
            var currentItem = getItem();
            if (event.getClickCount() == 1 && currentItem != null) {
                dispatch.accept(new Action.IconCopied(currentItem.ikon().getDescription()));
            }
        });
    }

    @Override
    protected void updateItem(PackIkon packIkon, boolean empty) {
        super.updateItem(packIkon, empty);

        if (packIkon == null || empty) {
            setGraphic(null);
        } else {
            var contextMenu = new ContextMenu();
            var copyItem = new MenuItem("Copy icon code");
            copyItem.setOnAction(_ ->
            {
                dispatch.accept(new Action.IconCopied(packIkon.ikon().getDescription()));
            });
            contextMenu.getItems().add(copyItem);

            root.setContextMenu(contextMenu);
            root.setText(packIkon.ikon().getDescription());
            fontIcon.setIconCode(packIkon.ikon());
            setGraphic(root);
        }

    }
//    private void copyIconCodeToClipboard() {
//        String iconCode = fontIcon.getIconCode().getDescription();
//
//        if (iconCode != null && !iconCode.isEmpty()) {
//            ClipboardContent content = new ClipboardContent();
//            content.putString(iconCode);
//            Clipboard.getSystemClipboard().setContent(content);
//        }
//    }
}
