package com.example.android.horizontalpaging;

import java.io.*;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import android.content.Intent;

// calendar imports
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CalendarContract;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

//begin calendar code
/**
    // source: http://www.emberex.com/using-androids-built-in-calendarprovider-part-one-retrieving-calendars/
    ContentResolver resolver = getContentResolver();
    // a list of which fields we want to retrieve for each calendar
    String[] EVENT_PROJECTION = new String[]{CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CalendarContract.Calendars.ACCOUNT_NAME, CalendarContract.Calendars.OWNER_ACCOUNT};
    // the uri to query (provided by the Calendars class)
    Uri uri = CalendarContract.Calendars.CONTENT_URI;

    // create a filter to retrieve calendars we want
    // in this instance, we have an id of a calendar we want to find
    String selection = "(" + CalendarContract.Calendars._ID + " = ?)";
    String[] selectionArgs = new String[]{id};

    // resolve the query and get a cursor (similar to iterator in java.util) to collection of calendars
    Cursor cursor = resolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
*/
//end calendar code


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    // using inside onCreate
    CalendarView calendar;


    //MapTab mapview;
    /**
     * Create the activity. Sets up an {@link ActionBar} with tabs, and then configures the
     * {@link ViewPager} contained inside R.layout.activity_main.
     *
     * <p>A {@link SectionsPagerAdapter} will be instantiated to hold the different pages of
     * fragments that are to be displayed. A
     * {@link ViewPager.SimpleOnPageChangeListener} will also be configured
     * to receive callbacks when the user swipes between pages in the ViewPager.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the UI from res/layout/activity_main.xml
        setContentView(R.layout.main);

        // Set up the action bar. The navigation mode is set to NAVIGATION_MODE_TABS, which will
        // cause the ActionBar to render a set of tabs. Note that these tabs are *not* rendered
        // by the ViewPager; additional logic is lower in this file to synchronize the ViewPager
        // state with the tab state. (See mViewPager.setOnPageChangeListener() and onTabSelected().)
        // BEGIN_INCLUDE (set_navigation_mode)
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // END_INCLUDE (set_navigation_mode)
        // to hide action bar
        //actionBar.hide();

        // to set image background of action bar
                //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bar_white));
        // to set image for background of ALL tabs simultaneously

        actionBar.setStackedBackgroundDrawable(getResources().getDrawable(R.drawable.bar_fake));


        //hides action bar
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);

        // to set action bar icon
        //actionBar.setIcon(R.drawable.uh_icon);

        // to set action bar title
        //actionBar.setTitle("Summer School App");
        // BEGIN_INCLUDE (setup_view_pager)
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // END_INCLUDE (setup_view_pager)

        // When swiping between different sections, select the corresponding tab. We can also use
        // ActionBar.Tab#select() to do this if we have a reference to the Tab.
        // BEGIN_INCLUDE (page_change_listener)
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
           public void onPageSelected(int position) {
               actionBar.setSelectedNavigationItem(position);
           }
        });
        // END_INCLUDE (page_change_listener)

        // BEGIN_INCLUDE (add_tabs)
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter. Also
            // specify this Activity object, which implements the TabListener interface, as the
            // callback (listener) for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            //disabled this to remove unnecessary text in icon bar.
                            //.setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));


        }
        // END_INCLUDE (add_tabs)
    }

    /**
     * triggers the google map intent. Future versions we will be passing String parameters to correlate with schedule.
     */
    public void sendMessageR() {
        //Intent mapIntent = new Intent(this, MapsRedActivity.class);
        //String query = "bilger addition";
        String query = "UH Manoa";

        //--------------------------------
        //*******DEMONSTRATION LINE******
        //-------------------------------
        // this commented out code sets the zoom to UH Manoa. used for testing without GPS (like at home laptop)
        //Uri gmmIntentUri = Uri.parse("geo:21.294088,-157.820490?q=" + Uri.encode(query));

        //--------------------------------------------------------
        //***********ACTUAL LINES TO BE USED IN LIVE.
        //--------------------------------------------------------
        // Enable code below and disable top when using on phone.
        //this Uri intent uses GPS and navigates from your location
        query = query.replace(" ", "+");
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + query);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        //I think we'll have to change the package being passed to a package being handled by our app to keep it in frame? maybe?
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    // opens option to manually add an event to a google calendar of your choice from the app
    // newly created event currently taking ~5 min to sync with goog calendar
    public void addEvents() {
        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(CalendarContract.Events.CONTENT_URI);
        startActivity(calIntent);
    }

    // opens users google calendar
    // source: http://www.grokkingandroid.com/intents-of-androids-calendar-app/
    // can experiment with other options: http://developer.android.com/guide/components/intents-common.html
    public void readEvents() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 2);
        long time = cal.getTime().getTime();
        Uri.Builder builder =
                CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        builder.appendPath(Long.toString(time));
        Intent intent =
                new Intent(Intent.ACTION_VIEW, builder.build());
        startActivity(intent);
    }

    public void readWeb(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Update {@link ViewPager} after a tab has been selected in the ActionBar.
     *
     * @param tab Tab that was selected.
     * @param fragmentTransaction A {@link android.app.FragmentTransaction} for queuing fragment operations to
     *                            execute once this method returns. This FragmentTransaction does
     *                            not support being added to the back stack.
     */
    // BEGIN_INCLUDE (on_tab_selected)
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, tell the ViewPager to switch to the corresponding page.
        mViewPager.setCurrentItem(tab.getPosition());

        //triggers google map intent when on tab 1 (form order of 0, 1, 2 left to right)
        if(tab.getPosition() == 1) {
            sendMessageR();
        }
        //triggers calendar reader
        if(tab.getPosition() == 2) {
            System.out.println("selected calendar tab");
            // next line opens screen to add events to google calendar via the app
            //addEvents();
            // next line opens calendar screen with user's google calendar
            readEvents();

            //test with web browsing intent
            //readWeb("http://www.hawaii.edu/calendar/manoa/");
        }

        //triggers web browser, currently set to fourth (non-existent) tab
        if(tab.getPosition() == 3) {
            readWeb("http://www.hawaii.edu/calendar/manoa/");
        }
    }
    // END_INCLUDE (on_tab_selected)

    /**
     * Unused. Required for {@link android.app.ActionBar.TabListener}.
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Unused. Required for {@link android.app.ActionBar.TabListener}.
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    // BEGIN_INCLUDE (fragment_pager_adapter)
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages. This provides the data for the {@link ViewPager}.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    // END_INCLUDE (fragment_pager_adapter)

        int icons[] = {R.drawable.mail_icon, R.drawable.map_icon, R.drawable.cal_icon};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // BEGIN_INCLUDE (fragment_pager_adapter_getitem)
        /**
         * Get fragment corresponding to a specific position. This will be used to populate the
         * contents of the {@link ViewPager}.
         *
         * @param position Position to fetch fragment for.
         * @return Fragment for specified position.
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment;

                fragment = new DummySectionFragment();
                Bundle args = new Bundle();
                args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
                fragment.setArguments(args);

            return fragment;
        }
        // END_INCLUDE (fragment_pager_adapter_getitem)

        // BEGIN_INCLUDE (fragment_pager_adapter_getcount)
        /**
         * Get number of pages the {@link ViewPager} should render.
         *
         * @return Number of fragments to be rendered as pages.
         */
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
        // END_INCLUDE (fragment_pager_adapter_getcount)

        // BEGIN_INCLUDE (fragment_pager_adapter_getpagetitle)
        /**
         * Get title for each of the pages. This will be displayed on each of the tabs.
         *
         * @param position Page to fetch title for.
         * @return Title for specified page.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

     // attempting to add icons to tabs
            //reference: https://www.youtube.com/watch?v=WSaSNX5QI-E

    /**         Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),icons[position]);
            drawable.setBounds(0,0, 20, 20);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spanString = new SpannableString(" ");
            spanString.setSpan(imageSpan, 0, spanString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spanString;
    **/
          //  TextView textView = (TextView)findViewById(R.id.section_label);
            //CharSequence testTab = (CharSequence) findViewById(R.id.section_label);

            Spannable ss = new SpannableString(" ");
             Drawable d = getResources().getDrawable(R.drawable.map_icon);
             d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
             ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
             ss.setSpan(span, 3, 4, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
         //   textView.setText(ss);
         //   String testTab = ss.toString();
        /**
           switch (position) {
                case 0:
                    return ss;
                    //return "Home".toUpperCase(l);
                case 1:
                    return "Map".toUpperCase(l);
                case 2:
                    return "Calendar".toUpperCase(l);
            } **/
            return ss;
        }

        // END_INCLUDE (fragment_pager_adapter_getpagetitle)
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     * This would be replaced with your application's content.
     */
    public class DummySectionFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";

        public DummySectionFragment() {
        }


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
            // creates view with activity_calendar.xml
            View calView = inflater.inflate(R.layout.activity_calendar, container, false);
            //SomeFragment map = new SomeFragment();
            View mapView = inflater.inflate(R.layout.activity_map, container, false);
           // View mapView = SomeFragment.OnCreateView(inflater, container, savedInstanceState);

            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);


            ImageView image = (ImageView) rootView.findViewById(R.id.bodyImg);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    image.setImageResource(R.drawable.events_pic);
                    return rootView;
                case 2:
                    return mapView;
                case 3:
                    // return calendar widget
                    return calView;
            }

            return rootView;
        }
    }


}
