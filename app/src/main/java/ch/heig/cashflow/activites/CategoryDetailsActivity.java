package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.SimpleColor;
import ch.heig.cashflow.adapters.CategoryAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryService.Callback, CategoriesService.Callback {
    private static final String TAG = "TransactionDetailsActivity";

    private CategoryService cs;
    private CategoriesService css;

    private CategoryAddOrEditAdapter adapter = null;

    private ImageView categoryIconName = null;
    private TextView categoryName = null;
    private TextView categoryType = null;
    private TextView categoryQuota = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(getResources().getString(R.string.category_adapter_key))) {
                adapter = (CategoryAddOrEditAdapter) i.getSerializableExtra(getResources().getString(R.string.category_adapter_key));
            }
        }

        cs = new CategoryService(this);
        css = new CategoriesService(this);

        // TODO: A voir ???
        adapter.setCallbackCategory(this);
        adapter.setCallbackCategory(this);

        setTitle(adapter.getViewTitle(getApplicationContext()));


        categoryIconName = findViewById(R.id.cat_edit_icon);
        categoryName = findViewById(R.id.cat_edit_category_name);
        categoryType = findViewById(R.id.cat_edit_type);
        categoryQuota = findViewById(R.id.cat_edit_quota);

        int iconImageId = this.getDrawableResIdByName(adapter.getCategory().getIconName());
        if (iconImageId != 0) {
            categoryIconName.setImageResource(iconImageId);
            categoryIconName.getDrawable().setTint(new SimpleColor(getContext()).get(R.color.white));
        }

        categoryName.setText(adapter.getCategory().getName());
        categoryType.setText(String.valueOf(adapter.getCategory().getType()));
        categoryQuota.setText(String.valueOf(adapter.getCategory().getQuota()));

    }

    public int getDrawableResIdByName(String resName) {
        String pkgName = getApplicationContext().getPackageName();
        // Return 0 if not found.
        return getApplicationContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu _menu) {
        getMenuInflater().inflate(R.menu.menu_expense_edit, _menu);
        return super.onCreateOptionsMenu(_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case R.id.expense_edit:
                edit();
                return true;

            case R.id.expense_delete:
                delete();
                return true;

            default:
                return super.onOptionsItemSelected(_item);

        }
    }

    private void edit() {
        Intent catAdd = new Intent(this, AddOrEditCategoryActivity.class);
        catAdd.putExtra(getResources().getString(R.string.category_adapter_key), adapter);
        startActivity(catAdd);
        finish();
    }

    private void delete() {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer la category?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        cs.disable(adapter.getCategory());
                        finish();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();

        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        dialog.show();
    }


    //TODO: Gerer Calback
    @Override
    public void getFinished(Category category) {

    }

    @Override
    public void operationFinished(boolean isFinished) {

    }

    @Override
    public void connectionFailed(String error) {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }


    @Override
    public void getFinished(List<Category> categories) {

    }
}
