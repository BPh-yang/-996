# pagehelper 小坑

分页插件配置

```
# 分页插件配置
pagehelper.helper-dialect=mysql
pagehelper.reasonable=true   
pagehelper.params=count=countSql
pagehelper.support-methods-arguments=false
```

配置小坑

```
pagehelper.reasonable=true
reasonable分页参数合理化，3.3.0以上版本可用，默认是false。 启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页； 禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据。

pagehelper.support-methods-arguments=false
官方介绍的不是特别清楚，其实这是个自动分页的配置，依据的是入参，如果参数中有pageNum，pageSize分页参数，则会自动分页，默认为true
```
