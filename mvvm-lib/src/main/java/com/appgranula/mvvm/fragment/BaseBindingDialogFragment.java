package com.appgranula.mvvm.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.appgranula.mvvm.activity.BaseMVVMActivity;
import com.appgranula.mvvm.api.BaseMVVMApi;
import com.appgranula.mvvm.binding.DataBindingClassUtils;
import com.appgranula.mvvm.utils.TypeResolver;
import com.appgranula.mvvm.viewmodel.BaseFragmentViewModel;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 19.01.2016
 */
@SuppressWarnings("unused")
public abstract class BaseBindingDialogFragment<B extends ViewDataBinding, M extends BaseFragmentViewModel> extends DialogFragment {
    private static final String EXTRA_MODEL = "extra_model";
    private final Class<B> bindingClass;
    private final Class<M> modelClass;
    private B binding;
    private M model;
    private DialogInterface.OnDismissListener onDismissListener;
    private ArrayList<Subscription> subscriptions = new ArrayList<>();


    @SuppressWarnings("unchecked")
    public BaseBindingDialogFragment() {
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(BaseBindingDialogFragment.class, getClass());
        this.bindingClass = (Class<B>) typeArguments[0];
        this.modelClass = (Class<M>) typeArguments[1];
    }

    @Override
    public void onResume() {
        super.onResume();
        model.onResume();
    }

    public abstract String getFragmentTag();

    protected B getBinding() {
        return binding;
    }

    protected M getModel() {
        return model;
    }

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = (B) DataBindingClassUtils.getViewDataBinding(bindingClass, LayoutInflater.from(getActivity()), null);
        createModel(savedInstanceState);
        AlertDialog.Builder alertDialog;
        if (getStyle() != -1) {
            alertDialog = new AlertDialog.Builder(getActivity(), getStyle());
        } else {
            alertDialog = new AlertDialog.Builder(getActivity());
        }
        alertDialog.setTitle(getTitle())
                .setView(binding.getRoot());
        onBeforeDialogCreated(alertDialog);
        return alertDialog.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null)
            onDismissListener.onDismiss(dialog);
    }

    protected int getStyle() {
        return -1;
    }

    protected abstract String getTitle();


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    private void createModel(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            model = (M) savedInstanceState.getSerializable(EXTRA_MODEL);
        }
        if (model == null) {
            try {
                model = modelClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        binding.setVariable(com.appgranula.mvvm.BR.viewModel, model);
        model.setApi(getApi());
        model.onViewCreated();
        onViewModelCreated(model);
        model.onModelAttached();
    }

    public BaseMVVMApi getApi() {
        return getBaseActivity().getApi();
    }


    protected BaseMVVMActivity getBaseActivity() {
        return (BaseMVVMActivity) getActivity();
    }

    protected void onBeforeDialogCreated(AlertDialog.Builder dialogBuilder) {

    }

    protected void onViewModelCreated(M model) {

    }

    @Override
    public void onPause() {
        super.onPause();
        model.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        model.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        for (Subscription subscription : subscriptions) {
            subscription.unsubscribe();
        }
        subscriptions.clear();
    }

    @SuppressWarnings({"unchecked", "unused"})
    public <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        subscriptions.add(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
    }

    @SuppressWarnings({"unchecked", "unused"})
    public <T> void subscribe(Observable<T> observable, Action1<T> onNext) {
        subscriptions.add(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext));
    }

    @SuppressWarnings({"unchecked", "unused"})
    public <T> void subscribe(Observable<T> observable, Action1<T> onNext, Action1<Throwable> onError) {
        subscriptions.add(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError));
    }

    @SuppressWarnings({"unchecked", "unused"})
    public <T> void subscribe(Observable<T> observable, Action1<T> onNext, Action1<Throwable> onError, Action0 onComplete) {
        subscriptions.add(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError, onComplete));
    }
}
