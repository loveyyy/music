package com.example.music.ui.activity;

import android.os.Build;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.music.BR;
import com.example.music.R;
import com.example.music.databinding.SearchactivityBinding;
import com.example.music.http.Api;
import com.example.music.http.ApiResponse;
import com.example.music.http.RxHelper;
import com.example.music.model.BaseRespon;
import com.example.music.model.DownLoadInfo;
import com.example.music.model.DownlodMusciInfo;
import com.example.music.model.Search;
import com.example.music.server.TaskDispatcher;
import com.example.music.ui.base.BaseAdapter;
import com.example.music.ui.base.BaseActivity;
import com.example.music.viewmodel.SearchVM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By morningsun  on 2020-06-24
 */
public class SearchActivity extends BaseActivity<SearchactivityBinding, SearchVM> {
    private SearchactivityBinding searchactivityBinding;
    private SearchVM search_vm;

    @Override
    public int getLayout() {
        return R.layout.searchactivity;
    }

    @Override
    public boolean isLight() {
        return false;
    }

    @Override
    protected void initView(SearchactivityBinding bindView) {
        searchactivityBinding=bindView;
    }

    @Override
    protected void setVM(SearchVM vm) {
        search_vm=vm;
        search_vm.keyList.observe(this, new Observer<BaseRespon<List<String>>>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onChanged(BaseRespon<List<String>> listBaseRespon) {
                final List<String> strings=new ArrayList<>();
                for(String a:listBaseRespon.getData()){
                    if(a.contains("\r\n")){
                        strings.add(a.split("\r\n")[0].split("=")[1]);
                    }else{
                        strings.add(a);
                    }

                }
                searchactivityBinding.etSearch.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,strings));
                searchactivityBinding.etSearch.setThreshold(0);
                searchactivityBinding.etSearch.showDropDown();

                searchactivityBinding.etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        searchactivityBinding.etSearch.setText(strings.get(position));
                        search_vm.searchMusic(strings.get(position));
                    }
                });

                searchactivityBinding.etSearch.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        View v = getWindow().peekDecorView();
                        if (null != v) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                });

            }
        });

        search_vm.search.observe(this, new Observer<BaseRespon<Search>>() {
            @Override
            public void onChanged(final BaseRespon<Search> listBaseRespon) {
                searchactivityBinding.rcvSearch.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
                BaseAdapter<Search.ListBean> baseAdapter=new BaseAdapter<>(getContext(),listBaseRespon.getData().getList(),R.layout.searchapt, BR.searchapt);
                searchactivityBinding.rcvSearch.setAdapter(baseAdapter);

                baseAdapter.setOnDownLoad(new BaseAdapter.OnDownLoad() {
                    @Override
                    public void OnDownLoadListener(int pos) {
                        Api.getInstance().iRetrofit.downloadMusic(listBaseRespon.getData().getList().get(pos).getMusicrid().split("_")[1],
                                "kuwo","id",1,"XMLHttpRequest")
                                .compose(RxHelper.observableIO2Main(getApplicationContext()))
                                .subscribe(new ApiResponse<BaseRespon<List<DownlodMusciInfo>>>() {
                                    @Override
                                    public void success(BaseRespon<List<DownlodMusciInfo>> data) {
                                        DownLoadInfo downLoadInfo=new DownLoadInfo();
                                        downLoadInfo.setUrl(data.getData().get(0).getUrl());
                                        downLoadInfo.setFilename(data.getData().get(0).getAuthor()+"-"+data.getData().get(0).getTitle()+".mp3");
                                        downLoadInfo.setFilepath(Environment.getExternalStorageDirectory().getPath() + File.separator + "mv");

                                        TaskDispatcher.getInstance().enqueue(downLoadInfo);
                                    }

                                });
                    }
                });
            }
        });
    }

    @Override
    protected void setListener() {

        searchactivityBinding.ivSearchMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 search_vm.searchMusic(searchactivityBinding.etSearch.getText().toString());
            }
        });


        searchactivityBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    search_vm.searchKey(s.toString());
            }
        });
    }

    @Override
    protected void initData() {
        searchactivityBinding.etSearch.setFocusable(true);
        searchactivityBinding.etSearch.setFocusableInTouchMode(true);
        searchactivityBinding.etSearch.requestFocus();
        search_vm.searchKey("");
    }


}
