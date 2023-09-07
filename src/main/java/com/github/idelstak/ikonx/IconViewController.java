package com.github.idelstak.ikonx;

import com.dlsc.gemsfx.SearchTextField;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.StatusBar;
import org.kordamp.ikonli.Ikon;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IconViewController {
    private static final Logger LOG = Logger.getLogger(IconViewController.class.getName());
    private static final int FILTER_LEN = 2;
    private final List<PackIkon> packIkons;

    @FXML
    private CheckComboBox<Pack> packCombo;
    @FXML
    private SearchTextField searchField;
    @FXML
    private TableView<List<PackIkon>> iconsTable;
    @FXML
    private StatusBar statusBar;

    public IconViewController() {
        packIkons = new ArrayList<>();
    }

    @FXML
    protected void initialize() {
        packCombo.setTitle("Selected icon packs: ");
        packCombo.setShowCheckedCount(true);

        Comparator<Pack> packComparator = (p1, p2) -> Comparator.comparing(Pack::toString).compare(p1, p2);
        List<Pack> packs = Arrays.stream(Pack.values())
                .sorted(packComparator)
                .toList();
        LOG.log(Level.INFO, "sorted pack values: {0}", packs);

        packCombo.getItems().setAll(packs);
        Platform.runLater(packCombo.getCheckModel()::checkAll);

        packCombo.getCheckModel().getCheckedItems().addListener((ListChangeListener.Change<? extends Pack> change) -> {
            while (change.next()) {
                packIkons.clear();
                change.getList().forEach(this::setPackIkons);

                updateData(searchField.getText());
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> updateData(newValue));

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
