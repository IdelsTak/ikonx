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

import com.github.idelstak.ikonx.view.IconView;
import java.io.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.testfx.framework.junit5.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public final class IconViewTest extends ApplicationTest {

    private IconView controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/icon-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testControllerIsNotNull() {
        assertNotNull(controller);
    }

//    @Test
//    void testSearchFieldInteraction(FxRobot robot) {
//        // Given
//        String textToType = "search text";
//
//        // When
//        robot.clickOn("#searchField");
//        robot.write(textToType);
//
//        // Then
//        assertEquals(textToType, controller.getSearchText());
//    }
//
//    @Test
//    void testSelectAllToggle(FxRobot robot) {
//        // When
//        robot.clickOn("#selectAllToggle");
//
//        // Then
//        assertEquals(controller.getPackComboBox().getItems().size(), controller.getSelectedPacks().size());
//    }
//
//    @Test
//    void testPackComboBoxSelection(FxRobot robot) {
//        // When
//        robot.clickOn("#packCombo");
//        robot.clickOn(".check-box");
//        
//        // Then
//        assertTrue(controller.getSelectedPacks().isEmpty());
//    }
}
