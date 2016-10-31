package skkk.gogogo.dakainote.Activity.AuthorActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.ValueEventListener;
import skkk.gogogo.dakainote.Adapter.AuthorContentAdapter;
import skkk.gogogo.dakainote.Bean.Communication;
import skkk.gogogo.dakainote.MyUtils.DateUtils;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.Presenter.AuthorPresenter;
import skkk.gogogo.dakainote.R;

public class AuthorActivity extends AppCompatActivity {
    private AuthorPresenter authorPresenter;
    private BmobRealTimeData rtd;
    private EditText etAuthorContnet;
    private Button btnAuthorSend;
    private RecyclerView rvContent;
    private AuthorContentAdapter adapter;
    private List<Communication> communicationsList=new ArrayList<Communication>();
    private LinearLayoutManager mLinearLayoutManager;
    private Toolbar tbAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authorPresenter=new AuthorPresenter(this);
        rtd = new BmobRealTimeData();
        initBmob();

        setMyTheme();
        initUI();
        initEvent();

    }

    /*
    * @方法 设置主题
    * @参数
    * @返回值
    */
    public void setMyTheme() {
        //设置主题
        if (authorPresenter.getThemeNight()) {
            setTheme(R.style.AppThemeNight);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    /*
    * @方法 获取data
    * @参数
    * @返回值
    */
    private void initData() {
        BmobQuery<Communication> query = new BmobQuery<Communication>();
        //查询playerName叫“比目”的数据
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<Communication>() {
            @Override
            public void done(List<Communication> list, BmobException e) {

                for (int i = 0; i < list.size(); i++) {
                    LogUtils.Log("查询Contnet： "+list.get(i).getContent());
                    LogUtils.Log("查询Time： "+list.get(i).getTime());
                }
                communicationsList=list;
                adapter.setItemDataList(communicationsList);
                adapter.notifyDataSetChanged();
                rvContent.smoothScrollToPosition(communicationsList.size()-1);
            }
        });
    }


    /*
    * @方法 初始化UI
    * @参数
    * @返回值
    */
    private void initUI() {
        setContentView(R.layout.activity_author);

        etAuthorContnet= (EditText) findViewById(R.id.et_author_content);
        btnAuthorSend= (Button) findViewById(R.id.btn_author_send);

        tbAuthor = (Toolbar) findViewById(R.id.tb_author);
        setSupportActionBar(tbAuthor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tbAuthor.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        /* @描述 获取RecyclerView实例 */
        rvContent = (RecyclerView)findViewById(R.id.rv_author_content);

        //LogUtils.Log("此时此刻++++"+communicationsList.toString());

        /* @描述 设置Adapter */
        adapter = new AuthorContentAdapter(this,communicationsList);
        /* @描述 布局 */
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvContent.setLayoutManager(mLinearLayoutManager);
        /* @描述 设置基本动画 */
        rvContent.setItemAnimator(new DefaultItemAnimator());
        /* @描述 rvNoteList */
        rvContent.setAdapter(adapter);
    }

    /*
    * @方法 监听事件
    * @参数
    * @返回值
    */
    private void initEvent() {
        btnAuthorSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication comm = new Communication();
                comm.setContent(etAuthorContnet.getText().toString());
                comm.setTime(DateUtils.getTime());
                comm.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            //Toast.makeText(AuthorActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AuthorActivity.this, "添加数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /*
    * @方法 初始化Bmob实时数据
    * @参数
    * @返回值
    */
    private void initBmob() {
        rtd.start(new ValueEventListener() {
            @Override
            public void onDataChange(final JSONObject arg0) {
               LogUtils.Log(arg0.toString());
                if(BmobRealTimeData.ACTION_UPDATETABLE.equals(arg0.optString("action"))){
                    final JSONObject data = arg0.optJSONObject("data");
                    Communication commNew=new Communication();
                    commNew.setContent(data.optString("content"));
                    commNew.setTime(data.optString("time"));
                    communicationsList.add(commNew);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setItemDataList(communicationsList);
                            adapter.notifyDataSetChanged();
                            rvContent.smoothScrollToPosition(communicationsList.size()-1);
                        }
                    });
                }
            }

            @Override
            public void onConnectCompleted(Exception ex) {
                LogUtils.Log("连接成功:"+rtd.isConnected());
                if(rtd.isConnected()){
                    // 监听表更新
                    rtd.subTableUpdate("Communication");
                    initData();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rtd.unsubTableUpdate("Communication");
    }
}
