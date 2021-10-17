package com.mindeurfou.golfbook.ui.common

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.*

class SScustomMenuItem(
    private val itemId: Int
) : MenuItem {

    override fun getItemId(): Int {
        return itemId
    }

    override fun getGroupId(): Int {
        TODO("Not yet implemented")
    }

    override fun getOrder(): Int {
        return Menu.CATEGORY_CONTAINER
    }

    override fun setTitle(p0: CharSequence?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun setTitle(p0: Int): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getTitle(): CharSequence {
        TODO("Not yet implemented")
    }

    override fun setTitleCondensed(p0: CharSequence?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getTitleCondensed(): CharSequence {
        TODO("Not yet implemented")
    }

    override fun setIcon(p0: Drawable?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun setIcon(p0: Int): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getIcon(): Drawable {
        TODO("Not yet implemented")
    }

    override fun setIntent(p0: Intent?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getIntent(): Intent {
        TODO("Not yet implemented")
    }

    override fun setShortcut(p0: Char, p1: Char): MenuItem {
        TODO("Not yet implemented")
    }

    override fun setNumericShortcut(p0: Char): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getNumericShortcut(): Char {
        TODO("Not yet implemented")
    }

    override fun setAlphabeticShortcut(p0: Char): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getAlphabeticShortcut(): Char {
        TODO("Not yet implemented")
    }

    override fun setCheckable(p0: Boolean): MenuItem {
        TODO("Not yet implemented")
    }

    override fun isCheckable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setChecked(p0: Boolean): MenuItem {
        TODO("Not yet implemented")
    }

    override fun isChecked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setVisible(p0: Boolean): MenuItem {
        TODO("Not yet implemented")
    }

    override fun isVisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setEnabled(p0: Boolean): MenuItem {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasSubMenu(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSubMenu(): SubMenu {
        TODO("Not yet implemented")
    }

    override fun setOnMenuItemClickListener(p0: MenuItem.OnMenuItemClickListener?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getMenuInfo(): ContextMenu.ContextMenuInfo {
        TODO("Not yet implemented")
    }

    override fun setShowAsAction(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun setShowAsActionFlags(p0: Int): MenuItem {
        TODO("Not yet implemented")
    }

    override fun setActionView(p0: View?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun setActionView(p0: Int): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getActionView(): View {
        TODO("Not yet implemented")
    }

    override fun setActionProvider(p0: ActionProvider?): MenuItem {
        TODO("Not yet implemented")
    }

    override fun getActionProvider(): ActionProvider {
        TODO("Not yet implemented")
    }

    override fun expandActionView(): Boolean {
        TODO("Not yet implemented")
    }

    override fun collapseActionView(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isActionViewExpanded(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setOnActionExpandListener(p0: MenuItem.OnActionExpandListener?): MenuItem {
        TODO("Not yet implemented")
    }
}