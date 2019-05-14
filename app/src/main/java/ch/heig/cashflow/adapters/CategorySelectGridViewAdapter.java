package ch.heig.cashflow.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;

public class CategorySelectGridViewAdapter extends ArrayAdapter<Category> {
    private Context context;

    public CategorySelectGridViewAdapter(Context context, int resource, List<Category> categories) {
        super(context, resource, categories);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_select_grid_item, null);
        }
        Category c = getItem(position);
        ImageView iconImage = v.findViewById(R.id.cat_select_image_view);
        TextView title = v.findViewById(R.id.cat_select_title);

        int iconImageId = this.getDrawableResIdByName(c.getIconName());
        iconImage.setImageResource(iconImageId);
        iconImage.getDrawable().setTint(Color.parseColor("#000000"));

        title.setText(c.getName());

        return v;
    }

    // Find Image ID corresponding to the name of the image (in the directory drawable).
    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        return context.getResources().getIdentifier(resName, "drawable", pkgName);
    }
}
