package com.misdk;

import android.os.Bundle;

/**
 * Created by kam on 2016/6/14.
 */
public interface ObserverListener {
    void notifyChange(Bundle bundle, Object object);
}
