package org.easternafricajesuits.adusum.constitution;

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
import org.easternafricajesuits.adusum.constitution.adapters.ConstitutionExpandableListAdapter;
import org.easternafricajesuits.adusum.constitution.fragments.*;
import org.easternafricajesuits.databinding.ActivityConstitutionBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConstitutionActivity extends AppCompatActivity {

    private ActivityConstitutionBinding binding;

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
        binding = ActivityConstitutionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.constitutiontoolbar;
        setSupportActionBar(toolbar);

        // set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init the views
        mDrawerLayout = binding.constitutiondrawerLayout;
        expandableListView = binding.constitutionnavList;

        initGroupList();
        genData();

        View listHeaderView = getLayoutInflater().inflate(R.layout.constitution_nav_header, null, false);
        expandableListView.addHeaderView(listHeaderView);

        addDrawersItem();
        setupDrawer();

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.constitutioncontainer, ConsFragmentIntroduction.class, null).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Constitution");
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
        expandableListAdapter = new ConstitutionExpandableListAdapter(this, groupListTitle, groupwithList);
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
                getSupportActionBar().setTitle("Constitution");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String selectedItem = ((List)(groupwithList.get(groupListTitle.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (groupListTitle.get(groupPosition).equals(groupListTitle.get(groupPosition))) {
                    switch (groupPosition) {
                        // Case INTRODUCTION
                        case 0:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIntroduction.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CASE FORWARD
                        case 1:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentForeword.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CASE PREFACE
                        case 2:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPreface.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CASE PREFACE TO THE FIRST EDITION OF THE CONSTITUTION
                        case 3:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPrefacetothefirsteditionoftheconstitutions.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CASE PRINCIPAL ABBREVIATIONS
                        case 4:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPrincipalAbbreviations.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CASE FORMULAS OF THE INSTITUTE OF THE SOCIETY OF JESUS
                        case 5:
                            if (childPosition == 0) {
                                // Paul
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentFormulasoftheinstituteofthesocietyofjesusPAUL.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                // Julius
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentFormulasoftheinstituteofthesocietyofjesusJULIUS.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // Faculty to establish constitutions
                        case 6:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentFacultytoestablish.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // Prelim inary Rem arks Concerning the N otes Ad d ed to the Constitu tions
                        case 7:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPreliminaryRemarksConcerningtheNotesAddedToTheConstitutions.class, null
                                )
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 1
                        case 8:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterOne.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 2
                        case 9:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterTwo.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 3
                        case 10:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterThree.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // chapter 4
                        case 11:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterFour.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 5
                        case 12:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterFive.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 6
                        case 13:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterSix.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 7
                        case 14:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterSeven.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // CHAPTER 8
                        case 15:
                            if (childPosition == 0) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentChapterEight.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;

                        // II-B TH E CON STITUTION S OF TH E SOCIETY OF JESUS AN D TH EIR DECLARATION S
                        case 16:
                            if (childPosition == 0) {
                                // PREAMBLE TO TH E CON STITUTION S
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPreambleToTheConstitutions.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPreambleToTheDeclarationsAndObservationsAboutTheConstitutions.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2){
                                // DECLARATION S ON TH E PREAMBLE
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentDeclarationsOnThePreamble.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                // NORMS PREAMBLE
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentNormsPreamble.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // PART 1
                        case 17:
                            if (childPosition == 0) {
                                // CHAPTER 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart1Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                // PART 1 CHAPTER 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart1Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2){
                                // PART 1  CHAPTER 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart1Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                // PART 1 CHAPTER 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart1Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // PART II
                        case 18:
                            if (childPosition == 0) {
                                // part 2 chapter 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart2Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1){
                                // part 2 chapter 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart2Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2){
                                // part 2 chapter 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart2Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                // part 2 chapter 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart2Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // PART III
                        case 19:
                            if (childPosition == 0) {
                                // part 3 chapter 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart3Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if(childPosition == 1) {
                                // part 3 chapter 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart3Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else {
                                // part 3 chapter 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart3Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                            // PART IV
                        case 20:
                            if (childPosition == 0) {
                                // preamble and formation
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4PreambleAndFormation.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                // Part 4 CHAPTER 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2) {
                                // Part 4 CHAPTER 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 3) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 4) {
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 5) {
                                // part 4 chapter 5
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter5.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 6) {
                                // part 4 chapter 6
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter6.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 7) {
                                // part 4 chapter 7
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter7.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 8) {
                                // part 4 chapter 8
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter8.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if(childPosition == 9) {
                                // part 4 chapter 9
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter9.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 10) {
                                // part 4 chapter 10
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter10.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 11) {
                                // part 4 chapter 11
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter11.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 12) {
                                // part 4 chapter 12
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter12.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 13) {
                                // part 4 chapter 13
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter13.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 14) {
                                // part 4 chapter 14
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter14.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 15) {
                                // part 4 chapter 15
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter15.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 16) {
                                // part 4 chapter 16
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter16.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 17) {
                                // part 4 chapter 17
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart4Chapter17.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // PART V
                        case 21:
                            if (childPosition == 0) {
                                // PART V chapter 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart5Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                // PART V chapter 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart5Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2) {
                                // PART V chapter 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart5Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 3) {
                                // PART V chapter 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart5Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // PART VI
                        case 22:
                            if (childPosition == 0) {
                                // PART VI SECTION 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Section1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 1) {
                                // PART VI SECTION 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Section2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 2) {
                                // PART VI CHAPTER 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 3) {
                                // PART VI CHAPTER 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 4) {
                                // PART VI CHAPTER 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 5) {
                                // PART VI CHAPTER 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 6) {
                                // PART VI CHAPTER 5
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart6Chapter5.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // PART VII
                        case 23:
                            if (childPosition == 0) {
                                // PART VII PART VII
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart7Part7.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                // PART VII Chapter 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart7Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2) {
                                // PART VII Chapter 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart7Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 3) {
                                // PART VII Chapter 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart7Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 4) {
                                // PART VII Chapter 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart7Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // PART VIII
                        case 24:
                            if (childPosition == 0) {
                                // PART VIII Chapter 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 1) {
                                // PART VIII Chapter 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 2) {
                                // PART VIII Chapter 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 3) {
                                // PART VIII Chapter 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 4) {
                                // PART VIII Chapter 5
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter5.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 5) {
                                // PART VIII Chapter 6
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter6.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else  if (childPosition == 6) {
                                // PART VIII Chapter 7
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart8Chapter7.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // PART IX
                        case 25:
                            if (childPosition == 0) {
                                // PART IX
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                // PART IX Chapter 1
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9Chapter1.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2) {
                                // PART IX Chapter 2
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9Chapter2.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 3) {
                                // PART IX Chapter 3
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9Chapter3.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 4) {
                                // PART IX Chapter 4
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9Chapter4.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 5) {
                                // PART IX Chapter 5
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9Chapter5.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 6) {
                                // PART IX Chapter 6
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart9Chapter6.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // PART X
                        case 26:
                            if (childPosition == 0) {
                                // PART X
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentPart10.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        // Index of Topics
                        case 27:
                            if (childPosition == 0) {
                                // Index of Topics, A
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsA.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 1) {
                                // Index of Topics, B
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsB.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 2) {
                                // Index of Topics, C
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsC.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 3) {
                                // Index of Topics, D
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsD.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 4) {
                                // Index of Topics, E
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsE.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 5) {
                                // Index of Topics, F
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsF.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 6) {
                                // Index of Topics, G
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsG.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 7) {
                                // Index of Topics, H
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsH.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 8) {
                                // Index of Topics, I
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsI.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 9) {
                                // Index of Topics, J
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsJ.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 10) {
                                // Index of Topics, K
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsK.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 11) {
                                // Index of Topics, L
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsL.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 12) {
                                // Index of Topics, M
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsM.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 13) {
                                // Index of Topics, N
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsN.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 14) {
                                // Index of Topics, O
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsO.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 15) {
                                // Index of Topics, P
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsP.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 16) {
                                // Index of Topics, R
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsR.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 17) {
                                // Index of Topics, S
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsS.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 18) {
                                // Index of Topics, T
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsT.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 19) {
                                // Index of Topics, U
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsU.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 20) {
                                // Index of Topics, V
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsV.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 21) {
                                // Index of Topics, W
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsW.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 22) {
                                // Index of Topics, Y
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsY.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            } else if (childPosition == 23) {
                                // Index of Topics, Z
                                fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIndexOfTopicsZ.class, null)
                                        .setReorderingAllowed(true)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            break;
                        default:
                            fragmentManager.beginTransaction().replace(R.id.constitutioncontainer, ConsFragmentIntroduction.class, null)
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
        String[] modelIntroduction = {"INTRODUCTION"};
        String[] modelPreface = {"FOREWORD"};
        String[] modelGuide = {"PREFACE"};
        String[] modelJesuitdictionary = {"PREFACE TO THE FIRST EDITION OF THE CONSTITUTIONS"};
        String[] modelPrincipalabbreviations = {"PRINCIPAL ABBREVIATIONS"};
        String[] modelJuliusandPaul = {"PAUL III", "JULIUS III"};
        String[] modelFacultytoestablish = {"Faculty to Establish Constitutions and the Approbations of the Institute in General and of the Constitutions in Particular"};
        String[] modelPreliminaryRemarks = {"Preliminary Remarks Concerning the Notes Added to the Constitutions"};
        String[] modelChapterOne = {"CHAPTER ONE"};
        String[] modelChapterTwo = {"CHAPTER TWO"};
        String[] modelChapterThree = {"CHAPTER THREE"};
        String[] modelChapterFour = {"CHAPTER FOUR"};
        String[] modelChapterFive = {"CHAPTER FIVE"};
        String[] modelChapterSix = {"CHAPTER SIX"};
        String[] modelChapterSeven = {"CHAPTER SEVEN"};
        String[] modelChapterEight = {"CHAPTER EIGHT"};
        String[] modelPreambles = {
                "PREAMBLE TO THE CONSTITUTIONS",
                "PREAMBLE TO THE DECLARATIONS AND OBSERVATIONS ABOUT THE CONSTITUTIONS",
                "DECLARATIONS ON THE PREAMBLE",
                "NORMS PREAMBLE"
        };
        String[] modelPart1 = {"CHAPTER 1", "CHAPTER 2", "CHAPTER 3", "CHAPTER 4"};
        String[] modelPart2 = {"CHAPTER 1", "CHAPTER 2", "CHAPTER 3", "CHAPTER 4"};
        String[] modelPart3 = {"CHAPTER 1", "CHAPTER 2", "CHAPTER 3"};
        String[] modelPart4 = {
                "PREAMBLE AND FORMATION",
                "CHAPTER 1",
                "CHAPTER 2",
                "CHAPTER 3",
                "CHAPTER 4",
                "CHAPTER 5",
                "CHAPTER 6",
                "CHAPTER 7",
                "CHAPTER 8",
                "CHAPTER 9",
                "CHAPTER 10",
                "CHAPTER 11",
                "CHAPTER 12",
                "CHAPTER 13",
                "CHAPTER 14",
                "CHAPTER 15",
                "CHAPTER 16",
                "CHAPTER 17"
        };
        String[] modelPart5 = {
                "CHAPTER 1",
                "CHAPTER 2",
                "CHAPTER 3",
                "CHAPTER 4"
        };
        String[] modelPart6 = {
                "SECTION 1",
                "SECTION 2",
                "CHAPTER 1",
                "CHAPTER 2",
                "CHAPTER 3",
                "CHAPTER 4",
                "CHAPTER 5"
        };

        String[] modelPart7 = {
                "PART VII",
                "CHAPTER 1",
                "CHAPTER 2",
                "CHAPTER 3",
                "CHAPTER 4"
        };

        String[] modelPart8 = {
                "CHAPTER 1",
                "CHAPTER 2",
                "CHAPTER 3",
                "CHAPTER 4",
                "CHAPTER 5",
                "CHAPTER 6",
                "CHAPTER 7"
        };

        String[] modelPart9 = {
                "PART IX",
                "CHAPTER 1",
                "CHAPTER 2",
                "CHAPTER 3",
                "CHAPTER 4",
                "CHAPTER 5",
                "CHAPTER 6"
        };

        String[] modelPart10 = {
            "PART X"
        };

        String[] modelIndexOfTopics = {
                "A",
                "B",
                "C",
                "D",
                "E",
                "F",
                "G",
                "H",
                "I",
                "J",
                "K",
                "L",
                "M",
                "N",
                "O",
                "P",
                "R",
                "S",
                "T",
                "U",
                "V",
                "W",
                "Y",
                "Z"
        };

        groupwithList = new LinkedHashMap<String, List<String>>();

        for (String group : groupListTitle) {
            if (group.equals("INTRODUCTION")) {
                loadChild(modelIntroduction);
            } else if (group.equals("FOREWORD")) {
                loadChild(modelPreface);
            } else if (group.equals("PREFACE")) {
                loadChild(modelGuide);
            } else if (group.equals("PREFACE TO THE FIRST EDITION OF THE CONSTITUTIONS")) {
                loadChild(modelJesuitdictionary);
            } else if (group.equals("PRINCIPAL ABBREVIATIONS")) {
                loadChild(modelPrincipalabbreviations);
            } else if (group.equals("FORMULAS OF THE INSTITUTE OF THE SOCIETY OF JESUS")) {
                loadChild(modelJuliusandPaul);
            } else if (group.equals("Faculty to Establish Constitutions and the Approbations of the Institute in General and of the Constitutions in Particular")) {
                loadChild(modelFacultytoestablish);
            } else if (group.equals("Preliminary Remarks Concerning the Notes Added to the Constitutions")) {
                loadChild(modelPreliminaryRemarks);
            } else if (group.equals("CHAPTER ONE")) {
                loadChild(modelChapterOne);
            } else if (group.equals("CHAPTER TWO")) {
                loadChild(modelChapterTwo);
            } else if (group.equals("CHAPTER THREE")) {
                loadChild(modelChapterThree);
            } else if (group.equals("CHAPTER FOUR")) {
                loadChild(modelChapterFour);
            } else if (group.equals("CHAPTER FIVE")) {
                loadChild(modelChapterFive);
            } else if (group.equals("CHAPTER SIX")) {
                loadChild(modelChapterSix);
            } else if (group.equals("CHAPTER SEVEN")) {
                loadChild(modelChapterSeven);
            } else if (group.equals("CHAPTER EIGHT")) {
                loadChild(modelChapterEight);
            } else if (group.equals("THE CONSTITUTIONS OF THE SOCIETY OF JESUS AND THEIR DECLARATIONS")) {
                loadChild(modelPreambles);
            } else if (group.equals("PART I")) {
                loadChild(modelPart1);
            } else if (group.equals("PART II")) {
                loadChild(modelPart2);
            } else if (group.equals("PART III")) {
                loadChild(modelPart3);
            } else if (group.equals("PART IV")) {
                loadChild(modelPart4);
            } else if (group.equals("PART V")) {
                loadChild(modelPart5);
            } else if (group.equals("PART VI")) {
                loadChild(modelPart6);
            } else if (group.equals("PART VII")) {
                loadChild(modelPart7);
            } else if (group.equals("PART VIII")) {
                loadChild(modelPart8);
            } else if (group.equals("PART IX")) {
                loadChild(modelPart9);
            } else if (group.equals("PART X")) {
                loadChild(modelPart10);
            } else if (group.equals("INDEX OF TOPICS")) {
                loadChild(modelIndexOfTopics);
            }


            groupwithList.put(group, childList);
        }
    }

    private void initGroupList() {
        groupListTitle = new ArrayList<>();

        groupListTitle.add("INTRODUCTION");
        groupListTitle.add("FOREWORD");
        groupListTitle.add("PREFACE");
        groupListTitle.add("PREFACE TO THE FIRST EDITION OF THE CONSTITUTIONS");
        groupListTitle.add("PRINCIPAL ABBREVIATIONS");
        groupListTitle.add("FORMULAS OF THE INSTITUTE OF THE SOCIETY OF JESUS");
        groupListTitle.add("Faculty to Establish Constitutions and the Approbations of the Institute in General and of the Constitutions in Particular");
        groupListTitle.add("Preliminary Remarks Concerning the Notes Added to the Constitutions");
        groupListTitle.add("CHAPTER ONE");
        groupListTitle.add("CHAPTER TWO");
        groupListTitle.add("CHAPTER THREE");
        groupListTitle.add("CHAPTER FOUR");
        groupListTitle.add("CHAPTER FIVE");
        groupListTitle.add("CHAPTER SIX");
        groupListTitle.add("CHAPTER SEVEN");
        groupListTitle.add("CHAPTER EIGHT");
        groupListTitle.add("THE CONSTITUTIONS OF THE SOCIETY OF JESUS AND THEIR DECLARATIONS");
        groupListTitle.add("PART I");
        groupListTitle.add("PART II");
        groupListTitle.add("PART III");
        groupListTitle.add("PART IV");
        groupListTitle.add("PART V");
        groupListTitle.add("PART VI");
        groupListTitle.add("PART VII");
        groupListTitle.add("PART VIII");
        groupListTitle.add("PART IX");
        groupListTitle.add("PART X");
        groupListTitle.add("INDEX OF TOPICS");

    }

    private void loadChild(String[] childModel) {
        childList = new ArrayList<>();

        for (String model : childModel) {
            childList.add(model);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.constitution_main_menu, menu);
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
}