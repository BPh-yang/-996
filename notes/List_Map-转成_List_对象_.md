# List<Map> 转成 List<对象>

```
List<Map<String, Object>> maps = xcxjDailyWorkMapper.queryDailWorkAndPage(queryDailyWorkParam);
JSONArray jsonArray = new JSONArray();
jsonArray.addAll(maps);
List<XcxjDailyWork> list= jsonArray.toJavaList(XcxjDailyWork.class);
```
