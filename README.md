![Icon](icon.png) RxDiffUtil
============================
[![Build][1]][2]
[![Release][3]][4]

*RxDiffUtil* is an Rx wrapper around the Android [DiffUtil] library for the
`RecyclerView` widget. It handles threading and reacts to concurrent changes
to the adapter with a `ConcurrentModificationException`.

All computation is done on the thread defined by the upstream `Observable`,
while the application of the diff result and all terminal events are
propagated on the Android main thread.

The resulting `Completable` expects upstream to honor backpressure and shares
the subscription to reduce concurrent changes to the adapter.


Usage
-----

Convert any `Observable` to a `Completable` applying all changes as diff to the
provided adapter.

    service.observeData()
           .subscribeOn(Schedulers.compute())
           .onBackpressureLatest()
           .to(RxDiffUtil.with(adapter))
           .calculateDiff(callback))
           .applyDiff(AdapterImpl::setUnsafe)
           .subscribe();


Installation
------------

Add [JitPack][4] to your repositories and *RxDiffUtil* to the dependencies

    compile "berlin.volders:rxdiffutil:$rxDiffUtilVersion"


License
-------

    Copyright (C) 2017 volders GmbH with <3 in Berlin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://travis-ci.org/volders/RxDiffUtil.svg?branch=master
  [2]: https://travis-ci.org/volders/RxDiffUtil
  [3]: https://jitpack.io/v/berlin.volders/rxdiffutil.svg
  [4]: https://jitpack.io/#berlin.volders/rxdiffutil
  [DiffUtil]: https://developer.android.com/reference/android/support/v7/util/DiffUtil.html
