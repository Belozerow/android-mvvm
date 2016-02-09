package com.appgranula.mvvmsample.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appgranula.mvvmsample.activity.BaseActivity;
import com.appgranula.mvvmsample.binding.DataBindingClassUtils;
import com.appgranula.mvvmsample.utils.TypeResolver;
import com.appgranula.mvvmsample.viewmodel.BaseFragmentViewModel;


/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 10.11.2015
 */
public abstract class BaseBindingFragment<B extends ViewDataBinding, M extends BaseFragmentViewModel> extends Fragment {
    public static final String ADD_TO_BACK_STACK = "add_to_back_stack";
    private static final String EXTRA_MODEL = "extra_model";
    private final Class<B> bindingClass;
    private final Class<M> modelClass;
    private B binding;
    private M model;


    @SuppressWarnings("unchecked")
    public BaseBindingFragment() {
        super();
        Class<?>[] typeArguments = TypeResolver.resolveRawArguments(BaseBindingFragment.class, getClass());
        this.bindingClass = (Class<B>) typeArguments[0];
        this.modelClass = (Class<M>) typeArguments[1];
    }

    @Override
    public void onResume() {
        super.onResume();
        model.onResume();
    }

    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    protected B getBinding() {
        return binding;
    }

    @SuppressWarnings("unused")
    public M getModel() {
        return model;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = (B) DataBindingClassUtils.getViewDataBinding(bindingClass, inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createModel(savedInstanceState);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
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
        model.onViewCreated();
        onViewModelCreated(model);
        model.onModelAttached();
    }

    protected void onViewModelCreated(M model) {

    }

    @SuppressWarnings("unused")
    public void replaceFragment(BaseBindingFragment fragment) {
        getBaseActivity().replaceFragment(fragment, false);
    }

    public void replaceFragmentWithBackStack(BaseBindingFragment fragment) {
        getBaseActivity().replaceFragment(fragment, true);
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

    public boolean onBackPressed() {
        return false;
    }
}