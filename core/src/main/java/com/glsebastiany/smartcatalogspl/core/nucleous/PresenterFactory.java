package com.glsebastiany.smartcatalogspl.core.nucleous;


public interface PresenterFactory<P extends Presenter> {
    P createPresenter();
}
