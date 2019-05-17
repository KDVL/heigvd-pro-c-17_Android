/**
 * Adapter for category display in recycle view
 *
 * @authors Aleksandar Milenkovic
 * @version 1.0
 * @see ch.heig.cashflow.adapters.cards.CategoryFragmentAdapter
 */

package ch.heig.cashflow.adapters.cards;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.CategoryDetailsActivity;
import ch.heig.cashflow.adapters.categories.CategoryEditExpenseAdapter;
import ch.heig.cashflow.adapters.categories.CategoryEditIncomeAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.SimpleColor;

public class CategoryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ApplicationResources appRes;
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;

    private CategoryService.Callback callback;
    private Context context;
    private final List<Category> mCategories;

    private Long tabId;

    /**
     * Constructor
     * @param callback callback of service
     * @param context context
     * @param categoryList the list of category to display
     * @param tabId
     */
    public CategoryFragmentAdapter(CategoryService.Callback callback, Context context, List<Category> categoryList, Long tabId) {
        appRes = new ApplicationResources(context);
        this.callback = callback;
        this.context = context;
        mCategories = categoryList;
        this.tabId = tabId;
    }

    /**
     * Choose the appropriate view
     * @param index index of item
     * @return the appropriate view
     */
    @Override
    public int getItemViewType(int index) {
        //if (index % 2 == 0)
        //return LAYOUT_ONE;
        //else
        return LAYOUT_TWO;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == LAYOUT_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_simpletext_item, parent, false);
            viewHolder = new ViewHolderText(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recyclerlist_item, parent, false);
            viewHolder = new ViewHolderCategory(view);
        }

        return viewHolder;
    }

    /**
     *
     * @param viewHolder
     * @param index
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int index) {

        SimpleColor sp = new SimpleColor(context);
        final Category c = mCategories.get(index);

        if (viewHolder.getItemViewType() == LAYOUT_ONE) {
            ViewHolderText holder = (ViewHolderText) viewHolder;
            TextView textView = holder.categoryName;
            textView.setText(c.getName());

        } else {
            final ViewHolderCategory holder = (ViewHolderCategory) viewHolder;

            int imageId = appRes.getDrawableResIdByName(c.getIconName());

            if (imageId != 0) {
                holder.categoryIconName.setImageResource(imageId);
            }

            holder.categoryName.setText(c.getName());
            holder.categoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editCategory(mCategories.get(holder.getAdapterPosition()));
                }
            });

            int enabledImageID, colorEnabled, colorIcon;

            if (c.isEnabled()) {
                enabledImageID = appRes.getDrawableResIdByName("button_remove");
                colorEnabled = sp.get(R.color.red);
                colorIcon = sp.get(R.color.green);
            } else {
                enabledImageID = appRes.getDrawableResIdByName("button_add");
                colorEnabled = sp.get(R.color.green);
                colorIcon = sp.get(R.color.red);
            }

            holder.categoryIconName.getDrawable().setTint(colorIcon);

            holder.bouttonState.setImageResource(enabledImageID);
            holder.bouttonState.getDrawable().setTint(colorEnabled);
            holder.bouttonState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enableOrDisable(mCategories.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    /**
     * Launch the activity to change the category
     * @param c the category to edit
     */
    private void editCategory(Category c) {
        Intent catDetails = new Intent(context, CategoryDetailsActivity.class);

        String str = "N° tab = " + tabId;

        if (tabId == 0) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            catDetails.putExtra(context.getResources().getString(R.string.category_adapter_key), new CategoryEditExpenseAdapter(c));
        } else {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            catDetails.putExtra(context.getResources().getString(R.string.category_adapter_key), new CategoryEditIncomeAdapter(c));
        }
        context.startActivity(catDetails);
    }

    /**
     * Enable or disable categories
     * @param c the category to edit
     */
    private void enableOrDisable(final Category c) {
        final String title;
        String msg;

        if (c.isEnabled()) {
            title = appRes.getString(R.string.alerte_titre_desactivation);
            msg = "Voulez-vous vraiment désactiver la catégorie?";
        } else {
            title = appRes.getString(R.string.alerte_titre_activation);
            msg = "Voulez-vous vraiment activer la catégorie?";
        }

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (title.equals("Désactivation!")) {
                            new CategoryService(callback).disable(c);
                        } else {
                            new CategoryService(callback).enable(c);
                        }
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                }).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });

        dialog.show();
    }

    /**
     * The count of items in recycler view
     * @return the size of list of categories
     */
    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    /*
    // TODO: Est-ce que ça sert à quelque chose ???
    public void refreshList(List<Category> newList) {
        this.mCategories.clear();
        this.mCategories.addAll(newList);
        notifyDataSetChanged();
    }
    */

    /**
     * Class of special element to present categories of different types
     */
    public class ViewHolderCategory extends RecyclerView.ViewHolder {
        public ImageView categoryIconName;
        public TextView categoryName;
        public ImageView bouttonState;

        public ViewHolderCategory(@NonNull View itemView) {
            super(itemView);

            categoryIconName = itemView.findViewById(R.id.cat_icon_name);
            categoryName = itemView.findViewById(R.id.cat_name);
            bouttonState = itemView.findViewById(R.id.cat_icon_enable_state);
        }
    }

    /**
     * Class of special element to separate categories of different types
     */
    static class ViewHolderText extends RecyclerView.ViewHolder {
        public TextView categoryName;

        public ViewHolderText(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.cat_state_title);
        }
    }
}
