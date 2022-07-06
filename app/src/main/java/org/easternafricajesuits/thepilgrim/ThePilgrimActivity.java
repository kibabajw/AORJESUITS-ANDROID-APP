package org.easternafricajesuits.thepilgrim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.ActivityThePilgrimBinding;
import org.easternafricajesuits.thepilgrim.adapters.ThePilgrimExpandableListAdapter;
import org.easternafricajesuits.thepilgrim.fragments.TPChapterOne;
import org.easternafricajesuits.thepilgrim.fragments.TPChapterTwo;
import org.easternafricajesuits.thepilgrim.fragments.TPForeword;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterEight;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterEleven;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterFive;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterFour;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterNine;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterSeven;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterSix;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterTen;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentChapterThree;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentIntroduction;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterEight;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterEleven;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterFive;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterFour;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterNine;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterSeven;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterSix;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterTen;
import org.easternafricajesuits.thepilgrim.fragments.TPFragmentNotesToChapterThree;
import org.easternafricajesuits.thepilgrim.fragments.TPNotesToChapterOne;
import org.easternafricajesuits.thepilgrim.fragments.TPNotesToChapterTwo;
import org.easternafricajesuits.thepilgrim.fragments.TPPreface;
import org.easternafricajesuits.thepilgrim.fragments.TPTestament;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ThePilgrimActivity extends AppCompatActivity {

    private ActivityThePilgrimBinding binding;

    private long pressedTime = 0;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    List<String> groupListTitle;
    List<String> childList;
    Map<String, List<String>> groupwithList;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThePilgrimBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.thepilgrimtoolbar;
        setSupportActionBar(toolbar);

        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Init the drawer
        mDrawerLayout = binding.thepilgrimdrawerLayout;
        expandableListView = binding.thepilgrimnavList;

        initGroupList();
        genData();

        View listHeaderView = getLayoutInflater().inflate(R.layout.the_pilgrim_nav_header, null, false);
        expandableListView.addHeaderView(listHeaderView);

        addDrawersItem();
        setupDrawer();

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.thepilgrimcontainer, TPTestament.class, null).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("The Pilgrim");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void addDrawersItem() {
        expandableListAdapter = new ThePilgrimExpandableListAdapter(this, groupListTitle, groupwithList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                parent.expandGroup(groupPosition, true);
                return true;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("The Pilgrim");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selectedItem = ((List)(groupwithList.get(groupListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (groupListTitle.get(groupPosition).equals(groupListTitle.get(groupPosition))) {
                    switch (groupPosition) {
                        // A pilgrim's Testament
                        case 0:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPTestament.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // Introduction
                        case 1:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentIntroduction.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // Foreword
                        case 2:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPForeword.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // Preface
                        case 3:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPPreface.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter one
                        case 4:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPChapterOne.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPNotesToChapterOne.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter two
                        case 5:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPChapterTwo.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPNotesToChapterTwo.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter three
                        case 6:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterThree.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterThree.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter four
                        case 7:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterFour.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterFour.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter five
                        case 8:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterFive.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterFive.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter six
                        case 9:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterSix.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterSix.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter seven
                        case 10:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterSeven.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterSeven.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter eight
                        case 11:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterEight.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterEight.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter nine
                        case 12:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterNine.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterNine.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter ten
                        case 13:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterTen.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterTen.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Chapter eleven
                        case 14:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentChapterEleven.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPFragmentNotesToChapterEleven.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // default
                        default:
                            fragmentManager.beginTransaction().replace(R.id.thepilgrimcontainer, TPTestament.class, null)
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null)
                                    .commit();
                    }
                } else {

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    private void genData() {
        String[] modeltestament = {"A Pilgrim's Testament"};
        String[] modelintroduction = {"Introduction"};
        String[] modelforeword = {"Foreword"};
        String[] modelpreface = {"Preface"};

        String[] modelone = {"Pamplona and Loyola", "Notes to Chapter One"};
        String[] modeltwo = {"Road to Montserrat", "Notes to Chapter Two"};
        String[] modelthree = {"Sojourn at Manresa", "Notes to Chapter Three"};
        String[] modelfour = {"Pilgrimage to Jerusalem", "Notes to Chapter Four"};
        String[] modelfive = {"Return Across Europe", "Notes to Chapter Five"};
        String[] modelsix = {"Barcelona and Alcalá", "Notes to Chapter Six"};
        String[] modelseven = {"Trouble at Salamanca", "Notes to Chapter Seven"};
        String[] modeleight = {"Progress in Paris", "Notes to Chapter Eight"};
        String[] modelnine = {"Farewell to Spain", "Notes to Chapter Nine"};
        String[] modelten = {"Venice and Vicenza", "Notes to Chapter Ten"};
        String[] modeleleven = {"Finally in Rome", "Notes to Chapter Eleven"};

        groupwithList = new LinkedHashMap<String, List<String>>();

        for (String group : groupListTitle) {
            if (group.equals("A Pilgrim's Testament")) {
                loadChild(modeltestament);
            } else if (group.equals("Introduction")) {
                loadChild(modelintroduction);
            } else if (group.equals("Foreword")) {
                loadChild(modelforeword);
            } else if (group.equals("Preface")) {
                loadChild(modelpreface);
            } else if (group.equals("Chapter One")) {
                loadChild(modelone);
            } else if (group.equals("Chapter Two")) {
                loadChild(modeltwo);
            } else if (group.equals("Chapter Three")) {
                loadChild(modelthree);
            } else if (group.equals("Chapter Four")) {
                loadChild(modelfour);
            } else if (group.equals("Chapter Five")) {
                loadChild(modelfive);
            } else if (group.equals("Chapter Six")) {
                loadChild(modelsix);
            } else if (group.equals("Chapter Seven")) {
                loadChild(modelseven);
            } else if (group.equals("Chapter Eight")) {
                loadChild(modeleight);
            } else if (group.equals("Chapter Nine")) {
                loadChild(modelnine);
            } else if (group.equals("Chapter Ten")) {
                loadChild(modelten);
            } else if (group.equals("Chapter Eleven")) {
                loadChild(modeleleven);
            }

            groupwithList.put(group, childList);
        }
    }

    private void loadChild(String[] childModel) {
        childList = new ArrayList<>();

        for (String model : childModel) {
            childList.add(model);
        }
    }

    private void initGroupList() {
        groupListTitle = new ArrayList<>();

        groupListTitle.add("A Pilgrim's Testament");
        groupListTitle.add("Introduction");
        groupListTitle.add("Foreword");
        groupListTitle.add("Preface");
        groupListTitle.add("Chapter One");
        groupListTitle.add("Chapter Two");
        groupListTitle.add("Chapter Three");
        groupListTitle.add("Chapter Four");
        groupListTitle.add("Chapter Five");
        groupListTitle.add("Chapter Six");
        groupListTitle.add("Chapter Seven");
        groupListTitle.add("Chapter Eight");
        groupListTitle.add("Chapter Nine");
        groupListTitle.add("Chapter Ten");
        groupListTitle.add("Chapter Eleven");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tp_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {

        }
        pressedTime = System.currentTimeMillis();
    }
}