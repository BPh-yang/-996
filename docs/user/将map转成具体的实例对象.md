# 将map转成具体的实例对象

依赖

```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.73</version>
</dependency>
```

```
JSONArry jsonArrry = new JSONArry();
jsonArry.addAll(XXX)
jsonArry.toJavaList(XXX.class);
```
