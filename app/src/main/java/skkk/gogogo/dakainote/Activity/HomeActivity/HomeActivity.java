package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import skkk.gogogo.dakainote.Activity.NoteEditActivity.BaseNoteEditActivity;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.Utils.RecyclerViewDecoration.PermissionUtils;

public class HomeActivity extends BaseHomeActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NoteListFragment noteListFragment;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeStart();
        initUI();

    }

    /*
     * 写在初始化UI之前
     * */
    private void beforeStart() {
        app= (MyApplication) getApplication();
        if(PermissionUtils.requestPermission(this)){
            Log.d("SKKK_____","判断权限存在");
        }else {
            Log.d("SKKK_____","判断权限缺失");
        }
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //加入默认的Fragment界面
        addDefaultFragment();
    }

    /*
     * @desc 加入默认的Fragment界面
     * @时间 2016/8/1 21:44
     */
    private void addDefaultFragment() {
        noteListFragment = new NoteListFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_home, noteListFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent=new Intent();
            intent.setClass(this, BaseNoteEditActivity.class);
            startActivityForResult(intent, app.getRequestCode_1());
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==app.getRequestCode_1()&&resultCode==RESULT_OK){
            Note noteFromEdit = (Note) data.getExtras().get("note_form_edit");
            Log.d("SKKK_____",noteFromEdit.getContent());
            //获取当前fragment
            NoteListFragment noteListFragment= (NoteListFragment) getSupportFragmentManager().
                    findFragmentById(R.id.fl_home);
            noteListFragment.updateList(0,noteFromEdit);
        }
    }
}
