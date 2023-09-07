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