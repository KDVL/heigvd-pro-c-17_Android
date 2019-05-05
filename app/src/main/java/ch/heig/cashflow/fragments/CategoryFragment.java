package ch.heig.cashflow.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.CategorySelectAdapter;
import ch.heig.cashflow.adapters.ExpenseCardsAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.network.services.TransactionsService;


public class CategoryFragment extends Fragment implements CategoriesService.Callback,
        CategoryService.Callback  {
    private static final String TAG = "CategoryFragment";

    private CategoriesService css = null;
    private CategoryService cs = null;
    private List<Category> categoriesList;
    private ListView catSelectListView;

    // TODO: Observable classe date update changement

    private View view;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);

        css = new CategoriesService(this);
        cs = new CategoryService(this);

        catSelectListView = view.findViewById(R.id.catSelectListView);

        css.getType(Type.EXPENSE);

        catSelectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = catSelectListView.getItemAtPosition(position);
                Category c = (Category) o;
                c.setEnabled(!c.isEnabled());

                setActive(c);
            }
        });

        return view;
    }

    private void setActive(Category c) {
        cs.update(c);
        categoriesList.clear();
        css.getType(Type.EXPENSE);
    }

    // TODO : Gerer Callbacks

    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public void getFinished(Category category) {

    }

    @Override
    public void addFinished(boolean isAdded) {

    }

    @Override
    public void updateFinished(boolean isUpdated) {

    }

    @Override
    public void deleteFinished(boolean isDeleted) {

    }

    @Override
    public void getAllFinished(List<Category> categories) {

    }

    @Override
    public void getTypeFinished(List<Category> categories) {
        categoriesList = categories;
        catSelectListView.setAdapter(new CategorySelectAdapter(getActivity(), categoriesList));
        getActivity().setTitle("Liste de catégories");
    }

    @Override
    public Context getContext() {
        return getContext();
    }

}
