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

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.CategoryDetailsActivity;
import ch.heig.cashflow.adapters.categories.CategoryEditExpenseAdapter;
import ch.heig.cashflow.adapters.categories.CategoryEditIncomeAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.Type;

/**
 * Adapter for category display in recycle view
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.cards.CategoryFragmentAdapter
 */
public class CategoryFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ApplicationResources appRes;
    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;

    private CategoryService.Callback callback;
    private Context context;
    private final List<Category> mCategories;
    private List<Category> orderedCategories;

    private Long tabId;

    /**
     * Constructor
     *
     * @param callback     callback of service
     * @param context      context
     * @param categoryList the list of category to display
     * @param tabId        number of tab
     */
    public CategoryFragmentAdapter(CategoryService.Callback callback, Context context, List<Category> categoryList, Long tabId) {
        appRes = new ApplicationResources(context);
        this.callback = callback;
        this.context = context;
        mCategories = categoryList;

        orderedCategories = new ArrayList<>();
        orderedCategories.add(new Category(0, "Activées", "enabled", Type.EXPENSE, 0, true));
        for (Category ac : mCategories) {
            if (ac.isEnabled())
                orderedCategories.add(ac);
        }

        orderedCategories.add(new Category(0, "Désactivées", "disabled", Type.EXPENSE, 0, true));
        for (Category dc : mCategories) {
            if (!dc.isEnabled())
                orderedCategories.add(dc);
        }

        this.tabId = tabId;
    }

    /**
     * Choose the appropriate view
     *
     * @param index index of item
     * @return the appropriate view
     */
    @Override
    public int getItemViewType(int index) {
        if (orderedCategories.get(index).getIconName().equals("enabled") ||
                orderedCategories.get(index).getIconName().equals("disabled"))
            return LAYOUT_ONE;
        else
            return LAYOUT_TWO;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder
     * of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;

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
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the item at
     * the given position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents
     *                   of the item at the given position in the data set.
     * @param index      The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int index) {
        final Category c = orderedCategories.get(index);

        if (viewHolder.getItemViewType() == LAYOUT_ONE) {
            ViewHolderText holder = (ViewHolderText) viewHolder;
            TextView textView = holder.categoryName;
            textView.setText(c.getName());
            if (c.getIconName().equals("enabled")) {
                textView.setTextColor(appRes.getColor(R.color.green));
            } else {
                textView.setTextColor(appRes.getColor(R.color.red));
            }

        } else {
            final ViewHolderCategory holder = (ViewHolderCategory) viewHolder;

            int imageId = appRes.getDrawableResIdByName(c.getIconName());

            if (imageId != 0) {
                holder.categoryIconName.setImageResource(imageId);
                holder.categoryIconName.getDrawable().setTint(appRes.getColor(R.color.black));
            }

            holder.categoryName.setText(c.getName());
            holder.categoryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editCategory(orderedCategories.get(holder.getAdapterPosition()));
                }
            });

            int enabledImageID, colorEnabled;

            if (c.isEnabled()) {
                enabledImageID = appRes.getDrawableResIdByName("button_remove");
                colorEnabled = appRes.getColor(R.color.red);
            } else {
                enabledImageID = appRes.getDrawableResIdByName("button_add");
                colorEnabled = appRes.getColor(R.color.green);
            }

            holder.bouttonState.setImageResource(enabledImageID);
            holder.bouttonState.getDrawable().setTint(colorEnabled);
            holder.bouttonState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enableOrDisable(orderedCategories.get(holder.getAdapterPosition()));
                }
            });
        }
    }

    /**
     * Launch the activity to change the category
     *
     * @param c the category to edit
     */
    private void editCategory(Category c) {
        Intent catDetails = new Intent(context, CategoryDetailsActivity.class);

        if (tabId == 0) {
            catDetails.putExtra(context.getResources().getString(R.string.category_adapter_key), new CategoryEditExpenseAdapter(c));
        } else {
            catDetails.putExtra(context.getResources().getString(R.string.category_adapter_key), new CategoryEditIncomeAdapter(c));
        }
        context.startActivity(catDetails);
    }

    /**
     * Enable or disable categories
     *
     * @param c the category to edit
     */
    private void enableOrDisable(final Category c) {
        final String title;
        String msg;

        if (c.isEnabled()) {
            title = appRes.getString(R.string.alert_des_title);
            msg = appRes.getString(R.string.alert_des_msg);
        } else {
            title = appRes.getString(R.string.alert_act_title);
            msg = appRes.getString(R.string.alert_act_msg);
        }

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(appRes.getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (title.equals(appRes.getString(R.string.alert_des_title))) {
                            new CategoryService(callback).disable(c);
                        } else {
                            new CategoryService(callback).enable(c);
                        }
                    }
                })
                .setNegativeButton(appRes.getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
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
     *
     * @return the size of list of categories
     */
    @Override
    public int getItemCount() {
        return orderedCategories.size();
    }

    /**
     * Class of special element to present categories of different types
     */
    public class ViewHolderCategory extends RecyclerView.ViewHolder {
        ImageView categoryIconName;
        TextView categoryName;
        ImageView bouttonState;

        ViewHolderCategory(@NonNull View itemView) {
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
        TextView categoryName;

        ViewHolderText(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.cat_state_title);
        }
    }
}
