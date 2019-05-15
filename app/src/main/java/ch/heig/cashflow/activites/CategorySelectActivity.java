package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.CategorySelectGridViewAdapter;
import ch.heig.cashflow.adapters.CategorySelectListViewAdapter;
import ch.heig.cashflow.adapters.categories.CategoryAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;

public class CategorySelectActivity extends AppCompatActivity implements CategoriesService.Callback {
    private static final String TAG = "CategorySelectActivity";
    private List<String> categoriesList;

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private CategorySelectListViewAdapter listViewAdapter;
    private CategorySelectGridViewAdapter gridViewAdapter;

    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    private CategoryAddOrEditAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        categoriesList = new ArrayList<>();

        setTitle("Choix de la catégorie");

        Intent intent = getIntent();
        if (intent != null) {
            adapter = (CategoryAddOrEditAdapter) intent.getSerializableExtra(getResources().getString(R.string.category_adapter_key));
        }

        stubList = findViewById(R.id.stub_list);
        stubGrid = findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view
        stubList.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.cat_select_listview);
        gridView = findViewById(R.id.cat_select_gridview);

        //get list of categories
        //adapter.loadCategories();

        // récuperer les icones de android mettre android.R
        Field[] drawables = R.drawable.class.getFields();
        String tab[] = new String[300];
        int i = 0;

        for (Field f : drawables) {
            try {
                if (f.getName().startsWith("cat_"))
                    categoriesList.add(f.getName());
                System.out.println("R.drawable." + f.getName());
                tab[i++] = f.getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // TODO lire dravable pour les catégories

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }


    private void switchView() {

        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new CategorySelectListViewAdapter(this, R.layout.category_select_list_item, categoriesList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new CategorySelectGridViewAdapter(this, R.layout.category_select_grid_item, categoriesList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String c = categoriesList.get(position);
            Log.i(TAG, c);
            adapter.getCategory().setIconName(c);
            saveCategory();
        }
    };

    public void saveCategory() {
        Intent addOrEditCategory = new Intent(this, AddOrEditCategoryActivity.class);
        addOrEditCategory.putExtra(getResources().getString(R.string.category_adapter_key), adapter);
        startActivity(addOrEditCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.switch_view:
                if (VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();

                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
        }
        return true;
    }

    @Override
    public void getFinished(List<Category> categories) {
    }

    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
