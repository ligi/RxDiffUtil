/*
 * Copyright (C) 2017 volders GmbH with <3 in Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package berlin.volders.rxdiff;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView;

import java.util.ConcurrentModificationException;

import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;

/**
 * {@code RxDiffResult} provides an interface to apply the result of the
 * {@code RxDiffUtil.calculateDiff()} methods family. Only one
 * {@code Completable} returned by {@link #applyDiff(Action2)} should be
 * active at any time. Simultaneous application of the diff results in a
 * {@link ConcurrentModificationException}.
 *
 * @param <T> type of the data set
 * @param <A> type of the adapter
 */
public class RxDiffResult<A extends RecyclerView.Adapter, T> {

    @VisibleForTesting
    final Observable<OnCalculateDiffResult<A, T>> o;

    RxDiffResult(Observable<OnCalculateDiffResult<A, T>> o) {
        this.o = o.share().observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * @param onUpdate callback to update the data set
     * @return a {@link Completable} to apply
     */
    public Completable applyDiff(@NonNull Action2<? super A, ? super T> onUpdate) {
        return o.doOnNext(new OnApplyDiff<>(onUpdate)).share().toCompletable();
    }
}
