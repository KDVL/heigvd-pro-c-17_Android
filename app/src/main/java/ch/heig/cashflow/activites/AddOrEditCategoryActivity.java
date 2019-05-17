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

public class AddOrEditCategoryActivity extends AppCompatActivity implements CategoryService.Callback {

    private CategoryAddOrEditAdapter adapter = null;

    @BindView(R.id.cat_icon)
    ImageView categoryIcon;
    @BindView(R.id.cat_input_name)
    EditText categoryName;
    @BindView(R.id.cat_input_quota)
    EditText categoryQuota;
    @BindView(R.id.cat_btn_add)
    Button addButton;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_category);

        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i != null) {
            adapter = (CategoryAddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.category_adapter_key));
        }

        setTitle(adapter.getViewTitle(getApplicationContext()));

        int iconImageId = getDrawableResIdByName(adapter.getCategory().getIconName());
        categoryIcon.setImageResource(iconImageId);

        if (adapter.getCategory() != null) {
            categoryName.setText(adapter.getCategory().getName());
            categoryQuota.setText(String.valueOf(adapter.getQuota()));
        }
    }

    /**
     * save data
     *
     * @param view the view
     */
    public void save(View view) {

        String quotaText = categoryQuota.getText().toString();

        if (quotaText.equals("")) {
            Toast.makeText(getApplicationContext(), "Quota pas saisi!", Toast.LENGTH_LONG).show();
            return;
        }
        if (quotaText.length() > 7) {
            Toast.makeText(getApplicationContext(), "Max 7 caractères depassé", Toast.LENGTH_LONG).show();
            return;
        }

        long quota = Long.valueOf(quotaText);

        if (quota <= 0) {
            Toast.makeText(getApplicationContext(), "Montant non conforme", Toast.LENGTH_LONG).show();
            return;
        }

        String categoryNameText = categoryName.getText().toString();

        if (categoryNameText == null || categoryNameText == "") {
            Toast.makeText(getApplicationContext(), "Le nom de catégorie non conforme", Toast.LENGTH_LONG).show();
            return;

        }

        addButton.setEnabled(false);

        //set informations
        adapter.getCategory().setName(categoryNameText);
        adapter.getCategory().setQuota(quota * 100);

        // do edit or add
        adapter.performAction(this);
    }

    // Find Image ID corresponding to the name of the image (in the directory drawable).
    public int getDrawableResIdByName(String resName) {
        String pkgName = getContext().getPackageName();
        // Return 0 if not found.
        return getContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    @Override
    public void getFinished(Category category) {

    }

    @Override
    public void operationFinished(boolean isFinished) {
        if (isFinished) {
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        } else {
            connectionFailed("");
        }
    }

    // TODO: checker utilisation de connctionFailed via operationFinished
    @Override
    public void connectionFailed(String error) {
        addButton.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Impossible d'effectuer cette opération, vérifiez vos saisies", Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
