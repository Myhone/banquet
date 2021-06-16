package com.lingyan.banquet.ui.apply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityFilterApplyBinding;
import com.lingyan.banquet.global.ApplyRecordType;
import com.lingyan.banquet.utils.GroupViewUtils;
import com.lingyan.banquet.utils.MyGsonUtils;
import com.lingyan.banquet.views.dialog.PersonPickerDialog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by _hxb on 2021/1/3.
 */

public class FilterApplyActivity extends BaseActivity {

    private ActivityFilterApplyBinding mBinding;
    private JsonObject mJsonObject;
    private GroupViewUtils mGroupViewUtils;

    public static void start(Activity activity ,String json,int code) {
        Intent intent = new Intent(activity, FilterApplyActivity.class);
        intent.putExtra("json", json);
        activity.startActivityForResult(intent,code);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFilterApplyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("筛选");

        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        if (json == null) {
            mJsonObject = new JsonObject();
        } else {
            mJsonObject = (JsonObject) JsonParser.parseString(json);
        }
        initGroup();

        //是否处理
        String by = MyGsonUtils.getString(mJsonObject, "by");
        if (StringUtils.equals(by, ApplyRecordType.TYPE_RECEIVED)) {
            mBinding.llStatusContainer.setVisibility(View.VISIBLE);
        } else {
            mBinding.llStatusContainer.setVisibility(View.GONE);
        }

        //审批提交人
        String createUserName = MyGsonUtils.getString(mJsonObject, "create_user_name");
        if (!StringUtils.isEmpty(createUserName)) {
            mBinding.tvCreateUser.setText(createUserName);
        } else {
            mBinding.tvCreateUser.setText("");
        }

        //时间
        String dateStart = MyGsonUtils.getString(mJsonObject, "date_start");
        String dateEnd = MyGsonUtils.getString(mJsonObject, "date_end");
        if (!StringUtils.isTrimEmpty(dateStart)) {
            mBinding.tvDateStart.setText(dateStart);
        } else {
            mBinding.tvDateStart.setText("");
        }
        if (!StringUtils.isTrimEmpty(dateEnd)) {
            mBinding.tvDateEnd.setText(dateEnd);
        } else {
            mBinding.tvDateEnd.setText("");
        }
        mBinding.tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始时间
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvDateStart.setText(string);
                        mJsonObject.addProperty("date_start", string);
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        mBinding.tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束时间
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvDateEnd.setText(string);
                        mJsonObject.addProperty("date_end", string);
                    }
                })
                        .setTitleText("请选择结束时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });

        //重置
        mBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.reset();
                mJsonObject.remove("status");
                mJsonObject.remove("check_status");
                mJsonObject.remove("type");
                mJsonObject.remove("e_type");

                mJsonObject.remove("create_user_name");
                mJsonObject.remove("create_user_id");
                mBinding.tvCreateUser.setText("");

                mBinding.tvDateStart.setText("");
                mBinding.tvDateEnd.setText("");
                mJsonObject.remove("date_end");
                mJsonObject.remove("date_start");
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> map = mGroupViewUtils.getSelectedValueMap();
                Set<Map.Entry<String, String>> entrySet = map.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value != null) {
                        mJsonObject.addProperty(key, value);
                    }
                }
                Intent i = new Intent();
                i.putExtra("json",mJsonObject.toString());
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });


        mBinding.tvCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
                dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
                    @Override
                    public void onPersonClick(String name, String id, PersonPickerDialog dialog) {
                        mJsonObject.addProperty("create_user_name", name);
                        mJsonObject.addProperty("create_user_id", id);
                        mBinding.tvCreateUser.setText(name);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void initGroup() {
        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add("status", mBinding.tvStatus1, "1");
        mGroupViewUtils.add("status", mBinding.tvStatus2, "2");

        mGroupViewUtils.add("check_status", mBinding.tvCheckStatus0, "0");
        mGroupViewUtils.add("check_status", mBinding.tvCheckStatus1, "1");
        mGroupViewUtils.add("check_status", mBinding.tvCheckStatus2, "2");
        mGroupViewUtils.add("check_status", mBinding.tvCheckStatus3, "3");

        mGroupViewUtils.add("type", mBinding.tvType1, "1");
        mGroupViewUtils.add("type", mBinding.tvType2, "2");

        mGroupViewUtils.add("e_type", mBinding.tvEType1, "1");
        mGroupViewUtils.add("e_type", mBinding.tvEType2, "2");

        mGroupViewUtils.select("status", MyGsonUtils.getString(mJsonObject, "status"));
        mGroupViewUtils.select("check_status", MyGsonUtils.getString(mJsonObject, "check_status"));
        mGroupViewUtils.select("type", MyGsonUtils.getString(mJsonObject, "type"));
        mGroupViewUtils.select("e_type", MyGsonUtils.getString(mJsonObject, "e_type"));
    }

}
