package com.example.latter.delegates;

/**
 * @author gentleman
 * @date 2018/5/13
 * @function
 */

public abstract class LatteDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T)getParentFragment();
    }
}
