package com.zsh.xuexi.newsecondapp.http;

import android.util.Log;

import com.zsh.xuexi.newsecondapp.entity.HotInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zsh on 2016/6/19.
 */
public class Jiexi {
//    private static long mPicSize;
//    private static ByteArrayOutputStream out;
    /**
    * JSON 解析
     * 返回list
     * news解析
    */
    public static List<HashMap<String,Object>> JXJSON(String str){
        JSONObject jo=null;//用来判断拿到的数据是否是正确的
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>();//定义的list，用于适配器

        try {
            jo=new JSONObject(str);//转换成json对象
            int code=jo.getInt("error_code");//拿到返回码，判断拿到的是什么数据
//            Log.i("ms","code="+code);
            switch (code){
                case 0://成功拿到数据
//                    Log.i("ms","ok");
                    //执行解析操作
                    JSONArray jsonArray=jo.getJSONArray("result");//拿到news的集合
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObjet=jsonArray.getJSONObject(i);//拿到每一天news
                        //存到hashmap里面
                        HashMap<String,Object> hashMap=new HashMap<String,Object>();
                        hashMap.put("title",jsonObjet.getString("title"));//新闻标题
                        hashMap.put("content",jsonObjet.getString("content"));//新闻摘要内容
                        hashMap.put("img_width",jsonObjet.getString("img_width"));//图片宽度
                        hashMap.put("full_title",jsonObjet.getString("full_title"));//完整标题
                        hashMap.put("pdate",jsonObjet.getString("pdate"));//发布时间
                        hashMap.put("img_length",jsonObjet.getString("img_length"));//图片高度
                        hashMap.put("img",jsonObjet.getString("img"));//图片链接
                        hashMap.put("url",jsonObjet.getString("url"));//新闻链接
                        hashMap.put("pdate_src",jsonObjet.getString("pdate_src"));//发布完整时间
                        list.add(hashMap);
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.i("ms","llist="+list.toString());
//        return asd(list);
        return list;
    }

    /**
     * JSON 解析
     * 返回list
     * hot解析
     */
    public static List<HashMap<String,Object>> JSON_Hot(String str){
        JSONObject jo=null;//用来判断拿到的数据是否是正确的
        List<HashMap<String,Object>> list=new ArrayList<HashMap<String, Object>>();//定义的list，用于适配器
        try {
            jo=new JSONObject(str);//转换成json对象
            String reason=jo.getString("reason");//拿到返回是否成功，判断拿到的是什么数据
            switch (reason){
                case "成功的返回"://成功拿到数据
                    //执行解析操作
                    JSONObject result=jo.getJSONObject("result");
                    JSONArray data=result.getJSONArray("data");//拿到数据集合
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject jsonObjet=data.getJSONObject(i);
                        //存到hashmap里面
                        HashMap<String,Object> hashMap=new HashMap<String,Object>();
                        hashMap.put("title",jsonObjet.getString("title"));//新闻标题
                        hashMap.put("date",jsonObjet.getString("date"));//发布时间
                        hashMap.put("author_name",jsonObjet.getString("author_name"));//作者
                        hashMap.put("url",jsonObjet.getString("url"));//新闻链接
                        hashMap.put("uniquekey",jsonObjet.getString("uniquekey"));//唯一标识
                        hashMap.put("type","null");//类型一
                        hashMap.put("realtype",jsonObjet.getString("realtype"));//类型二
                        hashMap.put("img1",jsonObjet.getString("thumbnail_pic_s"));//图片一
                        hashMap.put("img2",jsonObjet.getString("thumbnail_pic_s02"));//图片二
                        hashMap.put("img3",jsonObjet.getString("thumbnail_pic_s03"));//图片三
                        list.add(hashMap);
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * JSON 解析
     * 返回list
     * hot解析
     */
    public static List<HotInfo> JSON_HotInfo(String newsname,String str){

        Log.i("ms", "开始解析，，newsname="+newsname+"  ,str="+str);
        JSONObject jo=null;//用来判断拿到的数据是否是正确的
        List<HotInfo> list=new ArrayList<HotInfo>();//定义的list，用于适配器
        if(str.equals("错误")){
            Log.i("ms","拿下来的数据错误");
        }else{
            try {
                jo=new JSONObject(str);//转换成json对象
                String reason=jo.getString("reason");//拿到返回是否成功，判断拿到的是什么数据
                switch (reason){
                    case "成功的返回"://成功拿到数据
                        Log.i("ms","开始解析");
                        //执行解析操作
                        JSONObject result=jo.getJSONObject("result");
                        JSONArray data=result.getJSONArray("data");//拿到数据集合
                        for (int i = 0; i <data.length() ; i++) {
                            JSONObject jsonObjet=data.getJSONObject(i);

                            HotInfo hi=new HotInfo();
                            hi.setName(newsname);
                            hi.setTitle(jsonObjet.getString("title"));//新闻标题
                            hi.setDate(jsonObjet.getString("date"));//发布时间
//                            hi.setAuthor_name(jsonObjet.getString("author_name"));//作者
                            hi.setUrl(jsonObjet.getString("url"));//新闻链接
//                            hi.setUniquekey("null");//唯一标识
//                            hi.setType("null");//类型一/
//                            hi.setRealtype(jsonObjet.getString("realtype"));//类型二
                            hi.setImg1(jsonObjet.getString("thumbnail_pic_s"));//图片一
//                            hi.setImg2(jsonObjet.getString("thumbnail_pic_s02"));//图片二
//                            hi.setImg3(jsonObjet.getString("thumbnail_pic_s03"));//图片三
                            list.add(hi);
                        }
                        break;
                    default:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("ms","list==="+list.toString());
        return list;
    }



//    public static Bitmap getBipmap(String... params) {//执行耗时操作
//        try {
//            URL url = new URL(params[0]);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            //这句话可以不加 默认就是GET
//            conn.setRequestMethod("GET");
//            mPicSize = conn.getContentLength();
//            InputStream is = conn.getInputStream();
//            byte[] buffer = new byte[1024];
//            out = new ByteArrayOutputStream();
//            for (int count; (count = is.read(buffer)) != -1; ) {
//                out.write(buffer, 0, count);
////                publishProgress(out.toByteArray().length);
//                Thread.sleep(100);
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = null;
//        if (out != null) {
//            bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.toByteArray().length);
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return bitmap;
//    }


//
//
//    public static List<HashMap<String,Object>> asd(List<HashMap<String,Object>> list){
//        for (int i = 0; i <list.size() ; i++) {
//            HashMap<String,Object> hashMap=list.get(i);
//            String url=hashMap.get("img").toString();
//            Bitmap bitmap=returnBitMap(url);
//            hashMap.put("tu",bitmap);
//        }
//        return list;
//    }
//
//    //拿图片
//    public static Bitmap returnBitMap(String url){
//        URL myFileUrl = null;
//        Bitmap bitmap = null;
//        try {
//            myFileUrl = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
}
