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

public class CategorySelectAdapter extends ArrayAdapter<Category> {
    private static final String TAG = "CategorySelectAdapter";

    List<Category> myList;

    public CategorySelectAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
        myList = categories;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_select_listitem, null);
        }

        Category category = getItem(position);
        ImageView iconImage = v.findViewById(R.id.catSelectIcon);
        TextView title = v.findViewById(R.id.catSelectName);
        ImageView etatImage = v.findViewById(R.id.catSelectActiveIcon);

        int iconImageId = this.getDrawableResIdByName(category.getIconName());
        iconImage.setImageResource(iconImageId);
        iconImage.getDrawable().setTint(Color.parseColor("#222222"));

        title.setText(category.getName());

        int etatImageID = 0;
        int color = 0;

        if (category.isEnabled()) {
            etatImageID = this.getDrawableResIdByName("cat_button_remove");
            color = Color.RED;
        } else {
            etatImageID = this.getDrawableResIdByName("cat_button_add");
            color = Color.GREEN;
        }

        etatImage.setImageResource(etatImageID);
        etatImage.getDrawable().setTint(color);

        return v;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getDrawableResIdByName(String resName) {
        String pkgName = getContext().getPackageName();
        // Return 0 if not found.
        return getContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    public void refreshList(List<Category> newList) {
        this.myList.clear();
        this.myList.addAll(newList);
        notifyDataSetChanged();
    }
}