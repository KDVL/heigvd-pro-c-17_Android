package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.categories.CategoryFragmentAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.utils.DividerItemDecoration;
import ch.heig.cashflow.utils.Type;


/**
 * Classe pour TABHOST fragments
 */
public class CategoryFragment extends Fragment implements CategoriesService.Callback,
        CategoryService.Callback {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private CategoriesService css;
    private CategoryService cs;
    private List<Category> categoriesList;
    private RecyclerView recyclerView;

    private View view;

    private Long tabId;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        view = inflater.inflate(R.layout.fragment_category, container, false);

        Bundle b = getArguments();
        tabId = b.getLong("index");

        categoriesList = new ArrayList<>();

        css = new CategoriesService(this);
        cs = new CategoryService(this);

        // Lookup the recyclerview in activity layout
        recyclerView = view.findViewById(R.id.cat_type_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), R.drawable.divider));

        // Call list of category from server
        updateListeFromServer(tabId);

        return view;
    }

    private void updateListeFromServer(Long tabId) {
        if (tabId == 0)
            css.getType(Type.EXPENSE);
        else
            css.getType(Type.INCOME);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
    }

    /**
     * Méthode synchronisé pour gérer les spam boutton
     *
     * @param categories
     */
    private synchronized void refresh(List<Category> categories) {
        categoriesList = categories;
        Toast.makeText(getActivity().getApplicationContext(), "Liste des catégories récuperée du serveur!", Toast.LENGTH_SHORT).show();
        // Create adapter passing in the sample user data
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(this, view.getContext(), categoriesList, tabId);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // That's all!
    }

    // TODO : Gerer Callbacks
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
        if (isFinished) {
            Toast.makeText(getActivity().getApplicationContext(), "Mise à jour reussi!", Toast.LENGTH_SHORT).show();
            updateListeFromServer(tabId);
        }
    }

    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getActivity().getApplicationContext(), "Problème de connexion!", Toast.LENGTH_SHORT).show();
    }
}