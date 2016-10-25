package com.bupt.chengde.entity;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Category {
	private int id;
	private String mCategoryName;
	private List<ResponsePhotographer> mCategoryItem =new ArrayList<ResponsePhotographer>();
	//private List<String> mCategoryItem =new ArrayList<String>();
    public Category(String categoryName){
      this.mCategoryName=categoryName;
    }
    public String getmCategoryName() {  
        return mCategoryName;  
    }  
  
    public void addItem(ResponsePhotographer item) {  
        mCategoryItem.add(item);  
    }  
   /* public void addItem(String item) {  
        mCategoryItem.add(item);  
    }*/
      
    /** 
     *  获取Item内容 
     *  
     * @param pPosition 
     * @return 
     */  
    public Object getItem(int pPosition) {  
        // Category排在第一位  
        if (pPosition == 0) {  
            return mCategoryName;  
        } else {  
            return mCategoryItem.get(pPosition - 1);  
        }  
    }  
   /* public String getItem(int pPosition) {  
        // Category排在第一位
    	
        if (pPosition == 0) {  
        	
            return mCategoryName;  
        } else {  
        	
            return mCategoryItem.get(pPosition - 1);  
        }  
    } */
      
    /** 
     * 当前类别Item总数。Category也需要占用一个Item 
     * @return  
     */  
    public int getItemCount() {  
        return mCategoryItem.size() + 1;  
    }  


}
