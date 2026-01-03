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

import com.github.idelstak.ikonx.mvu.*;
import com.github.idelstak.ikonx.mvu.action.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.*;
import java.util.*;

final class FlowProbe implements Flow {

    private final Subject<Action> actions;
    private final Deque<Action> actionHistory;
    private final Update update;
    private final Observable<UpdateResult> states;
    private UpdateResult currentResult;

    FlowProbe() {
        actions = PublishSubject.<Action>create().toSerialized();
        actionHistory = new ArrayDeque<>();
        update = new Update();
        states = actions
          .doOnNext(actionHistory::add)
          .scan(UpdateResult.initial(), (result, action) -> update.apply(result.state(), action))
          .doOnNext(result -> currentResult = result)
          .distinctUntilChanged()
          .replay(1)
          .autoConnect();
    }

    @Override
    public void accept(Action action) {
        actions.onNext(action);
    }

    @Override
    public Observable<UpdateResult> observe() {
        return states;
    }

    Optional<Action> probeLastAfter(int count) {
        if (actionHistory.size() <= count) {
            return Optional.empty();
        }
        return Optional.of(actionHistory.getLast());
    }

    int probeActionCount() {
        return actionHistory.size();
    }

    UpdateResult probeResult() {
        return currentResult;
    }
}
