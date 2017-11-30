package com.arsan.niloufar.karyab;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niloufar on 4/16/2016.
 */
public class IDS {
        private int id;
        private String name;
        private long data;
        public IDS()
        {
        }
        public IDS(int id,String name,long data)
        {
            this.id=id;
            this.name=name;
            this.data=data;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }

        public void setData(long data) {
            this.data = data;
        }
        public int getId() {
            return id;
        }
        public long getData() {
            return data;
        }
        public String getName() {
            return name;
        }
        public static List<Long> storeInDB (String Name,long Data, Context context){
            DBHandler db = new DBHandler(context);
            List<Long> data = new ArrayList<Long>();
            IDS id = new IDS();
            id.setName(Name);
            id.setData(Data);
            db.addIDS(id);
            List<IDS> lISTID = db.getAllIDSs();
            for (IDS item : lISTID) {
                if (item.getName().equals(Name)) {
                    data.add(item.getData());
                }
            }
            return data;
        }
    }