package skkk.gogogo.dakainote.Activity.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.litepal.crud.DataSupport;

import skkk.gogogo.dakainote.Activity.MyTestActivity;
import skkk.gogogo.dakainote.Activity.NoteEditActivity.UINoteEditActivity;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.ArcMenuView;

/*
*
* 这里包含了所有的UI初始化
* @作者 admin
* @时间 2016/8/11 21:08
*
*/
public class UIHomeActivity extends BaseHomeActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected NoteListFragment noteListFragment;
    protected static final int REQUEST_CODE_1=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        arcMenuView= (ArcMenuView) findViewById(R.id.arc_menu_view_home);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置卫星菜单点击事件
        arcMenuView.setmMenuItemClickListener(new ArcMenuView.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                switch (pos){
                    case 1:
                        Intent intent=new Intent();
                        intent.setClass(UIHomeActivity.this, UINoteEditActivity.class);
                        startActivityForResult(intent,REQUEST_CODE_1);
                        break;
                    case 2:
                        startActivity(new Intent(UIHomeActivity.this, MyTestActivity.class));
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });

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
            intent.setClass(this, UINoteEditActivity.class);
            startActivityForResult(intent,REQUEST_CODE_1);

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

    /*
    * @方法 从editNote中获取返回的数据来刷新list
    *
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_1&&resultCode==RESULT_OK){
            Note noteFromEdit = (Note) data.getExtras().get("note_form_edit");
            Log.d("SKKK_____", noteFromEdit.getContent());
            //获取当前fragment
            NoteListFragment noteListFragment= (NoteListFragment) getSupportFragmentManager().
                    findFragmentById(R.id.fl_home);
            int count = DataSupport.count(Note.class);
            noteListFragment.updateList(0,noteFromEdit);
        }
    }



}
