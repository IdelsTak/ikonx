package com.github.idelstak.ikonx;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
    }

    @Override
    protected void updateItem(PackIkon packIkon, boolean empty) {
        super.updateItem(packIkon, empty);

        if (packIkon == null) {
            setGraphic(null);
            return;
        }

        root.setText(packIkon.ikon().getDescription());
        fontIcon.setIconCode(packIkon.ikon());
        setGraphic(root);
    }
}