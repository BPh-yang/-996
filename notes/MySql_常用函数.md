# MySql 常用函数

case函数语法： 类似于 if ... else...

```
CASE condition WHEN value1 THEN returnvalue1 WHEN value2 THEN returnvalue2 WHEN value3 THEN returnvalue3 …… ELSE defaultvalue END
```

IF\(\)函数语法：

```
select username,if(sex=1,'男','女') as sex from user;
```

IFNULL\(\) 函数语法

```
IFNULL() 函数用于判断第一个表达式是否为 NULL，如果为 NULL 则返回第二个参数的值，如果不为 NULL 则返回第一个参数的值
IFNULL(expression, alt_value)
```
