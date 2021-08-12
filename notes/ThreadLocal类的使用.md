# ThreadLocal类的使用

threadlocal而是一个线程内部的存储类，可以在指定线程内存储数据，数据存储以后，只有指定线程可以得到存储数据

工具类，可以根据业务自行修改

```
/*
*
*  * Copyright 2019 http://www.hswebframework.org
*  *
*  * Licensed under the Apache License, Version 2.0 (the "License");
*  * you may not use this file except in compliance with the License.
*  * You may obtain a copy of the License at
*  *
*  *     http://www.apache.org/licenses/LICENSE-2.0
*  *
*  * Unless required by applicable law or agreed to in writing, software
*  * distributed under the License is distributed on an "AS IS" BASIS,
*  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  * See the License for the specific language governing permissions and
*  * limitations under the License.
*
*/

package com.lswx.udssystem.util;

import com.lswx.udssystem.constant.SysConstant;
import com.lswx.udssystem.pojo.dto.SysLog;
import com.lswx.udssystem.pojo.po.UserLoginInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
* @author LSWXXY-WQ
*/
@SuppressWarnings("unchecked")
public final class ThreadLocalUtils {

    private ThreadLocalUtils() {
    }

    private static final ThreadLocal<Map<String, Object>> local = ThreadLocal.withInitial(HashMap::new);

    /**
     * @return threadLocal中的全部值
     */
    public static Map<String, Object> getAll() {
        return local.get();
    }

    /**
     * 设置一个值到ThreadLocal
     *
     * @param key   键
     * @param value 值
     * @param <T>   值的类型
     * @return 被放入的值
     * @see Map#put(Object, Object)
     */
    public static <T> T put(String key, T value) {
        local.get().put(key, value);
        return value;
    }

    /**
     * 删除参数对应的值
     *
     * @param key
     * @see Map#remove(Object)
     */
    public static void remove(String key) {
        local.get().remove(key);
    }

    /**
     * 清空ThreadLocal
     *
     * @see Map#clear()
     */
    public static void clear() {
        local.remove();
    }

    /**
     * 从ThreadLocal中获取值
     *
     * @param key 键
     * @param <T> 值泛型
     * @return 值, 不存在则返回null, 如果类型与泛型不一致, 可能抛出{@link ClassCastException}
     * @see Map#get(Object)
     * @see ClassCastException
     */
    public static <T> T get(String key) {
        return ((T) local.get().get(key));
    }

    /**
     * 从ThreadLocal中获取值,并指定一个当值不存在的提供者
     *
     * @see Supplier
     * @since 3.0
     */
    public static <T> T get(String key, Supplier<T> supplierOnNull) {
        return ((T) local.get().computeIfAbsent(key, k -> supplierOnNull.get()));
    }

    /**
     * 获取一个值后然后删除掉
     *
     * @param key 键
     * @param <T> 值类型
     * @return 值, 不存在则返回null
     * @see this#get(String)
     * @see this#remove(String)
     */
    public static <T> T getAndRemove(String key) {
        try {
            return get(key);
        } finally {
            remove(key);
        }
    }

    public static void setUserLoginInfo(UserLoginInfo loginInfo) {
        put(SysConstant.USER_LOGIN_INFO, loginInfo);
    }

    public static UserLoginInfo getUserLoginInfo() {
        return get(SysConstant.USER_LOGIN_INFO);
    }

    public static void setAppName(String appName) {
        put(SysConstant.APP_NAME, appName);
    }

    public static String getAppName() {
        return get(SysConstant.APP_NAME);
    }

    public static void setSysLog(SysLog sysLog){
        put(SysConstant.OPERATE_LOG,sysLog);
    }
    public static SysLog getSysLog(){
        return get(SysConstant.OPERATE_LOG);
    }

    //    public static void setUsername(String username) {
    //        put(Constants.USER_NAME, username);
    //    }
    //
    //    public static String getUsername() {
    //        return get(Constants.USER_NAME);
    //    }

}
```
