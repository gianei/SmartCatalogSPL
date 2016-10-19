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

import java.util.HashMap;

public enum PresenterStorage {

    INSTANCE;

    private HashMap<String, Presenter> idToPresenter = new HashMap<>();
    private HashMap<Presenter, String> presenterToId = new HashMap<>();

    /**
     * Adds a presenter to the storage
     *
     * @param presenter a presenter to add
     */
    public void add(final Presenter presenter) {
        String id = presenter.getClass().getSimpleName() + "/" + System.nanoTime() + "/" + (int)(Math.random() * Integer.MAX_VALUE);
        idToPresenter.put(id, presenter);
        presenterToId.put(presenter, id);
        presenter.addOnDestroyListener(new Presenter.OnDestroyListener() {
            @Override
            public void onDestroy() {
                idToPresenter.remove(presenterToId.remove(presenter));
            }
        });
    }

    /**
     * Returns a presenter by id or null if such presenter does not exist.
     *
     * @param id  id of a presenter that has been received by calling {@link #getId(Presenter)}
     * @param <P> a type of presenter
     * @return a presenter or null
     */
    public <P> P getPresenter(String id) {
        //noinspection unchecked
        return (P)idToPresenter.get(id);
    }

    /**
     * Returns id of a given presenter.
     *
     * @param presenter a presenter to get id for.
     * @return if of the presenter.
     */
    public String getId(Presenter presenter) {
        return presenterToId.get(presenter);
    }

    /**
     * Removes all presenters from the storage.
     * Use this method for testing purposes only.
     */
    public void clear() {
        idToPresenter.clear();
        presenterToId.clear();
    }
}
