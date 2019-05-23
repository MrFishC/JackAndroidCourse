package jack.alipayhomeeditalluidemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by @vitovalov on 30/9/15.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> implements View.OnClickListener {

    String[] mListData;
    public ItemClick mClick;
    private Context context;

    public ListAdapter(String[] mListData) {
        this.mListData = mListData;
    }

    public ListAdapter(String[] mListData, Context c) {
        context = c;
        this.mListData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        //更换布局  RecyclerView嵌套RecyclerView
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_textview,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(mListData[i]);


        myViewHolder.title.setTag(i);
        myViewHolder.tvContent.setTag(i);
        myViewHolder.title.setOnClickListener(this);
        myViewHolder.tvContent.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView tvContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_item);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }


    //    ----------------------------------------------------------
    public void setItemClick(ItemClick itemClick) {
        mClick = itemClick;
    }

    public interface ItemClick {
        void setItemOnClick(View view, int pos);
    }

    @Override
    public void onClick(View v) {
        if (mClick != null) {
            mClick.setItemOnClick(v, (Integer) v.getTag());
        }
    }
}


