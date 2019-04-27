package iss.vanil.retrofitex;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MessageItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    MessageItemDecoration(int space) { this.space = space; }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        outRect.left = outRect.right = space;

        boolean isLast = (position == state.getItemCount() - 1);
        if(isLast){
            outRect.bottom = space;
            outRect.top = space;
        }
        else {
            outRect.top = space;
            outRect.bottom = 0;
        }

    }

}