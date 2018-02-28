package com.shrikanthravi.cinemaappconcept.utils;

/**
 * Created by shrikanthravi on 27/02/18.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MiddleItemFinder extends RecyclerView.OnScrollListener {

    private
    Context context;

    private
    LinearLayoutManager layoutManager;

    private
    MiddleItemCallback callback;

    private
    int controlState;

    public
    static final int ALL_STATES = 10;

    public MiddleItemFinder(Context context, LinearLayoutManager layoutManager, MiddleItemCallback callback, int controlState) {
        this.context = context;
        this.layoutManager = layoutManager;
        this.callback = callback;
        this.controlState = controlState;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        if (controlState == ALL_STATES || newState == controlState) {

            int firstVisible = layoutManager.findFirstVisibleItemPosition();
            int lastVisible = layoutManager.findLastVisibleItemPosition();
            int itemsCount = lastVisible - firstVisible + 1;

            int screenCenter = context.getResources().getDisplayMetrics().widthPixels / 2;

            int minCenterOffset = Integer.MAX_VALUE;

            int middleItemIndex = 0;

            for (int index = 0; index < itemsCount; index++) {

                View listItem = layoutManager.getChildAt(index);

                if (listItem == null)
                    return;

                int leftOffset = listItem.getLeft();
                int rightOffset = listItem.getRight();
                int centerOffset = Math.abs(leftOffset - screenCenter) + Math.abs(rightOffset - screenCenter);

                if (minCenterOffset > centerOffset) {
                    minCenterOffset = centerOffset;
                    middleItemIndex = index + firstVisible;
                }
            }

            callback.scrollFinished(middleItemIndex);
        }
    }

    public interface MiddleItemCallback {

        void scrollFinished(int middleElement);
    }
}
