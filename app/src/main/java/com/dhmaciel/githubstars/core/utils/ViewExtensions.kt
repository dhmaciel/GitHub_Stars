package com.dhmaciel.githubstars.core.utils

import android.view.View

fun View.visible() {
    if (this.visibility == View.GONE || this.visibility == View.INVISIBLE) this.visibility =
        View.VISIBLE
}

fun View.gone() {
    if (this.visibility == View.VISIBLE || this.visibility == View.INVISIBLE) this.visibility =
        View.GONE
}

fun View.invisible() {
    if (this.visibility == View.VISIBLE || this.visibility == View.GONE) this.visibility =
        View.INVISIBLE
}