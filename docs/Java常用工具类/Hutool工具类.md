# Hutool工具类

## MapUtil

通过Map的Key排序
```Java
Map<String, Map<String, List<RectificationMeasuresExcleModel>>> collect = xszgRectificationMeasures.stream().collect(Collectors.groupingBy(a -> a.getQuestionType(), Collectors.groupingBy(b -> b.getQuestionBx())));
            
            TreeMap<String, Map<String, List<RectificationMeasuresExcleModel>>> sort = MapUtil.sort(collect, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);//默认：升序排列  
                    // return o2.compareTo(o1);  // 降序排列  
                }
            });
```