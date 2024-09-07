package org.easternafricajesuits.spiritualexercises;

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
import android.widget.Toast;

import org.easternafricajesuits.MainActivity;
import org.easternafricajesuits.R;
import org.easternafricajesuits.spiritualexercises.adapters.SpeCustomExpandableListAdapter;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentContemplationToGainLove;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFifthExercise;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFirstDay;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFirstExercise;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFirstWeekAdditions;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFourthDay;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFourthExercise;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentFourthWeek;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentGeneralConfessionWithCommunion;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentGeneralExamenOfConscience;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentParticularAndDailyExamen;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentPreambleToconsiderStates;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentPreface;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentPreludeForMakingElection;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentPrincipleAndFoundation;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentRULESfive;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentRULESfour;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentRULESone;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentRULESthree;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentRULEStwo;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentRulesToPutOneselfInOrderForTheFuture;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentSecondContemplation;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentSecondDay;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentSecondExercise;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentTMOPSecondMethodOfPrayer;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentSpiritualExercises;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentTMOPFirstMethod;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentTMOPThirdMethodOfPrayer;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentTheCallofTheTemporalKing;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentTheMysteriesOfTheLifeOfChristOurLord;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentTheSameFourthDayLetMeditationBeMadeOn;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentThefirstdayandFirstContemplation;
import org.easternafricajesuits.spiritualexercises.fragments.SpeFragmentThirdExercise;
import org.easternafricajesuits.spiritualexercises.interfaces.NavigationManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpiritualExercisesActivity extends AppCompatActivity {

    private long pressedTime = 0;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    private NavigationManager navigationManager;

    List<String> groupListTitle;
    List<String> childList;
    Map<String, List<String>> groupwithList;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiritual_exercises);

        Toolbar toolbar = (Toolbar) findViewById(R.id.spetoolbar);
        setSupportActionBar(toolbar);

        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Init view
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = (ExpandableListView)findViewById(R.id.navList);

//        navigationManager = SpeFragmentNavigationManager.getmInstance(this);

        intiGroupList();
        genData();

        View listHeaderView = getLayoutInflater().inflate(R.layout.spiritual_exercises_nav_header, null, false);
        expandableListView.addHeaderView(listHeaderView);

        addDrawersItem();
        setupDrawer();


        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, SpeFragmentPreface.class, null).commit();
//            selectFirstItemAsDefault();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Spiritual Exercises");
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

//    private void selectFirstItemAsDefault() {
//        if (navigationManager != null) {
//            String firstItem = groupListTitle.get(0);
//            navigationManager.showFragment(firstItem);
//            getSupportActionBar().setTitle(firstItem);
//        }
//    }

    private void addDrawersItem() {
        expandableListAdapter = new SpeCustomExpandableListAdapter(this, groupListTitle, groupwithList);
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
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
//                    getSupportActionBar().setTitle(groupListTitle.get(groupPosition));
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("Spiritual Exercises");
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
                        case 0:
                            if(childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentPreface.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentSpiritualExercises.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        case 1: // FIRST WEEK
                            // 10 children
                                if (childPosition == 0) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentPrincipleAndFoundation.class, null)
                                            .setReorderingAllowed(true)
                                            .addToBackStack(null)
                                            .commit();
                                } else if (childPosition == 1) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentParticularAndDailyExamen.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 2) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentGeneralExamenOfConscience.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 3) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentGeneralConfessionWithCommunion.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 4) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFirstExercise.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 5) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentSecondExercise.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 6) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentThirdExercise.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 7) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFourthExercise.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 8) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFifthExercise.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 9) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFirstWeekAdditions.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else {
                                    break;
                                }
                            break;
                        case 2: // SECOND WEEK
                            // 7 children
                                if (childPosition == 0) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentTheCallofTheTemporalKing.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 1) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentThefirstdayandFirstContemplation.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 2) {
                                    // THE SECOND CONTEMPLATION
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentSecondContemplation.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 3) {
                                    // PREAMBLE TO CONSIDER STATES
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentPreambleToconsiderStates.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 4) {
                                    // THE FOURTH DAY
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFourthDay.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 5) {
                                    // THE SAME FOURTH DAY LET MEDITATION BE MADE ON
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentTheSameFourthDayLetMeditationBeMadeOn.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 6) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentPreludeForMakingElection.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else {
                                    break;
                                }
                            break;
                        case 3:
                            // 3 children
                            if (childPosition == 0) {
                                // FIRST DAY
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFirstDay.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else if (childPosition == 1) {
                                // SECOND DAY
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentSecondDay.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else if (childPosition == 2) {
                                // RULES TO PUT ONESELF IN ORDER FOR THE FUTURE
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentRulesToPutOneselfInOrderForTheFuture.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else {
                                break;
                            }

                            break;
                        case 4:
                            // 1 child
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentFourthWeek.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else {
                                break;
                            }
                            break;
                        case 5:
                            // 1 child
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentContemplationToGainLove.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else {
                                break;
                            }
                            break;
                        case 6:
                            // 3 children
                                if (childPosition == 0) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentTMOPFirstMethod.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 1) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentTMOPSecondMethodOfPrayer.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else if (childPosition == 2) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentTMOPThirdMethodOfPrayer.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                                } else {
                                    break;
                                }
                            break;
                        case 7:
                            // 1 child
                            // THE MYSTERIES OF THE LIFE OF CHRIST OUR LORD
                            if (childPosition == 0) {
                                    fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentTheMysteriesOfTheLifeOfChristOurLord.class, null)
                                            .setReorderingAllowed(true)
                                            .commit();
                            } else {
                                break;
                            }
                            break;
                        case 8:
                            // 5 children
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentRULESone.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else if (childPosition == 1) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentRULEStwo.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else if (childPosition == 2) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentRULESthree.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else if (childPosition == 3) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentRULESfour.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else if (childPosition == 4) {
                                fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentRULESfive.class, null)
                                        .setReorderingAllowed(true)
                                        .commit();
                            } else {
                                break;
                            }
                            break;
                        default:
                            fragmentManager.beginTransaction().replace(R.id.container, SpeFragmentPreface.class, null)
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null)
                                    .commit();
                            break;
                    }
