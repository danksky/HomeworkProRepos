package com.skylan.homeworkpro;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainNavigationActivity extends ActionBarActivity implements OnChildClickListener {

    // In this activity, all data changing or real-time manipulation occurs.
    // This is the Main class and therefore, your user experience is handled here.
    // Leave the adapter simply to display and arrange data for visual aid.

    private DrawerLayout drawer;
    private ExpandableListView drawerList;
    private CheckBox checkBox;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public ArrayList<String> groupItem = new ArrayList<String>();
    public ArrayList<ArrayList<SubjectInfo>> childItem = new ArrayList<ArrayList<SubjectInfo>>();
    public SubjectInfo tempSI;
    public NewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout_test);

        setGroupData();
        setChildGroupData();
        myAdapter = new NewAdapter(this, groupItem, childItem);

        initDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_layout_test, menu);
        return true;
    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawerList = (ExpandableListView) findViewById(R.id.left_drawer2);

        drawerList.setAdapter(myAdapter);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            drawerList.setIndicatorBounds(310, 350);
        } else {
            drawerList.setIndicatorBoundsRelative(310, 350);
        }


        drawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id){
                Fragment fragment;
                FragmentManager fragmentManager;
                Bundle args;
                switch (groupPosition) {
                    case 0:
                        fragment = new AssignmentManagerFragment();
                        fragmentManager = getFragmentManager();
                        args = new Bundle();
                        args.putString(AssignmentManagerFragment.ARG_PARAM1, "" + groupPosition);
                        fragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();
                        if (drawerList.isGroupExpanded(groupPosition)) {
                            drawerList.collapseGroup(groupPosition);
                            drawerList.collapseGroup(groupPosition+1);
                        } else {
                            drawerList.expandGroup(groupPosition, false);
                            drawerList.collapseGroup(groupPosition + 1);

                        }
                        break;

                    case 1:
                        fragment = new AssignmentManagerFragment();
                        fragmentManager = getFragmentManager();
                        args = new Bundle();
                        args.putString(AssignmentManagerFragment.ARG_PARAM1, "" + groupPosition);
                        fragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();
                        if (drawerList.isGroupExpanded(groupPosition)) {
                            drawerList.collapseGroup(groupPosition);
                            drawerList.collapseGroup(groupPosition-1);
                        } else {
                            drawerList.expandGroup(groupPosition, false);
                            drawerList.collapseGroup(groupPosition - 1);
                        }
                        break;

                    case 2:
                        fragment = new SubjectManagerFragment();
                        fragmentManager = getFragmentManager();
                        args = new Bundle();
                        args.putString(SubjectManagerFragment.ARG_PARAM1, "" + groupPosition);
                        fragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();

                        drawerList.collapseGroup(0);
                        drawerList.collapseGroup(1);
                        drawer.closeDrawer(drawerList);
                        break;

                    // for now, just SubjectManager, but soon they will lead to Settings then H/Feedback
                    case 3:
                        fragment = new SubjectManagerFragment();
                        fragmentManager = getFragmentManager();
                        args = new Bundle();
                        args.putString(SubjectManagerFragment.ARG_PARAM1, "" + groupPosition);
                        fragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();
                        if (drawerList.isGroupExpanded(groupPosition)) {
                            drawerList.collapseGroup(groupPosition);
                        } else {
                            drawerList.expandGroup(groupPosition, false);
                        }
                        break;

                    case 4:
                        fragment = new SubjectManagerFragment();
                        fragmentManager = getFragmentManager();
                        args = new Bundle();
                        args.putString(SubjectManagerFragment.ARG_PARAM1, "" + groupPosition);
                        fragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.content_frame2, fragment).commit();
                        if (drawerList.isGroupExpanded(groupPosition)) {
                            drawerList.collapseGroup(groupPosition);
                        } else {
                            drawerList.expandGroup(groupPosition, false);
                        }
                        break;
                }
                return true;
            }

        });

        drawerList.setOnChildClickListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("open");
                invalidateOptionsMenu();
            }

             // Called when a drawer has settled in a completely open state.

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                myAdapter.notifyDataSetChanged();
                getSupportActionBar().setTitle("close");
                invalidateOptionsMenu();
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    public void setGroupData() {
        groupItem.add("Assignments");
        groupItem.add("Archived");
        groupItem.add("Subjects");
        groupItem.add("Settings");
        groupItem.add("Help / Feedback");
    }

    public void setChildGroupData() {
        /**
         * Add Data For Assignments and Archived
         */

        ArrayList<SubjectInfo> childAssignments = new ArrayList<SubjectInfo>();
        ArrayList<SubjectInfo> childArchived = new ArrayList<SubjectInfo>();
        List<SubjectInfo> sil = SubjectInfo.listAll(SubjectInfo.class);

        for (int go = 0; go < sil.size(); go++) {
            if (sil.get(go).itemHeaderTitle.equals("Assignments") && !sil.get(go).subjectArchived) {
                /**
                 * Only add item to Navigation drawer under 'Assignments' if not archived,
                 * and is an actual Subject
                 */
                childAssignments.add(sil.get(go));
            }
            else if (sil.get(go).itemHeaderTitle.equals("Archived")){
                childArchived.add(sil.get(go));
            }
        }
        childItem.add(childAssignments);
        if (childArchived.size() < 3) {
            childArchived.add(new SubjectInfo("Archived", "Archived", 888, true, true));
            childArchived.add(new SubjectInfo("Completed", "Archived", 888, true, true));
            childArchived.add(new SubjectInfo("Outdated", "Archived", 888, true, true));
            childArchived.get(0).save();
            childArchived.get(1).save();
            childArchived.get(2).save();
        }
        childItem.add(childArchived);

        /**
         * Add empty children for Subjects, Settings, and H/Feedback
         */
        ArrayList<SubjectInfo> child;

        child = new ArrayList<SubjectInfo>(); //essentially null
        childItem.add(child);
        childItem.add(child);
        childItem.add(child);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, final int childPosition, long id) {
        checkBox = (CheckBox) v.findViewById(R.id.show_child_subject_checkBox);
        /*
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                //Update SugarRecord value for SubjectInfo

                SubjectInfo si = SubjectInfo.findById(SubjectInfo.class, (long) (childPosition) + 1);
                //because indices start at 1 in SugarRecord
                si.subjectChecked = isChecked;
                si.save();
                Toast.makeText(getApplicationContext(), "Clicked On Child: " +buttonView.getTag() + "|"+ childPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
        */
        checkBox.toggle();
        tempSI = SubjectInfo.findById(SubjectInfo.class,
                 childItem.get(groupPosition).get(childPosition).getId());
        tempSI.subjectChecked = !tempSI.subjectChecked;
        tempSI.save();
        childItem.get(groupPosition).set(childPosition, tempSI);
        myAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(), "Toggled " +groupPosition + "|" + childPosition,
                Toast.LENGTH_SHORT).show();
        // the oncheckedchangedListener now resides and operates out of adapter
        // in a much more efficient, clean manner
        return true;
    }



    /*
    private void selectItem(int position) {
        // This will require a second argument, asking for child position as well.
        // Many conditions, because some are just checks and others call whole other fragments.

        //so in my case, checks with case becuase I believe I will have more than one fragment!
        //fragment for: 1 main assignment display 2 subject manager 3 settings 4 feedback
        // when 'help' is selected, runs through tutorial on whichever fragment is shown... or something like that

        Fragment fragment = new AssignmentManagerFragment();
        FragmentManager fragmentManager = getFragmentManager();
        Bundle args = new Bundle();
        args.putString(AssignmentManagerFragment.ARG_PARAM1, ""+position);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
/*
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        //essentially setContentView, then the fragment to replace the default/main fragment with

*
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }*/


}
