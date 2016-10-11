/*
 *     SmartCatalogSPL, an Android catalog Software Product Line
 *     Copyright (c) 2016 Gianei Leandro Sebastiany
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.glsebastiany.smartcatalogspl.core.nucleous;

import android.os.Bundle;
import android.support.annotation.Nullable;

public final class PresenterLifecycleDelegate<P extends Presenter> {

    private static final String PRESENTER_KEY = "presenter";
    private static final String PRESENTER_ID_KEY = "presenter_id";

    @Nullable private PresenterFactory<P> presenterFactory;
    @Nullable private P presenter;
    @Nullable private Bundle bundle;

    public PresenterLifecycleDelegate(@Nullable PresenterFactory<P> presenterFactory) {
        this.presenterFactory = presenterFactory;
    }

    /**
     * Returns a current presenter factory.
     */
    @Nullable
    public PresenterFactory<P> getPresenterFactory() {
        return presenterFactory;
    }

    /**
     * Sets a presenter factory.
     * Call this method before onCreatePresenter/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
     * Use this method for presenter dependency injection.
     */
    public void setPresenterFactory(@Nullable PresenterFactory<P> presenterFactory) {
        if (presenter != null)
            throw new IllegalArgumentException("setPresenterFactory() should be called before onResume()");
        this.presenterFactory = presenterFactory;
    }

    /**
     * Returns a current attached presenter.
     * This method is guaranteed to return a non-null value between
     * onResume/onPause and onAttachedToWindow/onDetachedFromWindow calls
     * if the presenter factory returns a non-null value.
     *
     * @return a currently attached presenter or null.
     */
    public P getPresenter() {
        if (presenterFactory != null) {
            if (presenter == null && bundle != null) {
                presenter = PresenterStorage.INSTANCE.getPresenter(bundle.getString(PRESENTER_ID_KEY));
            }

            if (presenter == null) {
                presenter = presenterFactory.createPresenter();
                PresenterStorage.INSTANCE.add(presenter);
                presenter.onCreatePresenter(bundle);
            }
            bundle = null;
        }
        return presenter;
    }

    /**
     * {@link android.app.Activity#onSaveInstanceState(Bundle)}, {@link android.app.Fragment#onSaveInstanceState(Bundle)}, {@link android.view.View#onSaveInstanceState()}.
     */
    public void onSaveInstanceState(Bundle bundle) {
        getPresenter();
        if (presenter != null) {
            presenter.onSaveViewInstance(bundle);
            bundle.putString(PRESENTER_ID_KEY, PresenterStorage.INSTANCE.getId(presenter));
        }
    }


    public void onCreate(Bundle bundle, Object view) {
        if (presenter != null)
            throw new IllegalArgumentException("onCreatePresenter() should be called before onResume()");
        if (bundle != null)
            this.bundle = ParcelFn.unmarshall(ParcelFn.marshall(bundle));
        getPresenter();
        //noinspection unchecked
        presenter.onCreateView(view);
    }

    public void onAfterViews() {
        presenter.onAfterViews();
    }

    /**
     * {@link android.app.Activity#onResume()},
     * {@link android.app.Fragment#onResume()},
     * {@link android.view.View#onAttachedToWindow()}
     */
    public void onResume(Object view) {
        getPresenter();
        if (presenter != null) {
            //noinspection unchecked
            presenter.onResumeView(view);
        }
    }

    /**
     * {@link android.app.Activity#onDestroy()},
     * {@link android.app.Fragment#onDestroyView()},
     * {@link android.view.View#onDetachedFromWindow()}
     */
    public void onPause() {
        if (presenter != null) {
            presenter.onPauseView();
        }
    }

    /**
     * {@link android.app.Activity#onDestroy()},
     * {@link android.app.Fragment#onDestroy()},
     * {@link android.view.View#onDetachedFromWindow()}
     */
    public void onDestroy(boolean isFinal) {
        if (presenter != null && isFinal) {
            presenter.onDestroyView();
            presenter = null;
        }
    }

}
