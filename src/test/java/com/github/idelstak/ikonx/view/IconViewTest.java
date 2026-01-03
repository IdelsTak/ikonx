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
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.testfx.api.*;
import org.testfx.framework.junit5.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
final class IconViewTest {

    @Test
    void typingEmitsSearchChanged(FxRobot robot) throws Exception {
        var flow = launch(robot);
        var before = flow.probeActionCount();

        robot.clickOn("#searchField").write("jalapeño");

        assertInstanceOf(
          Action.SearchChanged.class,
          flow.probeLastAfter(before).orElseThrow()
        );
    }

    @Test
    void typingUpdatesSearchTextInState(FxRobot robot) throws Exception {
        var flow = launch(robot);

        robot.clickOn("#searchField").write("Café");

        assertEquals(
          "Café",
          flow.probeState().searchText()
        );
    }

    @Test
    void stateDrivesFiltering(FxRobot robot) throws Exception {
        var flow = launch(robot);

        robot.clickOn("#searchField").write("über");

        assertTrue(
          flow.probeState().displayedIcons().isEmpty()
        );
    }

    @Test
    void searchFieldRendersFromState(FxRobot robot) throws Exception {
        var flow = launch(robot);

        flow.accept(new Action.SearchChanged("λ"));

        assertEquals(
          "λ",
          robot.lookup("#searchField")
            .queryTextInputControl()
            .getText()
        );
    }

    @Test
    void selectAllToggleShouldDispatchSelectAllAction(FxRobot robot) throws Exception {
        // When
        var flow = launch(robot);
        var before = flow.probeActionCount();
        robot.clickOn("#selectAllToggle");

        // Then
        var lastAction = flow.probeLastAfter(before);
        assertInstanceOf(Action.SelectAllToggled.class, lastAction.orElseThrow());
    }

    @Test
    void selectAllToggleShouldUpdateSelectedPacks(FxRobot robot) throws Exception {
        // When
        var flow = launch(robot);
        robot.clickOn("#selectAllToggle");

        // Then
        var viewState = flow.probeState();
        assertEquals(viewState.selectedPacks().size(), Pack.values().length);
    }

    @Test
    void packComboCheckboxShouldDispatchPackToggledAction(FxRobot robot) throws Exception {
        // When
        var flow = launch(robot);
        var before = flow.probeActionCount();
        robot.clickOn("#packCombo");
        robot.clickOn(".check-box");

        // Then
        var lastAction = flow.probeLastAfter(before);
        assertInstanceOf(Action.PackToggled.class, lastAction.orElseThrow());
    }

    private FlowProbe launch(FxRobot robot) throws Exception {
        var flow = new FlowProbe();
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
            stage.setScene(new Scene(root));
            stage.show();
        });
        return flow;
    }
}
