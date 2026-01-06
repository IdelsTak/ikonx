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

import com.github.idelstak.ikonx.icons.PackIkon;
import com.github.idelstak.ikonx.mvu.action.Action;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import org.kordamp.ikonli.javafx.FontIcon;
import java.util.List;
import java.util.function.Consumer;

final class FontIconCell extends TableCell<List<PackIkon>, PackIkon> {

    private final Consumer<Action> dispatch;
    private final Label root;
    private final FontIcon fontIcon;

    FontIconCell(Consumer<Action> dispatch) {
        super();
        this.dispatch = dispatch;

        root = new Label();
        fontIcon = new FontIcon();

        root.setContentDisplay(ContentDisplay.TOP);
        root.setGraphic(fontIcon);
        root.setGraphicTextGap(10);
        root.getStyleClass().addAll("icon-label", "text-small");
        fontIcon.iconColorProperty().bind(root.textFillProperty());
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append('{');
        sb.append("root=").append(root.getText());
        sb.append(", fontIcon=").append(fontIcon.getIconLiteral());
        sb.append('}');
        return fontIcon.getIconLiteral();
    }

    @Override
    protected void updateItem(PackIkon packIkon, boolean empty) {
        super.updateItem(packIkon, empty);

        if (packIkon == null || empty) {
            setGraphic(null);
        } else {
            root.setText(packIkon.styledIkon().ikon().getDescription());
            fontIcon.setIconCode(packIkon.styledIkon().ikon());

            root.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    dispatch.accept(new Action.CopyIconRequested(packIkon.styledIkon().ikon().getDescription()));
                }
            });

            var contextMenu = new ContextMenu();
            var copyItem = new MenuItem("Copy icon code");
            copyItem.setOnAction(_ ->
              dispatch.accept(new Action.CopyIconRequested(packIkon.styledIkon().ikon().getDescription())));
            contextMenu.getItems().add(copyItem);
            root.setContextMenu(contextMenu);

            setGraphic(root);
        }
    }
}
