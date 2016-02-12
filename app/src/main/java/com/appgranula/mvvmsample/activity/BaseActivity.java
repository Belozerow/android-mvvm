package com.appgranula.mvvmsample.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.MenuItem;

import com.appgranula.mvvmsample.R;
import com.appgranula.mvvmsample.api.Api;
import com.appgranula.mvvmsample.api.ApiFragment;
import com.appgranula.mvvmsample.fragment.BaseBindingFragment;

/**
 * Created: Belozerov
 * Company: APPGRANULA LLC
 * Date: 25.12.2015
 */
public class BaseActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());

        if (savedInstanceState == null) {
            createApiFragment();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    public Api getApi() {
        if (!hasApiContainer()) {
            throw new UnsupportedOperationException("You should add api fragment container to your layout. Default id is \"fragmentApiContainer\". Please, use default layout R.layout.activity_base if it's possible.");
        }
        return (ApiFragment) getSupportFragmentManager().findFragmentById(getApiFragmentContainerId());
    }

    private void createApiFragment() {
        if (!hasApiContainer())
            return;
        getSupportFragmentManager().beginTransaction()
                .replace(getApiFragmentContainerId(), ApiFragment.newInstance())
                .commit();
    }

    protected int getContentLayout() {
        return R.layout.activity_base;
    }

    protected int getApiFragmentContainerId() {
        return R.id.fragmentApiContainer;
    }

    protected int getFragmentContainerId() {
        return R.id.fragmentContainer;
    }

    public void replaceFragment(BaseBindingFragment fragment, boolean addToBackStack) {
        if (!hasFragmentContainer()) {
            throw new UnsupportedOperationException("You should add fragment container to your layout. Default id is \"fragmentContainer\". Please, use default layout R.layout.activity_base if it's possible.");
        }
        Bundle arguments;
        if (fragment.getArguments() == null) {
            arguments = new Bundle();
        } else {
            arguments = fragment.getArguments();
        }
        arguments.putBoolean(BaseBindingFragment.ADD_TO_BACK_STACK, addToBackStack);
        fragment.setArguments(arguments);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 21) {
            fragment.setEnterTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.slide_right));
            fragment.setExitTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.fade));
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getFragmentTag());
        }
        fragmentTransaction.replace(getFragmentContainerId(), fragment, fragment.getFragmentTag());
        fragmentTransaction.commitAllowingStateLoss();
    }

    public BaseBindingFragment getLastFragment() {
        if (!hasFragmentContainer()) {
            throw new UnsupportedOperationException("You should add fragment container to your layout. Default id is \"fragmentContainer\". Please, use default layout R.layout.activity_base if it's possible.");
        }
        return (BaseBindingFragment) getSupportFragmentManager().findFragmentById(getFragmentContainerId());
    }

    private boolean hasApiContainer() {
        return findViewById(getApiFragmentContainerId()) != null;
    }

    private boolean hasFragmentContainer() {
        return findViewById(getFragmentContainerId()) != null;
    }

    @Override
    public void onBackPressed() {
        BaseBindingFragment lastFragment = getLastFragment();
        if (lastFragment != null && lastFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
        if (hasApiContainer() && hasFragmentContainer()) {
            if (getLastFragment() != null) {
                getApi().clearCacheExcept(getLastFragment().getModel().getModelTag());
            }
        }
    }
}
