# Java反射的简单应用

```
Book book = new Book();
book.setName("杨炳浩");
//获取这个类的所有属性
Field[] fields = book.getClass().getDeclaredFields();
for (Field field : fields) {
    //获取属性的值
    field.get(book);
    //获取属性的名字
     field.getName();
}
```
