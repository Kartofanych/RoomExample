package com.example.roomexample1.adapter

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper

class SwipeHelper(
    swipeCallback: SwipeCallbackInterface,
    context: Context
) : ItemTouchHelper(SwipeCallback(swipeCallback, context))