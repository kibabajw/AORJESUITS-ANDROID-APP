package org.easternafricajesuits.jesuitjargon;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import org.easternafricajesuits.R;
import org.easternafricajesuits.databinding.ActivityJesuitJargonBinding;
import org.easternafricajesuits.jesuitjargon.adapters.JesuitJargonExpandableListAdapter;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentA;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentB;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentC;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentD;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentE;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentF;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentG;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentGuide;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentH;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentI;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentIntroduction;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentJ;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentJesuitDictionary;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentK;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentL;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentM;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentN;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentO;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentP;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentPreface;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentQ;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentR;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentS;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentT;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentU;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentV;
import org.easternafricajesuits.jesuitjargon.fragments.JJFragmentW;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JesuitJargonActivity extends AppCompatActivity {

    private ActivityJesuitJargonBinding binding;

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
        binding = ActivityJesuitJargonBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.jesuitjargontoolbar;
        setSupportActionBar(toolbar);

        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Init the view
        mDrawerLayout = binding.jesuitjargondrawerLayout;
        expandableListView = binding.jesuitjargonnavList;


        initGroupList();
        genData();

        View listHeaderView = getLayoutInflater().inflate(R.layout.jesuit_jargon_nav_header, null, false);
        expandableListView.addHeaderView(listHeaderView);

        addDrawersItem();
        setupDrawer();

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragscontainer, JJFragmentIntroduction.class, null).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Jesuit jargon");
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
        expandableListAdapter = new JesuitJargonExpandableListAdapter(this, groupListTitle, groupwithList);
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
                getSupportActionBar().setTitle("Jesuit jargon");
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
                        // Introduction
                        case 0:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentIntroduction.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // preface to the english translation
                        case 1:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentPreface.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // jesuit glossary, guide to understanding the documents
                        case 2:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentGuide.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // Jesuit dictionary
                        case 3:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentJesuitDictionary.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            //A
                        case 4:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentA.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // B
                        case 5:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentB.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // C
                        case 6:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentC.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // D
                        case 7:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentD.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // E
                        case 8:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentE.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // F
                        case 9:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentF.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // G
                        case 10:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentG.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // H
                        case 11:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentH.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // I
                        case 12:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentI.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // J
                        case 13:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentJ.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // K
                        case 14:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentK.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // L
                        case 15:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentL.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // M
                        case 16:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentM.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // N
                        case 17:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentN.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // O
                        case 18:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentO.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // P
                        case 19:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentP.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Q
                        case 20:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentQ.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // R
                        case 21:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentR.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // S
                        case 22:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentS.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // T
                        case 23:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentT.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;

                        // U
                        case 24:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentU.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;

                        // V
                        case 25:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentV.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;

                        // W
                        case 26:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentW.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;

                        default:
                            fragmentManager.beginTransaction().replace(R.id.fragscontainer, JJFragmentIntroduction.class, null)
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
        String[] modelIntroduction = {"Introduction"};
        String[] modelPreface = {"Preface to the English translation"};
        String[] modelGuide = {"Guide to understanding the documents"};
        String[] modelJesuitdictionary = {"Jesuit Dictionary"};
        String[] modelA = {"A"};
        String[] modelB = {"B"};
        String[] modelC = {"C"};
        String[] modelD = {"D"};
        String[] modelE = {"E"};
        String[] modelF = {"F"};
        String[] modelG = {"G"};
        String[] modelH = {"H"};
        String[] modelI = {"I"};
        String[] modelJ = {"J"};
        String[] modelK = {"K"};
        String[] modelL = {"L"};
        String[] modelM = {"M"};
        String[] modelN = {"N"};
        String[] modelO = {"O"};
        String[] modelP = {"P"};
        String[] modelQ = {"Q"};
        String[] modelR = {"R"};
        String[] modelS = {"S"};
        String[] modelT = {"T"};
        String[] modelU = {"U"};
        String[] modelV = {"V"};
        String[] modelW = {"W"};

        groupwithList = new LinkedHashMap<String, List<String>>();

        for (String group : groupListTitle) {
            if (group.equals("Introduction")) {
                loadChild(modelIntroduction);
            } else if (group.equals("Preface to the English translation")) {
                loadChild(modelPreface);
            } else if (group.equals("Guide to understanding the documents")) {
                loadChild(modelGuide);
            } else if (group.equals("Jesuit Dictionary")) {
                loadChild(modelJesuitdictionary);
            } else if (group.equals("A")) {
                loadChild(modelA);
            } else if (group.equals("B")) {
                loadChild(modelB);
            } else if (group.equals("C")) {
                loadChild(modelC);
            } else if (group.equals("D")) {
                loadChild(modelD);
            } else if (group.equals("E")) {
                loadChild(modelE);
            } else if (group.equals("F")) {
                loadChild(modelF);
            } else if (group.equals("G")) {
                loadChild(modelG);
            } else if (group.equals("H")) {
                loadChild(modelH);
            } else if (group.equals("I")) {
                loadChild(modelI);
            } else if (group.equals("J")) {
                loadChild(modelJ);

            } else if (group.equals("K")) {
                loadChild(modelK);

            } else if (group.equals("L")) {
                loadChild(modelL);

            } else if (group.equals("M")) {
                loadChild(modelM);

            } else if (group.equals("N")) {
                loadChild(modelN);

            } else if (group.equals("O")) {
                loadChild(modelO);

            } else if (group.equals("P")) {
                loadChild(modelP);

            } else if (group.equals("Q")) {
                loadChild(modelQ);

            } else if (group.equals("R")) {
                loadChild(modelR);

            } else if (group.equals("S")) {
                loadChild(modelS);

            } else if (group.equals("T")) {
                loadChild(modelT);

            } else if (group.equals("U")) {
                loadChild(modelU);

            } else if (group.equals("V")) {
                loadChild(modelV);

            } else if (group.equals("W")) {
                loadChild(modelW);
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

        groupListTitle.add("Introduction");
        groupListTitle.add("Preface to the English translation");
        groupListTitle.add("Guide to understanding the documents");
        groupListTitle.add("Jesuit Dictionary");
        groupListTitle.add("A");
        groupListTitle.add("B");
        groupListTitle.add("C");
        groupListTitle.add("D");
        groupListTitle.add("E");
        groupListTitle.add("F");
        groupListTitle.add("G");
        groupListTitle.add("H");
        groupListTitle.add("I");
        groupListTitle.add("J");
        groupListTitle.add("K");
        groupListTitle.add("L");
        groupListTitle.add("M");
        groupListTitle.add("N");
        groupListTitle.add("O");
        groupListTitle.add("P");
        groupListTitle.add("Q");
        groupListTitle.add("R");
        groupListTitle.add("S");
        groupListTitle.add("T");
        groupListTitle.add("U");
        groupListTitle.add("V");
        groupListTitle.add("W");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.jj_main_menu, menu);
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