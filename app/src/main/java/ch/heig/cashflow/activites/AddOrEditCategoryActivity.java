/**
 * Activity for adding or editing a category
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.categories.CategoryAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.utils.ApplicationResources;

public class AddOrEditCategoryActivity extends AppCompatActivity implements CategoryService.Callback {

    private static final String TAG = AddOrEditCategoryActivity.class.getSimpleName();

    @BindView(R.id.cat_icon)
    ImageView categoryIcon;
    @BindView(R.id.cat_input_name)
    EditText categoryName;
    @BindView(R.id.cat_input_quota)
    EditText categoryQuota;
    @BindView(R.id.cat_btn_add)
    Button addButton;

    private ApplicationResources appRes;
    private CategoryAddOrEditAdapter adapter = null;

    /**
     * Called when The activity is starting
     *
     * @param savedInstanceState If The activity is being re-initialized after previously being
     *                           shut down Then this Bundle contains The data it most recently supplied
     *                           in onSaveInstanceState(Bundle). Note: OTherwise it is null. This value may be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_category);

        appRes = new ApplicationResources(getContext());

        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i != null) {
            adapter = (CategoryAddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.category_adapter_key));
        }

        setTitle(adapter.getViewTitle(getApplicationContext()));

        categoryIcon.setImageResource(appRes.getDrawableResIdByName(adapter.getCategory().getIconName()));

        if (adapter.getCategory() != null) {
            categoryName.setText(adapter.getCategory().getName());
            categoryQuota.setText(String.valueOf(adapter.getCategory().getQuota() / 100));
        }
    }

    /**
     * Save datas
     *
     * <p>
     * For the display we divide by 100 the quota.
     * When the user enters a quota, it is multiplied by 100.
     *
     * @param view The view
     */
    public void save(View view) {

        String categoryNameText = categoryName.getText().toString();

        if (categoryNameText.equals("")) {
            Toast.makeText(getApplicationContext(), appRes.getString(R.string.category_name_input), Toast.LENGTH_LONG).show();
            return;

        }


        String quotaText = categoryQuota.getText().toString();

        if (quotaText.equals("")) {
            Toast.makeText(getApplicationContext(), appRes.getString(R.string.quota_input), Toast.LENGTH_LONG).show();
            return;
        }
        if (quotaText.length() > 7) {
            Toast.makeText(getApplicationContext(), appRes.getString(R.string.max_size_input), Toast.LENGTH_LONG).show();
            return;
        }

        long quota = Long.valueOf(quotaText);

        if (quota <= 0) {
            Toast.makeText(getApplicationContext(), appRes.getString(R.string.amount_input), Toast.LENGTH_LONG).show();
            return;
        }

        addButton.setEnabled(false);

        //set informations
        adapter.getCategory().setName(categoryNameText);
        adapter.getCategory().setQuota(quota * 100);

        // do edit or add
        adapter.performAction(this);
    }

    /**
     * Return off call API POST PUT and DELETE
     *
     * @param isFinished The result
     */
    @Override
    public void operationFinished(boolean isFinished) {
        if (isFinished) {
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        } else {
            connectionFailed(appRes.getString(R.string.server_response_nok));
        }
    }

    /**
     * Return off call API if failed
     *
     * @param error The error message
     */
    @Override
    public void connectionFailed(String error) {
        addButton.setEnabled(true);
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
}
