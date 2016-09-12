package skkk.gogogo.dakainote.ViewHolder;

/**
 * Created by admin on 2016/8/3.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：
*/

import android.view.View;

import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.AudioButton;

/**
 * 主页横向RecyclerView的viewholder
 * @author Robin
 * time 2015-04-10 12:53:02
 *
 */
public class NoteVoiceViewHolder extends RecyclerViewHolderBase {

    public AudioButton abNoteDetailItem;
    /**
     * 在构造函数中进行findviewbyid
     * @param itemView item的view
     */
    public NoteVoiceViewHolder(View itemView) {
        super(itemView);
        abNoteDetailItem= (AudioButton) itemView.findViewById(R.id.ab_note_detail_item);
    }

}
