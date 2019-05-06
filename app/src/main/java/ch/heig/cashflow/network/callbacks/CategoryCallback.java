package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public class CategoryCallback implements CategoryService.Callback {

    Context context;
    ServicesFragment fragment;
    CategoryService cs;

    public CategoryCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.cs = new CategoryService(this);
    }

    public void get(long id) {
        cs.get(id);
    }

    public void add(Category category) {
        cs.add(category);
    }

    public void update(Category category) {
        cs.update(category);
    }

    public void delete(Category category) {
        cs.delete(category);
    }

    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - CategoryService " + error);
    }

    @Override
    public void operationFinished(boolean isFinished) {
        System.out.println("OK    CategoryService found");
    }

    @Override
    public void getFinished(Category category) {
        if (category == null)
            System.err.println("EMPTY - CategoryService");
        else
            System.out.println("OK    CategoryService found " + category);
    }

    @Override
    public Context getContext() {
        return context;
    }
}