/**
 * Activity for adding or editing a category
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.activites.CategoryDetailsActivity
 */

package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.categories.CategoryAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.Currency;
import ch.heig.cashflow.utils.SimpleColor;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryService.Callback {

    private static final String TAG = CategoryDetailsActivity.class.getSimpleName();

    private ApplicationResources appRes;
    private CategoryAddOrEditAdapter adapter;

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
        setContentView(R.layout.activity_category_details);

        appRes = new ApplicationResources(getContext());

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(getResources().getString(R.string.category_adapter_key))) {
                adapter = (CategoryAddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.category_adapter_key));
            }
        }

        setTitle(adapter.getViewTitle(getApplicationContext()));

        ImageView categoryIconName = findViewById(R.id.cat_edit_icon);
        TextView categoryName = findViewById(R.id.cat_edit_category_name);
        TextView categoryType = findViewById(R.id.cat_edit_type);
        TextView categoryQuota = findViewById(R.id.cat_edit_quota);

        int iconImageId = appRes.getDrawableResIdByName(adapter.getCategory().getIconName());
        if (iconImageId != 0) {
            categoryIconName.setImageResource(iconImageId);
            categoryIconName.getDrawable().setTint(new SimpleColor(getContext()).get(R.color.white));
        }

        categoryName.setText(adapter.getCategory().getName());
        categoryType.setText(String.valueOf(adapter.getCategory().getType()));
        categoryQuota.setText(Currency.format(adapter.getCategory().getQuota()));

    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category_edit, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.category_edit) {
            edit();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launch activity AddOrEditCategoryActivity
     */
    private void edit() {
        Intent catAddOrEdit = new Intent(this, AddOrEditCategoryActivity.class);
        catAddOrEdit.putExtra(appRes.getString(R.string.category_adapter_key), adapter);
        startActivity(catAddOrEdit);
    }

    /**
     * Return off call API if failed
     *
     * @param error The error message
     */
    @Override
    public void connectionFailed(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    /**
     * Used by service
     *
     * @return Context The context of the application
     */
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * Needed by service but not used
     *
     * @param category The category
     */
    @Override
    public void getFinished(Category category) {

    }

    /**
     * Needed by service but not used
     *
     * @param isFinished The operation finished flag
     */
    @Override
    public void operationFinished(boolean isFinished) {

    }

}
