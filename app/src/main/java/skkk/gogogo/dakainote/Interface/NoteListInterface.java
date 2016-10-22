package skkk.gogogo.dakainote.Interface;

import android.support.v7.widget.RecyclerView;

import skkk.gogogo.dakainote.Adapter.NoteListAdapter;

/**
 * Created by admin on 2016/10/22.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/22$ 10:31$.
*/
public interface NoteListInterface {
    void setLayoutFlag(int flag);
    NoteListAdapter getAdapter();
    int getLayoutFlag();
    RecyclerView getRecyclerView();
    void showBlankTip();
}
