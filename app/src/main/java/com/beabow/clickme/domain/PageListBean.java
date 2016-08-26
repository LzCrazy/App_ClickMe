package com.beabow.clickme.domain;

import java.util.List;

/**
 * 创建者： lx
 * 创建时间：2016/6/3 11:59
 * 描述: 每个页面的所有数据
 */
public class PageListBean {

    private String add_time; //添加时间
    private String age; //年龄
    private String bust;//胸围
    private String waistline; //腰围
    private String hipline;//臀围
    private String cate;//
    private String gc_id_2; //分类ID
    private String goods_name; //技师名字
    private String id;  //技师ID
    private String is_click; //是否被选中
    private String is_clock; //是否在上钟
    private String is_work;  //是否在工作
    private String job_number; //技师工号
    private String list_img; //技师图片
    private String motto;  //座右铭
    private String shop_id; //店铺ID
    private List<String> banner; //技师相册

    @Override
    public String toString() {
        return "PageListBean{" +
                "add_time='" + add_time + '\'' +
                ", age='" + age + '\'' +
                ", bust='" + bust + '\'' +
                ", waistline='" + waistline + '\'' +
                ", hipline='" + hipline + '\'' +
                ", cate='" + cate + '\'' +
                ", gc_id_2='" + gc_id_2 + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", id='" + id + '\'' +
                ", is_click='" + is_click + '\'' +
                ", is_clock='" + is_clock + '\'' +
                ", is_work='" + is_work + '\'' +
                ", job_number='" + job_number + '\'' +
                ", list_img='" + list_img + '\'' +
                ", motto='" + motto + '\'' +
                ", shop_id='" + shop_id + '\'' +
                ", banner=" + banner +
                '}';
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getGc_id_2() {
        return gc_id_2;
    }

    public void setGc_id_2(String gc_id_2) {
        this.gc_id_2 = gc_id_2;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getHipline() {
        return hipline;
    }

    public void setHipline(String hipline) {
        this.hipline = hipline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_click() {
        return is_click;
    }

    public void setIs_click(String is_click) {
        this.is_click = is_click;
    }

    public String getIs_clock() {
        return is_clock;
    }

    public void setIs_clock(String is_clock) {
        this.is_clock = is_clock;
    }

    public String getIs_work() {
        return is_work;
    }

    public void setIs_work(String is_work) {
        this.is_work = is_work;
    }

    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public String getList_img() {
        return list_img;
    }

    public void setList_img(String list_img) {
        this.list_img = list_img;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getWaistline() {
        return waistline;
    }

    public void setWaistline(String waistline) {
        this.waistline = waistline;
    }

    public List<String> getBanner() {
        return banner;
    }

    public void setBanner(List<String> banner) {
        this.banner = banner;
    }
}
