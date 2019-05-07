package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    private CategoriesService css;
    private CategoryService cs;
    private List<Category> categoriesEnabledList;
    private List<Category> categoriesDisabledList;
    private ListView catEnabledListView;
    private ListView catDisabledListView;

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

        categoriesEnabledList = new ArrayList<>();
        categoriesDisabledList = new ArrayList<>();

        css = new CategoriesService(this);
        cs = new CategoryService(this);


        catEnabledListView = view.findViewById(R.id.cat_enabled_listview);
        catDisabledListView = view.findViewById(R.id.cat_disabled_listview);

        css.getType(Type.EXPENSE);

        catEnabledListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = catEnabledListView.getItemAtPosition(position);
                Category c = (Category) o;
                c.setEnabled(!c.isEnabled());
                delete(c);
            }
        });

        catDisabledListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = catDisabledListView.getItemAtPosition(position);
                Category c = (Category) o;
                c.setEnabled(!c.isEnabled());
                add(c);
            }
        });

        return view;
    }

    private void add(Category c) {
        cs.add(c);
        css.getType(Type.EXPENSE);
    }

    private void delete(Category c) {
        cs.delete(c);
        css.getType(Type.EXPENSE);
    }

    private synchronized void refresh(List<Category> categories) {
        categoriesEnabledList.clear();
        categoriesDisabledList.clear();

        for (Category ec : categories)
            if (ec.isEnabled())
                categoriesEnabledList.add(ec);

        boolean alreadyExist = false;
        for (Category dc : categories) {
            for (Category c : categoriesEnabledList) {
                if (c.getName().equals(dc.getName())) {
                    alreadyExist = true;
                    break;
                }
            }
            if (!alreadyExist)
                categoriesDisabledList.add(dc);
            else
                alreadyExist = false;
        }

        catEnabledListView.setAdapter(new CategorySelectAdapter(getActivity(), categoriesEnabledList));
        catDisabledListView.setAdapter(new CategorySelectAdapter(getActivity(), categoriesDisabledList));
    }

    // TODO : Gerer Callbacks

    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getActivity().getApplicationContext(), "Problème connexion!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getFinished(List<Category> categories) {
        refresh(categories);
    }

    /**
     * Retour de la methode GET
     *
     * @param category
     */
    @Override
    public void getFinished(Category category) {
    }

    /**
     * Retour après POST, PUT et DELETE auprès du serveur
     *
     * @param isFinished
     */
    @Override
    public void operationFinished(boolean isFinished) {
        String msg = "Résultat de la mise à jour : " + isFinished;
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
