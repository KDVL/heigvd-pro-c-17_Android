/**
 * Activity for adding or editing a category
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.activites.CategorySelectActivity
 */

package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.cards.CategorySelectGridViewAdapter;
import ch.heig.cashflow.adapters.cards.CategorySelectListViewAdapter;
import ch.heig.cashflow.adapters.categories.CategoryAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.utils.ApplicationResources;

public class CategorySelectActivity extends AppCompatActivity implements CategoriesService.Callback {
    private List<String> categoriesList;

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;

    private int currentViewMode = 0;

    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    private CategoryAddOrEditAdapter adapter = null;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied
     *                           in onSaveInstanceState(Bundle). Note: Otherwise it is null. This value may be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);
        categoriesList = new ArrayList<>();

        ApplicationResources appRes = new ApplicationResources(getContext());

        setTitle(appRes.getString(R.string.title_cat_choice));

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
        Field[] drawables = R.drawable.class.getFields();

        for (Field f : drawables) {
            try {
                if (f.getName().startsWith(appRes.getString(R.string.cat_start)))
                    categoriesList.add(f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item lick
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }


    /**
     * Change the view after reading the user's preference
     */
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

    /**
     * Choose the right adapter after selecting the view
     */
    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listView.setAdapter(new CategorySelectListViewAdapter(this, R.layout.category_select_list_item, categoriesList));
        } else {
            gridView.setAdapter(new CategorySelectGridViewAdapter(this, R.layout.category_select_grid_item, categoriesList));
        }
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String c = categoriesList.get(position);
            adapter.getCategory().setIconName(c);
            saveCategory();
        }
    };

    /**
     * Launch activity AddOrEditCategoryActivity
     */
    public void saveCategory() {
        Intent addOrEditCategory = new Intent(this, AddOrEditCategoryActivity.class);
        addOrEditCategory.putExtra(getResources().getString(R.string.category_adapter_key), adapter);
        startActivity(addOrEditCategory);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category_select, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.switch_view) {
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
            editor.apply(); //TODO: commit or apply ???
        }
        return true;
    }

    //TODO: callbacks

    /**
     * Return of call API GET_All or GET_BY_TYPE
     *
     * @param categories list of categories
     */
    @Override
    public void getFinished(List<Category> categories) {
    }

    /**
     * Return off call API if failed
     *
     * @param error error message
     */
    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * @return the context of application
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
