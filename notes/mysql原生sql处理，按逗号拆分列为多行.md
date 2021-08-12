# mysql原生sql处理，按逗号拆分列为多行

[mysql原生sql处理，按逗号拆分列为多行](https://www.cnblogs.com/cnsdhzzl/p/11053005.html)

```
SELECT DISTINCT
    SUBSTRING_INDEX( SUBSTRING_INDEX( ytn.value, ',', b.help_topic_id + 1 ), ',',- 1 )
FROM
    test ytn
    JOIN mysql.help_topic b ON b.help_topic_id < ( LENGTH( ytn.`value` ) - LENGTH( REPLACE ( ytn.`value`, ',', '' ) ) + 1 )
    where ytn.id=2
    
```

效果

![45248af1a14597d52e21895d288aa3d2.png](image/45248af1a14597d52e21895d288aa3d2.png)
