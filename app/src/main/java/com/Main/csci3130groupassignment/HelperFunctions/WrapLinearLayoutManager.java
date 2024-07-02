package com.Main.csci3130groupassignment.HelperFunctions;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom LinearLayoutManager that extends the default LinearLayoutManager to handle
 * IndexOutOfBoundsException during the layout process in a RecyclerView.
 */
public class WrapLinearLayoutManager extends LinearLayoutManager {
    //This is the WrapLinearLayoutManager from FirebaseCRUD tutorial 4,
    //as I saw it helps prevent IndexOutOfBoundsExceptions and thought
    //I should add it to avoid such potential problems.

    /**
     * Constructor for WrapLinearLayoutManager.
     *
     * @param context        The context associated with this LinearLayoutManager.
     * @param orientation    Layout orientation (LinearLayoutManager.VERTICAL or LinearLayoutManager.HORIZONTAL).
     * @param reverseLayout  Whether to reverse the layout of the items.
     */
    public WrapLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    /**
     * Overrides the default onLayoutChildren method to catch IndexOutOfBoundsException
     * during the layout process in a RecyclerView. This helps in preventing crashes
     * caused by invalid indices.
     *
     * @param recycler  Recycler instance for managing views that are detached from the RecyclerView.
     * @param state     Current state of the RecyclerView.
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            // Attempt to execute the default onLayoutChildren method.
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            // Catch and log IndexOutOfBoundsException to prevent crashes caused by invalid indices.
            Log.e("WrapLinearLayoutManager", "Index Out Of Bound Exception in RecyclerView");
        }
    }
}
