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

package com.glsebastiany.smartcatalogspl.core.presentation.nucleous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import rx.Subscription;
import rx.functions.Func0;
import rx.subscriptions.CompositeSubscription;

public abstract class Presenter<V> {

    private static final String REQUESTED_KEY = Presenter.class.getName() + "#requested";

    private final CompositeSubscription subscriptions = new CompositeSubscription();
    private final HashMap<Integer, Func0<Subscription>> restartables = new HashMap<>();
    private final HashMap<Integer, Subscription> restartableSubscriptions = new HashMap<>();
    private final ArrayList<Integer> requested = new ArrayList<>();

    @Nullable
    private V view;
    private CopyOnWriteArrayList<OnDestroyListener> onDestroyListeners = new CopyOnWriteArrayList<>();

    /**
     * Registers a subscription to automatically unsubscribe it during onDestroy.
     * See {@link CompositeSubscription#add(Subscription) for details.}
     *
     * @param subscription a subscription to add.
     */
    protected void add(Subscription subscription) {
        subscriptions.add(subscription);
    }

    /**
     * Removes and unsubscribes a subscription that has been registered with {@link #add} previously.
     * See {@link CompositeSubscription#remove(Subscription)} for details.
     *
     * @param subscription a subscription to remove.
     */
    protected void remove(Subscription subscription) {
        subscriptions.remove(subscription);
    }

    /**
     * A restartable is any RxJava observable that can be started (subscribed) and
     * should be automatically restarted (re-subscribed) after a process restart if
     * it was still subscribed at the moment of saving presenter's state.
     *
     * Registers a factory. Re-subscribes the restartable after the process restart.
     *
     * @param restartableId id of the restartable
     * @param factory       factory of the restartable
     */
    protected void restartable(int restartableId, Func0<Subscription> factory) {
        restartables.put(restartableId, factory);
        if (requested.contains(restartableId))
            start(restartableId);
    }

    /**
     * Starts the given restartable.
     *
     * @param restartableId id of the restartable
     */
    protected void start(int restartableId) {
        stop(restartableId);
        requested.add(restartableId);
        restartableSubscriptions.put(restartableId, restartables.get(restartableId).call());
    }

    /**
     * Unsubscribes a restartable
     *
     * @param restartableId id of a restartable.
     */
    protected void stop(int restartableId) {
        requested.remove((Integer) restartableId);
        Subscription subscription = restartableSubscriptions.get(restartableId);
        if (subscription != null)
            subscription.unsubscribe();
    }

    /**
     * Checks if a restartable is unsubscribed.
     *
     * @param restartableId id of the restartable.
     * @return true if the subscription is null or unsubscribed, false otherwise.
     */
    protected boolean isUnsubscribed(int restartableId) {
        Subscription subscription = restartableSubscriptions.get(restartableId);
        return subscription == null || subscription.isUnsubscribed();
    }

    /**
     * Returns a current view attached to the presenter or null.
     *
     * View is normally available between
     * {@link Activity#onResume()} and {@link Activity#onPause()},
     * {@link Fragment#onResume()} and {@link Fragment#onPause()},
     * {@link android.view.View#onAttachedToWindow()} and {@link android.view.View#onDetachedFromWindow()}.
     *
     * Calls outside of these ranges will return null.
     * Notice here that {@link Activity#onActivityResult(int, int, Intent)} is called *before* {@link Activity#onResume()}
     * so you can't use this method as a callback.
     *
     * @return a current attached view.
     */
    @Nullable
    protected V getView() {
        return view;
    }


    /**
     * A callback to be invoked when a presenter is about to be destroyed.
     */
    public interface OnDestroyListener {
        /**
         * Called before {@link Presenter#onDestroyView()}.
         */
        void onDestroy();
    }

    /**
     * Adds a listener observing {@link #onDestroyView()}.
     *
     * @param listener a listener to add.
     */
    public void addOnDestroyListener(OnDestroyListener listener) {
        onDestroyListeners.add(listener);
    }

    /**
     * Removed a listener observing {@link #onDestroyView()}.
     *
     * @param listener a listener to remove.
     */
    public void removeOnDestroyListener(OnDestroyListener listener) {
        onDestroyListeners.remove(listener);
    }
















    /**
     * This method is called after presenter construction.
     * <p>
     * This method is intended for overriding.
     *
     * @param savedState If the presenter is being re-instantiated after a process restart then this Bundle
     *                   contains the data it supplied in {@link #onSaveViewInstance(Bundle)}.
     */
    protected void onCreatePresenter(Bundle savedState) {}

    /**
     * This method is called after a view is created. Take care since some fields such as those defined with findViewById
     * may be null at this point
     * @param view
     */
    @CallSuper
    protected void onCreateView(V view){
        this.view = view;
    }

    /**
     * This method is called on creation when the view as filled its view fields with findViewById
     */
    protected void onAfterViews() {}

    /**
     * This method is called after presenter construction and recreation.
     * <p>
     * This method is intended for overriding.
     */
    @CallSuper
    protected void onResumeView(V view) {
        this.view = view;
    }

    /**
     * This method is being called when a view gets detached from the presenter.
     * Normally this happens during {@link Activity#onPause()} ()}, {@link Fragment#onDestroyView()}
     * and {@link android.view.View#onDetachedFromWindow()}.
     *
     * This method is intended for overriding.
     */
    @CallSuper
    protected void onPauseView() {
        this.view = null;
    }

    /**
     * This method is being called when a user leaves view.
     *
     * This method is intended for overriding.
     */
    @CallSuper
    protected void onDestroyView() {
        for (OnDestroyListener listener : onDestroyListeners)
            listener.onDestroy();

        subscriptions.unsubscribe();
        for (Map.Entry<Integer, Subscription> entry : restartableSubscriptions.entrySet())
            entry.getValue().unsubscribe();
    }

    /**
     * A returned state is the state that will be passed to {@link #onCreatePresenter} for a new presenter instance after a process restart.
     *
     * This method is intended for overriding.
     *
     * @param state a non-null bundle which should be used to put presenter's state into.
     */
    @CallSuper
    protected void onSaveViewInstance(Bundle state) {
        for (int i = requested.size() - 1; i >= 0; i--) {
            int restartableId = requested.get(i);
            Subscription subscription = restartableSubscriptions.get(restartableId);
            if (subscription != null && subscription.isUnsubscribed())
                requested.remove(i);
        }
        state.putIntegerArrayList(REQUESTED_KEY, requested);
    }

}
