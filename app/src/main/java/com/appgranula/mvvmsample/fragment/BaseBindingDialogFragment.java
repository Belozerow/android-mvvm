package com.appgranula.mvvmsample.fragment;

import android.app.Dialog;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.appgranula.mvvmsample.R;
import com.appgranula.mvvmsample.activity.BaseActivity;
import com.appgranula.mvvmsample.binding.DataBindingClassUtils;
import com.appgranula.mvvmsample.utils.TypeResolver;
import com.appgranula.mvvmsample.viewmodel.BaseFragmentViewModel;


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

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = (B) DataBindingClassUtils.getViewDataBinding(bindingClass, LayoutInflater.from(getActivity()), null);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), getStyle())
                .setTitle(getTitle())
                .setView(binding.getRoot())
                .create();
        createModel(savedInstanceState);
        onBeforeDialogCreated(alertDialog);
        return alertDialog;
    }

    protected int getStyle() {
        return R.style.AppTheme_Dialog;
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
        binding.setVariable(com.appgranula.mvvmsample.BR.viewModel, model);
        model.setApi(getBaseActivity().getApi());
        model.onViewCreated();
        onViewModelCreated(model);
        model.onModelAttached();
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected void onBeforeDialogCreated(Dialog dialog) {

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
}
