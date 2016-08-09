package com.zsh.xuexi.newsecondapp.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/6/19
 * 自定义基础适配器
 */
public abstract class MyBaseAdapter<DataType> extends BaseAdapter {
    protected Context cont;
    protected LayoutInflater layoutInflater;
    //当前适配器内所用的数据
    private List<DataType> dataList=new ArrayList<DataType>();

    public MyBaseAdapter(Context cont){
        this.cont=cont;
        layoutInflater= (LayoutInflater) cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //获取适配器内所用数据列表（list集合）
    public List<DataType> getDataList(){
        return dataList;
    }

    //向适配器内所用数据列表（list集合），添加一个数据
    public void addDataToAdapter(DataType data){
        dataList.add(data);
    }

    //向适配器list最前面添加一个或多个数据
    public void addDataToAdapterHead(List<DataType> datas) {
        dataList.addAll(0, datas);
    }

    //向适配器list最后面添加一个或多个数据
    public void addDataToAdapterEnd(List<DataType> datas) {
        dataList.addAll(dataList.size(), datas);
    }

    /** 设置适配器内所用数据列表(List集合)内数据,为指定数据(将先清理原数据) */
    public void setDataToAdapter(DataType data) {
        dataList.clear();
        dataList.add(data);
    }

    /** 设置适配器内所用数据列表(List集合)内数据,为指定集合(将先清理原数据) */
    public void setDataToAdapter(List<DataType> datas) {
        dataList.clear();
        dataList.addAll(datas);
    }

    @Override
    public int getCount() {
        return dataList==null ? 0 :dataList.size();
    }

    @Override
    public DataType getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView, parent);
    }
    /**
     * 返回当前列表项视图效果(主要目标：将当前适配器内数据对应“适配”到布局各控件上)
     * @see内部使用观察者模式实现的callBack方法
     * @see当列表项第一次显示出来,当列表项滑出滑进屏幕时，当列表项数据变化，调用notifyDataSetChanged()方法时 ，都将来调用
     * @param position 当前列表项在列表中的位置
     */
    public abstract View getItemView(int position, View convertView, ViewGroup parent);
}
