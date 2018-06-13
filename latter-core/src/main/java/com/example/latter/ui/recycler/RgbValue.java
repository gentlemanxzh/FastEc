package com.example.latter.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * @author gentleman
 * @date 2018/6/13
 * @function 用来存储颜色的一个类
 */

@AutoValue
public abstract class RgbValue {

     public abstract int red();

     public abstract int green();

     public abstract int blue();

     public static RgbValue create(int red,int green,int blue){
          return new AutoValue_RgbValue(red,green,blue);
     }
}
