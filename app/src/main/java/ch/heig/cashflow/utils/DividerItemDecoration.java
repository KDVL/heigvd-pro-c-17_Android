/**
 * Class needed to add divider in recycle view
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.utils.DividerItemDecoration
 */

package ch.heig.cashflow.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable divider;

    /**
     * A DividerItemDecoration constructor
     *
     * <p>
     * Default divider will be used
     *
     * @param context The context of the application
     */
    public DividerItemDecoration(Context context) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
        divider = styledAttributes.getDrawable(0);
        styledAttributes.recycle();
    }

    /**
     * A DividerItemDecoration constructor
     *
     * <p>
     * Custom divider will be used
     *
     * @param context The context of the application
     * @param resId   The res id
     */
    public DividerItemDecoration(Context context, int resId) {
        divider = ContextCompat.getDrawable(context, resId);
    }

    /**
     * Draw the dividing line
     *
     * @param canvas The canvas
     * @param parent The RecyclerView parent
     * @param state  The state of recycler view
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();

            divider.setBounds(left, top, right, bottom);
            divider.draw(canvas);
        }
    }
}
