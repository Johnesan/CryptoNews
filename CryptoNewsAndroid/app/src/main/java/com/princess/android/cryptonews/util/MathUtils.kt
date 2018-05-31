@file:JvmName("MathUtils")

package com.princess.android.cryptonews.util

/**
 * Created by numb3rs on 5/31/18.
 */


fun constrain(min: Float, max: Float, v: Float) = v.coerceIn(min, max)