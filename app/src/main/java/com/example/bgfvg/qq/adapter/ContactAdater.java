package com.example.bgfvg.qq.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bgfvg.qq.R;
import com.example.bgfvg.qq.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BGFVG on 2017/3/15.
 */

public class ContactAdater extends RecyclerView.Adapter<ContactAdater.ViewHolder> {

    private List<String> contactList;

    public ContactAdater(List<String> contactList) {
        this.contactList = contactList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String contact = contactList.get(position);
        holder.mTv_username.setText(contact);
        String initial = StringUtils.getInitial(contact);
        holder.mTv_section.setText(initial);
        if (position == 0) {
            holder.mTv_section.setVisibility(View.VISIBLE);
        } else {
            String initialPre = StringUtils.getInitial(contactList.get(position - 1));
            if (TextUtils.equals(initialPre, initial)) {
                holder.mTv_section.setVisibility(View.GONE);
            } else {
                holder.mTv_section.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTv_section;
        public TextView mTv_username;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv_section = (TextView) itemView.findViewById(R.id.tv_section);
            mTv_username = (TextView) itemView.findViewById(R.id.tv_username);
        }
    }
}
