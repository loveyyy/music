package com.example.music.ui.frament;

import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.music.R;
import com.example.music.databinding.FramentMvBinding;
import com.example.music.model.Artist_Mv;
import com.example.music.model.BaseRespon;
import com.example.music.ui.adapter.Gv_artist_Mv_apt;
import com.example.music.ui.base.BaseFrament;
import com.example.music.viewmodel.FramentMv_VM;

/**
 * Create By morningsun  on 2019-12-05
 */
public class Framnet_MV extends BaseFrament<FramentMvBinding> {
    private FramentMvBinding framentMvBinding;
    private FramentMv_VM framentMv_vm;

//    @SuppressLint("HandlerLeak")
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            framentMineBinding.btnMyDownload.setText("下载中("+TaskDispatcher.getInstance().getQueueTaskList().size()+")");
//            framentMineBinding.btnMyDownloadFinish.setText("完成下载("+TaskDispatcher.getInstance().getDownloadedList().size()+")");
//            if(tag==1){
//                downLoadApt.notifyData();
//            }
//            if(tag==2){
//                downLoadApt.notifyData1();
//            }
//        }
//    };


    @Override
    public int getContentViewId() {
        return R.layout.frament_mv;
    }

    @Override
    protected void initview(FramentMvBinding bindView) {
        framentMvBinding=bindView;
        framentMv_vm = ViewModelProviders.of(this).get(FramentMv_VM.class);
    }

    @Override
    protected void SetVM() {
        framentMv_vm.mv_list.observe(this, new Observer<BaseRespon<Artist_Mv>>() {
            @Override
            public void onChanged(BaseRespon<Artist_Mv> mv_listBaseRespon) {
                Gv_artist_Mv_apt gv_artist_mv_apt=new Gv_artist_Mv_apt(getMContext(),mv_listBaseRespon.getData().getMvlist());
                framentMvBinding.gvMv.setAdapter(gv_artist_mv_apt);
                framentMvBinding.gvMv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //播放mv
                    }
                });
            }
        });
    }


    @Override
    protected void setonclick() {

    }

    @Override
    protected void initData() {
        framentMv_vm.Get_Mv_List("236682871","1","30");
    }

    //    private  void Start(){
//        if(timer==null){
//            timer=new Timer();
//        }
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//               handler.sendEmptyMessage(10);
//            }
//        },0,20);
//    }

//    public void scanFileAsync(Context ctx, String filePath) {
//        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        scanIntent.setData(Uri.fromFile(new File(filePath)));
//        ctx.sendBroadcast(scanIntent);
//    }

}
