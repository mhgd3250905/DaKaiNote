package skkk.gogogo.dakainote.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 2016/10/17.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/17$ 20:35$.
*/


public class Person extends BmobObject {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