//                    navigationManager.showFragment(selectedItem);
                } else {
                    throw new IllegalArgumentException("Not supported fragment");
                }

                mDrawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Opened");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void genData() {
        String[] modelIntroduction = {"Preface", "Spiritual Exercises"};
        String[] modelFIRSTWEEK = {"PRINCIPLE AND FOUNDATION", "PARTICULAR AND DAILY EXAMEN", "GENERAL EXAMEN OF CONSCIENCE", "GENERAL CONFESSION WITH COMMUNION", "FIRST EXERCISE", "SECOND EXERCISE", "THIRD EXERCISE", "FOURTH EXERCISE", "FIFTH EXERCISE", "ADDITIONS"};
        String[] modelSECONDWEEK = {"THE CALL OF THE TEMPORAL KING", "THE FIRST DAY AND FIRST CONTEMPLATION", "THE SECOND CONTEMPLATION", "PREAMBLE TO CONSIDER STATES", "THE FOURTH DAY", "THE SAME FOURTH DAY LET MEDITATION BE MADE ON", "PRELUDE FOR MAKING ELECTION"};
        String[] modelTHIRDWEEK = {"FIRST DAY", "SECOND DAY", "RULES TO PUT ONESELF IN ORDER FOR THE FUTURE"};
        String[] modelFOURTHWEEK = {"Fourth Week"};
        String[] modelCONTEMPLATIONTOGAINLOVE = {"Contemplation to gain love"};
        String[] modelTHREEMETHODSOFPRAYER = {"FIRST METHOD", "SECOND METHOD OF PRAYER", "THIRD METHOD OF PRAYER"};
        String[] modelTHEMYSTERIESOFTHELIFEOFCHRISTOURLORD = {"The mysteries of the life of Christ our Lord"};
        String[] modelRULES = {"FOR PERCEIVING AND KNOWING IN SOME MANNER THE\n" +
                "DIFFERENT MOVEMENTS WHICH ARE CAUSED IN THE SOUL", "RULES FOR THE SAME EFFECT WITH GREATER DISCERNMENT OF\n" +
                "SPIRITS", "IN THE MINISTRY OF DISTRIBUTING ALMS", "THE FOLLOWING NOTES HELP TO PERCEIVE AND UNDERSTAND\n" +
                "SCRUPLES", "TO HAVE THE TRUE SENTIMENT"};


        groupwithList = new LinkedHashMap<String, List<String>>();

        for (String group : groupListTitle) {
            if (group.equals("Introduction")) {
                loadChild(modelIntroduction);
            } else if (group.equals("FIRST WEEK")) {
                loadChild(modelFIRSTWEEK);
            } else if (group.equals("SECOND WEEK")) {
                loadChild(modelSECONDWEEK);
            } else if (group.equals("THIRD WEEK")) {
                loadChild(modelTHIRDWEEK);
            } else if (group.equals("FOURTH WEEK")) {
                loadChild(modelFOURTHWEEK);
            } else if (group.equals("CONTEMPLATION TO GAIN LOVE")) {
                loadChild(modelCONTEMPLATIONTOGAINLOVE);
            } else if (group.equals("THREE METHODS OF PRAYER")) {
                loadChild(modelTHREEMETHODSOFPRAYER);
            } else if (group.equals("THE MYSTERIES OF THE LIFE OF CHRIST OUR LORD")) {
                loadChild(modelTHEMYSTERIESOFTHELIFEOFCHRISTOURLORD);
            } else if (group.equals("RULES")){
                loadChild(modelRULES);
            } else {}

            groupwithList.put(group, childList);
        }

    }

    private void loadChild(String[] childModel) {
        childList = new ArrayList<>();

        for (String model : childModel) {
            childList.add(model);
        }
    }

    private void intiGroupList() {
        groupListTitle = new ArrayList<>();

        groupListTitle.add("Introduction");
        groupListTitle.add("FIRST WEEK");
        groupListTitle.add("SECOND WEEK");
        groupListTitle.add("THIRD WEEK");
        groupListTitle.add("FOURTH WEEK");
        groupListTitle.add("CONTEMPLATION TO GAIN LOVE");
        groupListTitle.add("THREE METHODS OF PRAYER");
        groupListTitle.add("THE MYSTERIES OF THE LIFE OF CHRIST OUR LORD");
        groupListTitle.add("RULES");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spe_main_menu, menu);
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