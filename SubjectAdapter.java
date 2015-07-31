package com.skylan.homeworkpro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.List;

import static com.skylan.homeworkpro.SubjectManagerFragment.*;

/**
 * Created by danielkawalsky on 3/7/15.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

    private List<SubjectInfo> subjectList;
    public Context context;

    public SubjectAdapter(ArrayList<SubjectInfo> subjectList) {
        this.subjectList = subjectList;
    }

    private MultiSelector mMultiSelector = new MultiSelector();

    public int getItemCount() {
        return subjectList.size();
    }

    public void onBindViewHolder(final SubjectViewHolder subjectViewHolder, int i) {
        final SubjectInfo si = subjectList.get(i);
        subjectViewHolder.vSubjectName.setText(si.subjectName);
        subjectViewHolder.vSubjectGrade.setText(Integer.toString(si.subjectGrade));
        subjectViewHolder.vSubjectLayout.setBackgroundColor(Color.parseColor(giveSubjectHexValue((double) si.subjectGrade)));

        /*
        subjectViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(subjectViewHolder.itemView.getContext());
                builder.setTitle("Delete Subject"); //needs to become Archive option!!!
                builder.setMessage("Do you want to delete the subject \"" + si.subjectName + "\"?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        si.delete();
                        subjectList = SubjectInfo.listAll(SubjectInfo.class);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return false;
            }
        });
        */
    }

    public SubjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.subject_card_layout, viewGroup, false);

        return new SubjectViewHolder(itemView);
    }


    /*
    public SubjectManagerFragment.MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.subject_card_layout, viewGroup, false);

        return new SubjectManagerFragment.MyHolder(itemView);
    }
    */

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {

        protected TextView vSubjectName;
        protected EditText vSubjectGrade;
        protected RelativeLayout vSubjectLayout;

        public SubjectViewHolder (View v) {
            super (v);
            vSubjectName = (TextView) v.findViewById(R.id.subject_card_name_textView);
            vSubjectGrade = (EditText) v.findViewById(R.id.subject_card_grade_editText);
            vSubjectLayout = (RelativeLayout) v.findViewById(R.id.subject_card_relative_layout);

        }
    }
    /*

    public class MyHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener {
        private final RelativeLayout relaLayout;

        public MyHolder (View itemView) {
            super(itemView, mMultiSelector);
            relaLayout = (RelativeLayout) itemView.findViewById(R.id.subject_card_relative_layout);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setLongClickable(true);
        }
        @Override
        public void onClick(View v) {
            if (mMultiSelector.tapSelection(this)) { //true if in selection mode

            }   else { //what happens when tapping if not in selection mode
            }
        }

        @Override
        public boolean onLongClick(View v) {
            AppCompatActivity activity = (AppCompatActivity)getActivity();
            activity.startSupportActionMode(mDeleteMode);
            mMultiSelector.setSelected(this, true);
            return true;
        }
    }
    */

    public static String giveSubjectHexValue(double rated) {
        rated = (512*((rated-60)/40));
        int i = (int) rated;
        String part = "00";
        String complete = "";
        if (i <= 0) {
            i = 0;
        }
        if (i >= 512) {
            i = 512;
        }
        if (rated<=256) {
            String hex = Integer.toHexString(i);
            if (hex.length() <= 1) {
                complete = "#4DFF0" + hex + part;
            } else if (hex.length() == 2) {
                complete = "#4DFF" + hex + part;
            } else if (hex.length() > 2) {
                hex = Integer.toHexString(255);
                complete = "#4DFF" + hex + part;
            }

        } else if (rated>256) {
            i= 256 - Math.abs(256 - i);
            String hex = Integer.toHexString(i);
            if (hex.length() <= 1) {
                complete = "#4D0" + hex + "FF" + part;
            } else if (hex.length() == 2) {
                complete = "#4D" +hex + "FF" + part;
            } else if (i < 0) {
                hex = Integer.toHexString(0);
                complete = "#4D" + "0" + hex + "FF" + part;
            }
        }
        return complete;
    }


}

