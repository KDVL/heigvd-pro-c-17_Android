package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.CategorySelectAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;


public class CategoryFragment extends Fragment implements CategoriesService.Callback,
        CategoryService.Callback {
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
        // TODO: update ne marche pas
        /*
        cs.update(c);
        categoriesList.clear();
        css.getType(Type.EXPENSE);
        */

        catSelectListView.setAdapter(new CategorySelectAdapter(getActivity(), categoriesList));
    }

    // TODO : Gerer Callbacks

    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public void getFinished(List<Category> categories) {
        //TODO: Pas de retour pour le moment ajout manuel
        //categoriesList = categories;

        categoriesList = new ArrayList<>();
        categoriesList.add(new Category(1, "Boisson", "cat_drink", Type.EXPENSE, 200, true));
        categoriesList.add(new Category(2, "Voiture", "cat_cars", Type.EXPENSE, 200, true));
        categoriesList.add(new Category(3, "Enfants", "cat_child", Type.EXPENSE, 200, true));
        categoriesList.add(new Category(4, "Aliments", "cat_aliments", Type.EXPENSE, 200, true));

        catSelectListView.setAdapter(new CategorySelectAdapter(getActivity(), categoriesList));
        getActivity().setTitle("Liste de catégories");
    }

    @Override
    public void getFinished(Category category) {

    }

    @Override
    public void operationFinished(boolean isFinished) {

    }

}
