package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.bean.Province;
import com.lingyan.banquet.databinding.DialogPersonSelectBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetSinglePersonList;
import com.lingyan.banquet.views.SideIndexBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by _hxb on 2021/2/14.
 */

public class PersonPickerDialog extends BaseDialog {

    private final DialogPersonSelectBinding mBinding;
    private List<MultiItemEntity> mRecData;
    private MyAdapter mAdapter;
    private OnPersonClickListener mOnPersonClickListener;

    public static interface OnPersonClickListener {
        void onPersonClick(String name, String id, PersonPickerDialog dialog);
    }

    public PersonPickerDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogPersonSelectBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
    }


    private void init() {
        mRecData = new ArrayList<>();
        mAdapter = new MyAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mConstructContext));
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MultiItemEntity multiItemEntity = mRecData.get(i);
                if (multiItemEntity instanceof Person) {
                    Person person = (Person) multiItemEntity;
                    if (mOnPersonClickListener != null) {
                        mOnPersonClickListener.onPersonClick(person.name, person.id, PersonPickerDialog.this);
                    }
                }
            }
        });
        mBinding.viewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setOnPersonClickListener(OnPersonClickListener onPersonClickListener) {
        mOnPersonClickListener = onPersonClickListener;
    }

    @Override
    public void show() {
        super.show();
        OkGo.<NetSinglePersonList>post(HttpURLs.listSinglePerson)
                .tag(PersonPickerDialog.this)
//                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .execute(new JsonCallback<NetSinglePersonList>() {
                    @Override
                    public void onSuccess(Response<NetSinglePersonList> response) {
                        NetSinglePersonList body = response.body();
                        if (body == null) {
                            return;
                        }
                        List<NetSinglePersonList.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }
                        for (NetSinglePersonList.DataDTO dataDTO : list) {
                            String first = dataDTO.getFirst();
                            if (StringUtils.isTrimEmpty(first)) {
                                dataDTO.setFirst("#");
                            }
                        }
                        mRecData.clear();
                        mAdapter.setNewData(mRecData);
                        Collections.sort(list, new Comparator<NetSinglePersonList.DataDTO>() {
                            @Override
                            public int compare(NetSinglePersonList.DataDTO o1, NetSinglePersonList.DataDTO o2) {
                                String o1First = o1.getFirst();
                                String o2First = o2.getFirst();
                                return o1First.compareTo(o2First);
                            }
                        });
                        String letter = null;
                        List<String> letterList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            NetSinglePersonList.DataDTO dto = list.get(i);
                            String first = dto.getFirst();
                            if (!StringUtils.equals(letter, first)) {
                                //先创建字母
                                letter = first;
                                PersonLetter personLetter = new PersonLetter();
                                personLetter.letter = letter;
                                mRecData.add(personLetter);
                                letterList.add(letter);
                            }
                            Person person = new Person();
                            person.name = dto.getRealname();
                            person.id = dto.getId();
                            mRecData.add(person);
                        }
                        mAdapter.notifyDataSetChanged();
                        mBinding.slideIndexBar.setLetterList(letterList);
                        mBinding.slideIndexBar.setOnIndexChangedListener(new SideIndexBar.OnIndexTouchedChangedListener() {
                            @Override
                            public void onIndexChanged(String index, int position) {
                                for (int i = 0; i < mRecData.size(); i++) {
                                    MultiItemEntity multiItemEntity = mRecData.get(i);
                                    if (multiItemEntity instanceof PersonLetter) {
                                        PersonLetter personLetter = (PersonLetter) multiItemEntity;
                                        if (StringUtils.equals(personLetter.letter, index)) {
                                            mBinding.recyclerView.smoothScrollToPosition(i);
                                            return;
                                        }
                                    }
                                }

                            }
                        });
                    }
                });

    }

    @Override
    public void dismiss() {
        super.dismiss();
        OkGo.getInstance().cancelTag(PersonPickerDialog.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getAppScreenWidth());
        attributes.height = (int) (ScreenUtils.getAppScreenHeight() * 0.8f);
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.view_bottom_slide_anim);
    }

    private class Person implements MultiItemEntity {
        private String name;
        private String id;

        @Override
        public int getItemType() {
            return 2;
        }
    }

    private class PersonLetter implements MultiItemEntity {
        private String letter;

        @Override
        public int getItemType() {
            return 1;
        }
    }

    private class MyAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

        public MyAdapter(List<MultiItemEntity> data) {
            super(data);
            addItemType(1, R.layout.item_person_picker_letter);
            addItemType(2, R.layout.item_person_picker_name);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, MultiItemEntity multiItemEntity) {
            int itemType = multiItemEntity.getItemType();
            switch (itemType) {
                case 1: {
                    PersonLetter letter = (PersonLetter) multiItemEntity;
                    holder.setText(R.id.tv_letter, letter.letter);
                    break;
                }
                case 2: {
                    Person person = (Person) multiItemEntity;
                    holder.setText(R.id.tv_name, person.name);
                    break;
                }
            }
        }
    }

}
