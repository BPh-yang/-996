# mysql使用sql语句查询数据库所有表注释已经表字段注释

```

1. 要查询所有表名以及表注释
SELECT
    TABLE_NAME as '表名',
    TABLE_COMMENT as '表注释'  
FROM
    information_schema.TABLES
WHERE
    table_schema = 'disciplinary_exam'
    
2. 要查询表字段的注释
SELECT
    COLUMN_NAME as '字段',
    column_comment as '注释'
FROM
    INFORMATION_SCHEMA.COLUMNS
WHERE
    table_name = 'xcxj_patrol_group'
    AND table_schema = 'disciplinary_exam'

3. 一次性查询表注释以及对应表字段注释
SELECT
    t.TABLE_NAME as '表名',
    t.TABLE_COMMENT  as '表注释',
    c.COLUMN_NAME as '字段',
    c.COLUMN_TYPE as '字段类型',
    c.COLUMN_COMMENT as '字段注释'
FROM
    information_schema.TABLES t,
    INFORMATION_SCHEMA.COLUMNS c
WHERE
    c.TABLE_NAME = t.TABLE_NAME
    AND t.`TABLE_SCHEMA` = 'disciplinary_exam'
```
