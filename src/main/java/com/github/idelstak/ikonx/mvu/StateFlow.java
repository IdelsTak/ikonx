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

import com.github.idelstak.ikonx.mvu.Flow;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.view.*;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.*;
import io.reactivex.rxjava3.subjects.*;
import java.util.concurrent.*;

public final class StateFlow implements Flow {

    private final Subject<Action> actions;
    private final Observable<ViewState> states;

    public StateFlow(LocalClipboard clipboard, AppMeta appMeta) {
        this(clipboard, appMeta, Schedulers.computation());
    }

    StateFlow(LocalClipboard clipboard, AppMeta appMeta, Scheduler time) {
        actions = PublishSubject.<Action>create().toSerialized();

        var search = actions
          .ofType(Action.SearchChanged.class)
          .debounce(300, TimeUnit.MILLISECONDS, time)
          .distinctUntilChanged();

        var others = actions.filter(a -> !(a instanceof Action.SearchChanged));

        var throttled = Observable.merge(search, others);

        var seeded = throttled.startWithArray(
          new Action.AppVersionRequested(),
          new Action.StageIconsRequested()
        );

        var effects = new EffectFlow(clipboard, appMeta);
        var merged = Observable.merge(seeded, effects.apply(seeded));

        var update = new Update();

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
