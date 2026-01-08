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
package com.github.idelstak.ikonx.mvu;

import com.github.idelstak.ikonx.*;
import com.github.idelstak.ikonx.mvu.Flow;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.view.*;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.*;
import java.io.*;
import java.util.concurrent.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.testfx.api.*;
import org.testfx.framework.junit5.*;

@ExtendWith(ApplicationExtension.class)
final class StateFlowTest {

    @Test
    void throttlesSearchInput(FxRobot robot) {
        var time = new TestScheduler();
        var flow = launch(robot, time);
        var states = flow.observe().test();

        flow.accept(new Action.SearchChanged("α"));
        flow.accept(new Action.SearchChanged("αλ"));
        flow.accept(new Action.SearchChanged("αλφ"));

        time.advanceTimeBy(299, TimeUnit.MILLISECONDS);
        states.assertValueCount(1);
        time.advanceTimeBy(1, TimeUnit.MILLISECONDS);

        states.assertValueAt(
          states.values().size() - 1,
          s -> "αλφ".equals(s.searchText())
        );
    }

    private Flow launch(FxRobot robot, Scheduler time) {
        LocalClipboard clipboard = _ -> {
        };
        var meta = AppMeta.empty();
        var flow = new StateFlow(clipboard, meta, time);
        
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
