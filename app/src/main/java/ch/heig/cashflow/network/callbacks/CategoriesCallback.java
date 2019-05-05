package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import java.util.List;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;

public class CategoriesCallback implements CategoriesService.Callback {

    Context context;
    ServicesFragment fragment;
    CategoriesService cs;

    public CategoriesCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.cs = new CategoriesService(this);
    }

    public void getAll() {
        cs.getAll();
    }

    public void getType(Type type) {
        cs.getType(type);
    }

    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - CategoriesService " + error );
    }

    @Override
    public void getFinished(List<Category> categories) {
        if (categories.size() < 1)
            System.err.println("EMPTY LIST - CategoriesService");
        else
            System.out.println("OK    CategoriesService found " + categories);
    }

    @Override
    public Context getContext() {
        return context;
    }
}