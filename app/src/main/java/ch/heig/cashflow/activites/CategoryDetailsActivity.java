package ch.heig.cashflow.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.SimpleColor;
import ch.heig.cashflow.adapters.CategoryAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryService.Callback {
    private static final String TAG = "CategoryDetailsActivity";

    private CategoryService cs;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category_edit:
                edit();
                return true;
            case R.id.category_delete:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void edit() {
        Intent catAddOrEdit = new Intent(this, AddOrEditCategoryActivity.class);
        catAddOrEdit.putExtra(getResources().getString(R.string.category_adapter_key), adapter);
        startActivity(catAddOrEdit);
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

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
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

}
