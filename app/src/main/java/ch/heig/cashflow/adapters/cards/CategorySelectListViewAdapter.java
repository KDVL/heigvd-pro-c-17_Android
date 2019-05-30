package ch.heig.cashflow.adapters.cards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.utils.ApplicationResources;

/**
 * Adapter for category display in list view
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.cards.CategorySelectListViewAdapter
 */
public class CategorySelectListViewAdapter extends ArrayAdapter<String> {
    private ApplicationResources appRes;
    private Context context;

    /**
     * Constructor
     *
     * @param context    context of application
     * @param resource   resource layout
     * @param categories the list of categories
     */
    public CategorySelectListViewAdapter(Context context, int resource, List<String> categories) {
        super(context, resource, categories);
        this.context = context;
        appRes = new ApplicationResources(context);
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set of the
     *                    item whose view we want.
     * @param convertView convertView This value may be null.
     * @param parent      parent This value must never be null.
     * @return view This value will never be null.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_select_list_item, null);
        }
        String c = getItem(position);
        ImageView iconImage = v.findViewById(R.id.cat_select_image_view);
        TextView title = v.findViewById(R.id.cat_select_title);

        iconImage.setImageResource(appRes.getDrawableResIdByName(c));
        iconImage.getDrawable().setTint(appRes.getColor(R.color.black));

        title.setText(c.substring(4));

        return v;
    }
}
