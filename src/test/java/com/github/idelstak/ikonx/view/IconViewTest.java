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

import com.github.idelstak.ikonx.*;
import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.action.*;
import java.io.*;
import java.util.function.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.kordamp.ikonli.javafx.*;
import org.testfx.api.*;
import org.testfx.framework.junit5.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
final class IconViewTest {

    @Test
    void typingEmitsSearchChanged(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);
        var before = flow.probeActionCount();

        robot.clickOn("#searchField").write("jalapeño");

        assertInstanceOf(
          Action.SearchChanged.class,
          flow.probeLastAfter(before).orElseThrow()
        );
    }

    @Test
    void typingUpdatesSearchTextInState(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);

        robot.clickOn("#searchField").write("Café");

        assertEquals(
          "Café",
          flow.probeState().searchText()
        );
    }

    @Test
    void stateDrivesFiltering(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);

        robot.clickOn("#searchField").write("über");

        assertTrue(
          flow.probeState().displayedIcons().isEmpty()
        );
    }

    @Test
    void searchFieldRendersFromState(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);

        robot.interact(() -> flow.accept(new Action.SearchChanged("λ")));

        assertEquals(
          "λ",
          robot.lookup("#searchField")
            .queryTextInputControl()
            .getText()
        );
    }

    @Test
    void selectAllToggleShouldDispatchSelectAllAction(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);
        var before = flow.probeActionCount();
        robot.clickOn("#selectAllToggle");

        var lastAction = flow.probeLastAfter(before);
        assertInstanceOf(Action.SelectAllToggled.class, lastAction.orElseThrow());
    }

    @Test
    void selectAllToggleShouldUpdateSelectedPacks(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);
        robot.clickOn("#selectAllToggle");

        var result = flow.probeState();
        assertEquals(result.selectedPacks().size(), Pack.values().length);
    }

    @Test
    void packComboCheckboxShouldDispatchPackToggledAction(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);
        var before = flow.probeActionCount();
        robot.clickOn("#packCombo");
        robot.clickOn(".check-box");

        var lastAction = flow.probeLastAfter(before);
        assertInstanceOf(Action.PackToggled.class, lastAction.orElseThrow());
    }

    @Test
    void dispatchedActionHasCorrectIconCode(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        var flow = launch(robot, fakeClipboard::set);

        robot.clickOn("#packCombo");
        robot.clickOn(Pack.ANT_DESIGN_ICONS.toString());
        robot.clickOn(Pack.BOOTSTRAP.toString());
        robot.clickOn("#packCombo");

        var before = flow.probeActionCount();
        robot.clickOn("bi-alarm");

        var lastAction = flow.probeLastAfter(before);
        Action.CopyIconSucceeded action = (Action.CopyIconSucceeded) lastAction.orElseThrow();

        assertThat(action.iconCode(), is("bi-alarm"));
    }

    @Test
    void rendersText(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        launch(robot, fakeClipboard::set);

        robot.clickOn("#packCombo");
        robot.clickOn(Pack.ANT_DESIGN_ICONS.toString());
        robot.clickOn(Pack.BOOTSTRAP.toString());
        robot.clickOn("#packCombo");

        var table = robot.lookup(".icon-browser").queryAs(TableView.class);

        robot.interact(() -> table.scrollTo(table.getItems().size() - 1));

        var label = robot.lookup(".icon-label").queryAllAs(Labeled.class)
          .stream()
          .map(Labeled::getText)
          .sorted()
          .toList()
          .getLast();
        var expected = Pack.BOOTSTRAP.getIkons()[Pack.BOOTSTRAP.getIkons().length - 1].getDescription();

        assertThat(label, is(expected));
    }

    @Test
    void rendersIcon(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        launch(robot, fakeClipboard::set);

        robot.clickOn("#packCombo");
        robot.clickOn(Pack.ANT_DESIGN_ICONS.toString());
        robot.clickOn(Pack.BOOTSTRAP.toString());
        robot.clickOn("#packCombo");

        var table = robot.lookup(".icon-browser").queryAs(TableView.class);
        var last = table.getItems().size() - 1;
        robot.interact(() -> table.scrollTo(last));

        var row = robot.lookup(".table-row-cell")
          .queryAllAs(TableRow.class)
          .stream()
          .filter(r -> r.getIndex() == last)
          .findFirst()
          .orElseThrow();
        
        var rendered = row.lookupAll(".ikonli-font-icon")
          .stream()
          .map(FontIcon.class::cast)
          .toList()
          .getLast()
          .getIconCode();
        
        var expected = Pack.BOOTSTRAP.getIkons()[Pack.BOOTSTRAP.getIkons().length - 1];
        assertThat(rendered, is(expected));
    }

    @Test
    void copiesIconCodeToClipboard(FxRobot robot) throws Exception {
        var fakeClipboard = new SimpleStringProperty();
        launch(robot, fakeClipboard::set);

        robot.clickOn("#packCombo");
        robot.clickOn(Pack.ANT_DESIGN_ICONS.toString());
        robot.clickOn(Pack.BOOTSTRAP.toString());
        robot.clickOn("#packCombo");

        var table = robot.lookup(".icon-browser").queryAs(TableView.class);
        robot.interact(() -> table.scrollTo(table.getItems().size() - 1));

        var expected = Pack.BOOTSTRAP.getIkons()[Pack.BOOTSTRAP.getIkons().length - 1].getDescription();
        robot.clickOn(expected);
        
        assertThat(fakeClipboard.get(), is(expected));
    }

    private FlowProbe launch(FxRobot robot, Consumer<String> copyCallback) throws Exception {
        LocalClipboard testClipboard = copyCallback::accept;
        var flow = new FlowProbe(testClipboard);
        robot.interact(() -> {
            var loader = new FXMLLoader(
              Ikonx.class.getResource("/fxml/icon-view.fxml")
            );
            loader.setControllerFactory(_ -> new IconView(flow));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException error) {
                throw new RuntimeException(error);
            }
            var stage = new Stage();
            stage.setOnCloseRequest(_ -> {
                var controller = loader.<IconView>getController();
                if (controller != null) {
                    controller.dispose();
                }
                Platform.exit();
                System.exit(0); // ensures all threads die
            });
            stage.setScene(new Scene(root));
            stage.show();
        });
        return flow;
    }
}
