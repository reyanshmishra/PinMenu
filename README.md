PinMenu
-------------------------


A context menu like pinterest **(UNDER DEVELOPMENT)**.

 This is under development and might have bugs but I have implemented the core functionality and Algorithm,
 which is required to obtain a the desired result like pinterest context menu.

 ***Feel free to contribute**
 

Usage
-----
Clone the library and import pinmenu module to your project so that you can modify as per your need until it's not 
completely developed.

Take PinMenuHolder as a ViewGroup and add PinMenu as a child.
```xml
 
layout_pin_menu.xml
 
<?xml version="1.0" encoding="utf-8"?>
<com.reyanshmishra.pinmenu.PinMenuHolder xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:pin_holder_draw_over_view="true"
    app:pin_holder_overlay_color="#90ffffff">


  
    <com.reyanshmishra.pinmenu.PinMenu
        android:id="@+id/one"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:elevation="5dp"
        android:padding="5dp"
        android:scaleType="centerInside"
        android:src="@drawable/ic_close_black_24dp"
        app:pin_background_color="@color/white"
        app:pin_name="Cancel"
        app:pin_selected_color="#BD081C" />
 


    <com.reyanshmishra.pinmenu.PinMenu
        android:id="@+id/three"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:elevation="5dp"
        android:padding="5dp"
        android:scaleType="centerInside"
        android:src="@drawable/share_variant"
        app:pin_background_color="@color/white"
        app:pin_name="Share"
        app:pin_selected_color="#BD081C" />
 

    <com.reyanshmishra.pinmenu.PinMenu
        android:id="@+id/four"
        android:layout_width="48dp"
        android:layout_height="48dp"
        android:elevation="5dp"
        android:padding="5dp"
        android:scaleType="centerInside"
        android:src="@drawable/dots_horizontal"
        app:pin_background_color="@color/white"
        app:pin_name="More"
        app:pin_selected_color="#BD081C" />
        

</com.reyanshmishra.pinmenu.PinMenuHolder>
    
```
<img src="https://i.imgur.com/X0fgfmk.png" width=270 height=400>

```Java

 PinDialog mPinDialog = new PinDialog(this);
        mPinDialog.setContentView(R.layout.layout_pin_menu);
           mPinDialog.setPinSelectListener(new PinSelectListener() {
                    @Override
                    public void pinSelected(PinMenu pinMenu) {
                        Toast.makeText(mContext, "" + pinMenu.getPinName(), Toast.LENGTH_SHORT).show();
                    }
                });
        
        mPinDialog.addToRecyclerView(mRecyclerView);
        
        
```

Customization
-------
Currently the library offers the following customization options:
 - `pin_holder_draw_over_view`: Whether to draw over selected view or not.
 
License
-------

    Copyright 2014 - 2018 Reyansh Mishra

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.