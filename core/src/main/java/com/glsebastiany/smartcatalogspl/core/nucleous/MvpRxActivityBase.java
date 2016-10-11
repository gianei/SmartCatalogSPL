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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.glsebastiany.smartcatalogspl.core.presentation.di.InjectableActivity;

/**
 * This class is an example of how an activity could controls it's presenter.
 * You can inherit from this class or copy/paste this class's code to
 * createPresenter your own view implementation.
 *
 * @param <P> a type of presenter to return with {@link #getPresenter}.
 */
public abstract class MvpRxActivityBase<P extends Presenter> extends InjectableActivity {

    private PresenterLifecycleDelegate<P> presenterDelegate =
            new PresenterLifecycleDelegate<>(ReflectionPresenterFactory.<P>fromViewClass(getClass()));

    /**
     * Returns a current presenter factory.
     */
    public PresenterFactory<P> getPresenterFactory() {
        return presenterDelegate.getPresenterFactory();
    }

    /**
     * Sets a presenter factory.
     * Call this method before onCreatePresenter/onFinishInflate to override default {@link ReflectionPresenterFactory} presenter factory.
     * Use this method for presenter dependency injection.
     */
    public void setPresenterFactory(PresenterFactory<P> presenterFactory) {
        presenterDelegate.setPresenterFactory(presenterFactory);
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
        return presenterDelegate.getPresenter();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        presenterDelegate.onResume(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenterDelegate.onCreate(savedInstanceState, this);
    }

    /**
     * This method must be called after view fields are assigned, so that the presenter can work
     */
    public void presenterAfterView() {
        presenterDelegate.onAfterViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenterDelegate.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenterDelegate.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenterDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterDelegate.onDestroy(!isChangingConfigurations());
    }

}
