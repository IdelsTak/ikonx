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

import com.github.idelstak.ikonx.*;
import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.action.*;
import java.io.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
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
    void typingUpdatesSearchTextInState(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.clickOn("#searchField").write("Café");
        var finalState = flow.probeState();
        assertEquals("Café", finalState.searchText());
    }

    @Test
    void typingEmitsSearchChanged(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        var before = flow.probeActionCount();

        robot.clickOn("#searchField").write("jalapeño");

        assertInstanceOf(
          Action.SearchChanged.class,
          flow.probeLastAfter(before).orElseThrow()
        );
    }

    @Test
    void typingEmitsSingleAction(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        var before = flow.probeActionCount();

        robot.clickOn("#searchField").write("λ");

        assertEquals(before + 1, flow.probeActionCount());
    }

    @Test
    void stateDrivesFiltering(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.clickOn("#searchField").write("über");
        var finalState = flow.probeState();
        assertTrue(finalState.displayedIkons().isEmpty());
    }

    @Test
    void searchFieldRendersFromState(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.interact(() -> flow.accept(new Action.SearchChanged("λ")));
        var searchField = robot.lookup("#searchField").queryAs(TextField.class);
        assertEquals("λ", searchField.getText());
    }

    @Test
    void searchChangedCarriesTypedText(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        var before = flow.probeActionCount();

        robot.clickOn("#searchField").write("Café");

        var action = (Action.SearchChanged) flow.probeLastAfter(before).orElseThrow();

        assertEquals("Café", action.query());
    }

    @Test
    void selectAllToggleShouldUpdateSelectedPacks(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.clickOn("#selectAllToggle");
        var finalState = flow.probeState();
        assertEquals(Pack.values().length, finalState.selectedPacks().size());
    }

    @Test
    void selectAllToggleEmitsAction(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        var before = flow.probeActionCount();

        robot.clickOn("#selectAllToggle");

        assertInstanceOf(
          Action.SelectPacksAllToggled.class,
          flow.probeLastAfter(before).orElseThrow()
        );
    }

    @Test
    void packSelectionUpdatesState(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.interact(() -> flow.accept(new Action.PackToggled(Pack.BOOTSTRAP, true)));
        var finalState = flow.probeState();
        assertTrue(finalState.selectedPacks().contains(Pack.BOOTSTRAP));
    }

    @Test
    void packToggleEmitsCorrectPack(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        var before = flow.probeActionCount();

        robot.clickOn("#packCombo");
        robot.clickOn(Pack.ANT_DESIGN_ICONS.toString());
        robot.clickOn(Pack.BOOTSTRAP.toString());

        var action = (Action.PackToggled) flow.probeLastAfter(before).orElseThrow();

        assertEquals(Pack.BOOTSTRAP, action.pack());
    }

    @Test
    void iconClickCopiesCodeToClipboard(FxRobot robot) {
        var clipboardContent = new AtomicReference<String>();
        var flow = launch(robot, clipboardContent::set);

        robot.interact(() -> {
            flow.accept(new Action.PackToggled(Pack.ANT_DESIGN_ICONS, false));
            flow.accept(new Action.PackToggled(Pack.BOOTSTRAP, true));
        });

        var iconDescription = Pack.BOOTSTRAP.ikons()[0].ikon().getDescription();
        robot.clickOn(iconDescription);

        var finalState = flow.probeState();
        assertThat(finalState.statusMessage(), containsString(clipboardContent.get()));
    }

    @Test
    void iconClickEmitsCopyAction(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.interact(() -> {
            flow.accept(new Action.PackToggled(Pack.ANT_DESIGN_ICONS, false));
            flow.accept(new Action.PackToggled(Pack.BOOTSTRAP, true));
        });

        var before = flow.probeActionCount();
        var ikon = Pack.BOOTSTRAP.ikons()[0].ikon();

        robot.clickOn(ikon.getDescription());

        var action = (Action.CopyIconSucceeded) flow.probeLastAfter(before).orElseThrow();

        assertEquals(ikon.getDescription(), action.iconCode());
    }

    @Test
    void stateRendersCorrectIconCode(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.interact(() -> {
            flow.accept(new Action.PackToggled(Pack.ANT_DESIGN_ICONS, false));
            flow.accept(new Action.PackToggled(Pack.BOOTSTRAP, true));
        });

        var table = robot.lookup(".icon-browser").queryAs(TableView.class);
        var last = table.getItems().size() - 1;
        robot.interact(() -> table.scrollTo(last));

        var row = robot.lookup(".table-row-cell").queryAllAs(TableRow.class).stream()
          .filter(r -> r.getIndex() == last).findFirst().orElseThrow();
        var rendered = row.lookupAll(".ikonli-font-icon").stream()
          .map(FontIcon.class::cast).toList().getLast().getIconCode();
        var ikon = Pack.BOOTSTRAP.ikons()[Pack.BOOTSTRAP.ikons().length - 1].ikon();

        assertEquals(ikon, rendered);
    }

    @Test
    void stateRendersCorrectIconDescription(FxRobot robot) {
        var flow = launch(robot, _ -> {
        });
        robot.interact(() -> {
            flow.accept(new Action.PackToggled(Pack.ANT_DESIGN_ICONS, false));
            flow.accept(new Action.PackToggled(Pack.BOOTSTRAP, true));
        });

        var table = robot.lookup(".icon-browser").queryAs(TableView.class);
        robot.interact(() -> table.scrollTo(table.getItems().size() - 1));

        var label = robot.lookup(".icon-label").queryAllAs(Labeled.class).stream()
          .map(Labeled::getText).sorted().toList().getLast();
        var ikon = Pack.BOOTSTRAP.ikons()[Pack.BOOTSTRAP.ikons().length - 1].ikon();

        assertEquals(ikon.getDescription(), label);
    }

    private FlowProbe launch(FxRobot robot, Consumer<String> copyCallback) {
        LocalClipboard testClipboard = copyCallback::accept;
        var flow = new FlowProbe(testClipboard);
        robot.interact(() -> {
            var loader = new FXMLLoader(Ikonx.class.getResource("/fxml/icon-view.fxml"));
            loader.setControllerFactory(_ -> new IconView(flow));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException error) {
                throw new RuntimeException(error);
            }
            var stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        return flow;
    }
}
