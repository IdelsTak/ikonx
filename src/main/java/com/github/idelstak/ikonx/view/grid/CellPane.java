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
import javafx.scene.layout.*;
import org.kordamp.ikonli.javafx.*;

final class CellPane extends StackPane {

    private final BorderPane content;
    private final StackPane iconWrapper;
    private final FontIcon icon;
    private final VBox textWrapper;
    private final Label iconName;
    private final Label iconPack;
    private final Button favorite;
    private final Region favoriteIcon;
    private final Button details;

    CellPane() {
        this.content = new BorderPane();
        this.iconWrapper = new StackPane();
        this.icon = new FontIcon();
        this.textWrapper = new VBox();
        this.iconName = new Label();
        this.iconPack = new Label();
        this.favorite = new Button();
        this.favoriteIcon = new Region();
        this.details = new Button();

        content.getStyleClass().add("content-pane");

        iconWrapper.getStyleClass().add("icon-wrapper");
        icon.getStyleClass().add("icon-placeholder");
        iconWrapper.getChildren().add(icon);

        textWrapper.getStyleClass().add("text-wrapper");
        iconName.getStyleClass().add("icon-name");
        iconPack.getStyleClass().add("icon-pack");
        textWrapper.getChildren().addAll(iconName, iconPack);

        content.setCenter(iconWrapper);
        content.setBottom(textWrapper);

        favorite.getStyleClass().add("action-button");
        favoriteIcon.getStyleClass().add("favorite-icon-unselected");
        favorite.setGraphic(favoriteIcon);
        StackPane.setAlignment(favorite, Pos.TOP_RIGHT);
        StackPane.setMargin(favorite, new Insets(8));

        details.getStyleClass().add("action-button");
        details.setGraphic(new Region() {
            {
                getStyleClass().add("details-icon");
            }
        });
        StackPane.setAlignment(details, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(details, new Insets(0, 8, 8, 0));

        getChildren().addAll(content, favorite, details);
    }

    void setIkon(PackIkon ikon) {
        iconName.setText(ikon.description());
        iconPack.setText(ikon.pack().toString().toUpperCase(Locale.ROOT));
        icon.setIconLiteral(ikon.description());
    }
    
    void setViewMode(ViewMode mode) {
        content.getStyleClass().remove("list-view");
        iconWrapper.getStyleClass().remove("list-view");
        textWrapper.getStyleClass().remove("list-view");

        content.getChildren().clear();

        if (mode instanceof ViewMode.List) {
            // LIST MODE: Icon on left, text in center, aligned left
            content.getStyleClass().add("list-view");
            iconWrapper.getStyleClass().add("list-view");
            textWrapper.getStyleClass().add("list-view");

            content.setLeft(iconWrapper);
            content.setCenter(textWrapper);
            content.setBottom(null); // Unset bottom
            textWrapper.setAlignment(Pos.CENTER_LEFT);
        } else {
            // GRID MODE: Icon in center, text on bottom, aligned center
            content.setCenter(iconWrapper);
            content.setBottom(textWrapper);
            content.setLeft(null); // Unset left
            textWrapper.setAlignment(Pos.CENTER);
        }
    }
}
