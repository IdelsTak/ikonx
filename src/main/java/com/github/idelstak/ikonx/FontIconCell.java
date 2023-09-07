/*
 * MIT License
 *
 * Copyright (c) 2023 Hiram K
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

import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

class FontIconCell extends TableCell<List<PackIkon>, PackIkon> {

    private final Label root = new Label();
    private final FontIcon fontIcon = new FontIcon();

    public FontIconCell() {
        super();

        root.setContentDisplay(ContentDisplay.TOP);
        root.setGraphic(fontIcon);
        root.setGraphicTextGap(10);
        root.getStyleClass().addAll("icon-label", "text-small");

        fontIcon.iconColorProperty().bind(root.textFillProperty());

        root.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                copyIconCodeToClipboard();
            }
        });
    }

    @Override
    protected void updateItem(PackIkon packIkon, boolean empty) {
        super.updateItem(packIkon, empty);

        if (packIkon == null) {
            setGraphic(null);
            return;
        }

        ContextMenu contextMenu = new ContextMenu();
        MenuItem copyItem = new MenuItem("Copy icon code");
        copyItem.setOnAction(event -> copyIconCodeToClipboard());
        contextMenu.getItems().add(copyItem);
        root.setContextMenu(contextMenu);

        root.setText(packIkon.ikon().getDescription());
        fontIcon.setIconCode(packIkon.ikon());
        setGraphic(root);
    }

    private void copyIconCodeToClipboard() {
        String iconCode = fontIcon.getIconCode().getDescription();

        if (iconCode != null && !iconCode.isEmpty()) {
            ClipboardContent content = new ClipboardContent();
            content.putString(iconCode);
            Clipboard.getSystemClipboard().setContent(content);
        }
    }
}