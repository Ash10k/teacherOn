package com.ash.teacheron.commonComponents;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MarginDecoration extends RecyclerView.ItemDecoration {

    private final int margin;

    public MarginDecoration(Context context, int marginDp)
    {
        margin = dpToPx(context, marginDp);
    }
    private int dpToPx(Context context, int dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        // Set margins for specific positions (adjust logic as needed)
        if (position % 2 == 0) { // Add margin to even positions
            outRect.left = margin;
            outRect.right = margin;
        } else {
            outRect.left = 0;
            outRect.right = 0;
        }
    }

}
