package ch.heig.cashflow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.AddOrEditCategoryActivity;
import ch.heig.cashflow.adapters.CategoryAddExpenseAdapter;
import ch.heig.cashflow.adapters.CategoryFragmentAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.DividerItemDecoration;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;

public class CategoriesFragmentTabHost extends Fragment {

    private static final String TAG = CategoriesFragmentTabHost.class.getSimpleName();

    private FragmentTabHost fragmentTabHost;

    public CategoriesFragmentTabHost() {
    }

    public static CategoriesFragmentTabHost newInstance() {
        return new CategoriesFragmentTabHost();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_category_tabhost, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.category_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.actionbar_add_category:
                Intent catAdd = new Intent(getContext(), AddOrEditCategoryActivity.class);
                catAdd.putExtra(getResources().getString(R.string.category_adapter_key), new CategoryAddExpenseAdapter());
                startActivity(catAdd);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        fragmentTabHost = view.findViewById(android.R.id.tabhost);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");

        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("Dépenses").setIndicator("Dépenses"), CategoryExpenseFragment.class, null);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("Revenus").setIndicator("Revenus"), CategoryIncomeFragment.class, null);
    }


    /**
     * Classe pour TABHOST fragment n°1
     */
    public static class CategoryExpenseFragment extends Fragment implements CategoriesService.Callback,
            CategoryService.Callback {

        private static final String TAG = CategoryExpenseFragment.class.getSimpleName();

        private CategoriesService css;
        private CategoryService cs;
        private List<Category> categoriesList;
        private RecyclerView recyclerView;

        private View view;

        public CategoryExpenseFragment() {
            // Required empty public constructor
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            Log.i(TAG, "onCreateView: ");
            view = inflater.inflate(R.layout.fragment_category, container, false);

            categoriesList = new ArrayList<>();

            css = new CategoriesService(this);
            cs = new CategoryService(this);

            // Lookup the recyclerview in activity layout
            recyclerView = view.findViewById(R.id.cat_expense_recyclerview);
            recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), R.drawable.divider));

            // Call list of category from server
            updateListeFromServer(Type.EXPENSE);

            return view;
        }

        private void updateListeFromServer(Type type) {
            css.getType(type);
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
            CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(this, view.getContext(), categoriesList);
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
                updateListeFromServer(Type.EXPENSE);
            }
        }

        @Override
        public void connectionFailed(String error) {
            Toast.makeText(getActivity().getApplicationContext(), "Problème de connexion!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Classe pour TABHOST fragment n°2
     */
    public static class CategoryIncomeFragment extends Fragment {

        private static final String TAG = CategoryIncomeFragment.class.getSimpleName();

        public CategoryIncomeFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            Log.i(TAG, "onCreateView: ");
            return inflater.inflate(R.layout.fragment_category, container, false);
        }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.i(TAG, "onViewCreated: ");
        }

    }
}
