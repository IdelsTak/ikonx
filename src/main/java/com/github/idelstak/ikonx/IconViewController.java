package com.github.idelstak.ikonx;

import com.dlsc.gemsfx.SearchTextField;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;
import org.controlsfx.control.StatusBar;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IconViewController {
    private static final Logger LOG = Logger.getLogger(IconViewController.class.getName());
    private static final int FILTER_LEN = 2;
    private final List<PackIkon> packIkons;

    @FXML
    private SearchTextField searchField;
    @FXML
    private CheckComboBox<Pack> packCombo;
    @FXML
    private ToggleButton selectAllToggle;
    @FXML
    private Tooltip selectTip;
    @FXML
    private TableView<List<PackIkon>> iconsTable;
    @FXML
    private StatusBar statusBar;

    public IconViewController() {
        packIkons = new ArrayList<>();
    }

    @FXML
    protected void initialize() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateData(newValue));

        packCombo.setTitle("Selected icon packs: ");
        packCombo.setShowCheckedCount(true);

        Comparator<Pack> packComparator = (p1, p2) -> Comparator.comparing(Pack::toString).compare(p1, p2);
        List<Pack> packs = Arrays.stream(Pack.values())
                .sorted(packComparator)
                .toList();
        LOG.log(Level.INFO, "sorted pack values: {0}", packs);

        packCombo.getItems().setAll(packs);
        Platform.runLater(() -> packCombo.getCheckModel().check(0));

        packCombo.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends Pack> change) -> {
            while (change.next()) {
                packIkons.clear();
                ObservableList<? extends Pack> checkedPacks = change.getList();

                Platform.runLater(() -> {
                    if (selectAllToggle.isSelected()) {
                        selectAllToggle.setSelected(checkedPacks.size() == packCombo.getItems().size());
                    }

                    updateSelectTipText();
                });

                checkedPacks.forEach(this::setPackIkons);

                updateData(searchField.getText());
            }
        });

        selectAllToggle.setOnAction(event -> {
            if (selectAllToggle.isSelected()) {
                LOG.log(Level.INFO, "selecting all...");
                Platform.runLater(packCombo.getCheckModel()::checkAll);
            } else {
                Pack firstPack = packCombo.getItems().get(0);
                LOG.log(Level.INFO, "selecting {0}...", firstPack);
                Platform.runLater(() -> {
                    IndexedCheckModel<Pack> checkModel = packCombo.getCheckModel();
                    checkModel.clearChecks();
                    checkModel.check(firstPack);
                });
            }

            updateSelectTipText();
        });

        updateSelectTipText();

        iconsTable.setPlaceholder(new Text("No result found"));

        iconsTable.getItems().addListener((ListChangeListener.Change<? extends List<PackIkon>> change) -> {
            while (change.next()) {
                statusBar.setText("%d icons".formatted(change.getList().size()));
            }
        });

        iconsTable.getSelectionModel().setCellSelectionEnabled(true);

        for (int i = 0; i < iconsTable.getColumns().size(); i++) {
            TableColumn<List<PackIkon>, PackIkon> col = (TableColumn<List<PackIkon>, PackIkon>) iconsTable.getColumns().get(i);
            int colIndex = i;

            col.setCellValueFactory(cb -> {
                List<PackIkon> row = cb.getValue();
                PackIkon item = row.size() > colIndex ? row.get(colIndex) : null;
                return new SimpleObjectProperty<>(item);
            });

            col.setCellFactory(cb -> new FontIconCell());
            col.getStyleClass().add("align-center");
        }

        packs.forEach(this::setPackIkons);

        updateData(null);
    }

    private void updateSelectTipText() {
        selectTip.setText(selectAllToggle.isSelected() ? "Select first" : "Select all");
    }

    private void setPackIkons(Pack pack) {
        packIkons.addAll(Arrays.stream(pack.getIkons()).map(ikon -> new PackIkon(pack, ikon)).toList());
    }

    private void updateData(String filterString) {
        List<PackIkon> displayedIcons = filterString == null || filterString.isBlank() || filterString.length() < FILTER_LEN
                ? packIkons.stream().toList()
                : packIkons.stream().filter(packIkon -> containsString(packIkon.ikon().getDescription(), filterString)).toList();

        Collection<List<PackIkon>> data = partitionList(displayedIcons, iconsTable.getColumns().size());

        iconsTable.getItems().setAll(data);
    }

    private <T> Collection<List<T>> partitionList(List<T> list, int size) {
        List<List<T>> partitions = new ArrayList<>();
        if (list.size() == 0) {
            return partitions;
        }

        int length = list.size();
        int numOfPartitions = length / size + ((length % size == 0) ? 0 : 1);

        for (int i = 0; i < numOfPartitions; i++) {
            int from = i * size;
            int to = Math.min((i * size + size), length);
            partitions.add(list.subList(from, to));
        }
        return partitions;
    }

    private boolean containsString(String s1, String s2) {
        return s1.toLowerCase(Locale.ROOT).contains(s2.toLowerCase(Locale.ROOT));
    }
}
