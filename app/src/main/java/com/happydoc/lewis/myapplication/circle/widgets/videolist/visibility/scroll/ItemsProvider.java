package com.happydoc.lewis.myapplication.circle.widgets.videolist.visibility.scroll;


import com.happydoc.lewis.myapplication.circle.widgets.videolist.visibility.items.ListItem;

/**
 * This interface is used by
 * Using this class to get
 *
 * @author Wayne
 */
public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
