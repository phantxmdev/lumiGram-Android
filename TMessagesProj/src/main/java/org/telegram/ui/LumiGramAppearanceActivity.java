package org.telegram.ui;

import static org.telegram.messenger.LocaleController.getString;

import android.content.Context;
import android.view.View;

import org.telegram.messenger.LiteMode;
import org.telegram.messenger.R;
import org.telegram.ui.Cells.AppIconsSelectorCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;
import org.telegram.ui.Components.UniversalFragment;

import java.util.ArrayList;

public class LumiGramAppearanceActivity extends UniversalFragment {

    private AppIconsSelectorCell appIconsSelectorCell;

    @Override
    protected boolean useSimpleActionBar() {
        return true;
    }

    @Override
    public View createView(Context context) {
        setHasOwnBackground(true);
        appIconsSelectorCell = new AppIconsSelectorCell(context, this, currentAccount);
        fragmentView = super.createView(context);
        listView.setSections();
        listView.setSectionsDrawBackground(true);
        return fragmentView;
    }

    @Override
    protected RecyclerListView getListViewForSimpleGlass() {
        return listView;
    }

    @Override
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    @Override
    protected CharSequence getTitle() {
        return "Внешний вид";
    }

    @Override
    protected void fillItems(ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader(getString(R.string.AppIcon)));
        items.add(UItem.asCustom(appIconsSelectorCell));
        items.add(UItem.asShadow(null));

        items.add(UItem.asHeader("Эффекты интерфейса"));
        items.add(UItem.asCheck(1, "«Жидкое стекло»").setChecked(LiteMode.isEnabled(LiteMode.FLAG_LIQUID_GLASS)));
        items.add(UItem.asShadow("Включение эффекта жидкого стекла с преломлением света и динамическими бликами в интерфейсе."));
    }

    @Override
    protected void onClick(UItem item, View view, int position, float x, float y) {
        if (item.id == 1) {
            LiteMode.toggleFlag(LiteMode.FLAG_LIQUID_GLASS);
            boolean enabled = LiteMode.isEnabled(LiteMode.FLAG_LIQUID_GLASS);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(enabled);
            }
            listView.adapter.update(true);
        }
    }

    @Override
    protected boolean onLongClick(UItem item, View view, int position, float x, float y) {
        return false;
    }
}
