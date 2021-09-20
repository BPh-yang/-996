# controller返回值封装

```Java
package com.ybh.project.util;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {
    
    public R() {
        this.put((String)"code", 200);
        this.put((String)"msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put((String)"code", code);
        r.put((String)"msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put((String)"msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
```

## 分页插件小坑
分页插件配置
```properties
# 分页插件配置
pagehelper.helper-dialect=mysql
pagehelper.reasonable=true   
pagehelper.params=count=countSql
pagehelper.support-methods-arguments=false
```
pagehelper.reasonable=true
reasonable分页参数合理化，3.3.0以上版本可用，默认是false。 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页； 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据。

pagehelper.support-methods-arguments=false
官方介绍的不是特别清楚，其实这是个自动分页的配置，依据的是入参，如果参数中有pageNum，pageSize分页参数，则会自动分页，默认为true