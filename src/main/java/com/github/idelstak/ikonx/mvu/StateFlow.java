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
package com.github.idelstak.ikonx.mvu;

import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.view.*;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.subjects.*;

public final class StateFlow implements Flow {

    private final Subject<Action> actions;
    private final Observable<ViewState> states;

    public StateFlow(LocalClipboard clipboard, AppMeta appMeta) {
        actions = PublishSubject.<Action>create().toSerialized();

        var update = new Update();
        var effects = new EffectFlow(clipboard, appMeta);

        var seededActions = actions.startWithItem(new Action.AppVersionRequested());

        var sideEffects = effects.apply(seededActions);
        var merged = Observable.merge(seededActions, sideEffects);

        states = merged
          .scan(ViewState.initial(), update::apply)
          .distinctUntilChanged()
          .replay(1)
          .autoConnect();
    }

    @Override
    public void accept(Action action) {
        actions.onNext(action);
    }

    @Override
    public Observable<ViewState> observe() {
        return states;
    }
}
