# jdk8对list的各种处理实例详解，包括去重，排序，过滤，分组，统计

实体类

```
import java.util.Objects;

public class TestStreamModel {
    private int id;
    private String name;
    private int grade;
    private int classes;
    private double score;

    @Override
    public String toString() {
        return "TestStreamModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                ", classes=" + classes +
                ", score='" + score + '\'' +
                '}';
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getClasses() {
        return classes;
    }
    public void setClasses(int classes) {
        this.classes = classes;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestStreamModel that = (TestStreamModel) o;
        return id == that.id &&
                grade == that.grade &&
                classes == that.classes &&
                Double.compare(that.score, score) == 0 &&
                name.equals(that.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, grade, classes, score);
    }
}
```

测试类

```
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test {
        private static List<TestStreamModel> getList(){
            //List<TestStreamModel> list = Lists.newArrayList();
            List<TestStreamModel> list = new ArrayList<>();
            TestStreamModel testStreamModel = new TestStreamModel();
            testStreamModel.setId(2);/*主键*/
            testStreamModel.setName("张三");/*姓名*/
            testStreamModel.setClasses(1);/*班级*/
            testStreamModel.setGrade(1);/*年级*/
            testStreamModel.setScore(80);/*成绩*/
            list.add(testStreamModel);

            TestStreamModel testStreamModel1 = new TestStreamModel();
            testStreamModel1.setId(1);
            testStreamModel1.setName("李四");
            testStreamModel1.setClasses(1);
            testStreamModel1.setGrade(1);
            testStreamModel1.setScore(60);
            list.add(testStreamModel1);

            TestStreamModel testStreamModel2 = new TestStreamModel();
            testStreamModel2.setId(3);
            testStreamModel2.setName("王二麻子");
            testStreamModel2.setClasses(2);
            testStreamModel2.setGrade(1);
            testStreamModel2.setScore(90);
            list.add(testStreamModel2);

            TestStreamModel testStreamModel3 = new TestStreamModel();
            testStreamModel3.setId(4);
            testStreamModel3.setName("王五");
            testStreamModel3.setClasses(2);
            testStreamModel3.setGrade(1);
            testStreamModel3.setScore(59.5);
            list.add(testStreamModel3);

            TestStreamModel testStreamModel4 = new TestStreamModel();
            testStreamModel4.setId(8);
            testStreamModel4.setName("小明");
            testStreamModel4.setClasses(1);
            testStreamModel4.setGrade(2);
            testStreamModel4.setScore(79.5);
            list.add(testStreamModel4);

            TestStreamModel testStreamModel5 = new TestStreamModel();
            testStreamModel5.setId(5);
            testStreamModel5.setName("小红");
            testStreamModel5.setClasses(2);
            testStreamModel5.setGrade(2);
            testStreamModel5.setScore(99);
            list.add(testStreamModel5);

            TestStreamModel testStreamModel6 = new TestStreamModel();
            testStreamModel6.setId(7);
            testStreamModel6.setName("小黑");
            testStreamModel6.setClasses(2);
            testStreamModel6.setGrade(2);
            testStreamModel6.setScore(45);
            list.add(testStreamModel6);

            TestStreamModel testStreamModel7 = new TestStreamModel();
            testStreamModel7.setId(6);
            testStreamModel7.setName("小白");
            testStreamModel7.setClasses(1);
            testStreamModel7.setGrade(2);
            testStreamModel7.setScore(88.8);
            list.add(testStreamModel7);

            TestStreamModel testStreamModel8 = new TestStreamModel();
            testStreamModel8.setId(6);
            testStreamModel8.setName("小白");
            testStreamModel8.setClasses(1);
            testStreamModel8.setGrade(2);
            testStreamModel8.setScore(88.8);
            list.add(testStreamModel8);

            return list;
        }

    public static void main(String[] args) {
        TestStreamModel streamTest = new TestStreamModel();
        List<TestStreamModel> list = Test.getList();

        /*去重，去除重复对象（每个属性的值都一样的），需要注意的是要先重写对象TestStreamModel的equals和hashCode方法*/
        System.out.println("去重前："+list);
        List<TestStreamModel> distinctList = list.stream().distinct().collect(Collectors.toList());
        System.out.println("去重后："+distinctList);

        /*排序，按id升续排列，如果要降续则改成：(a, b) -> b.getId() - a.getId(); a和b都是变量名（可以按自己意愿取名字），都是list中的对象的实例*/
        System.out.println("排序前："+list);
        List<TestStreamModel> sortList = list.stream().sorted((a, b) -> a.getId() - b.getId()).collect(Collectors.toList());
        System.out.println("排序后"+sortList);

        /*过滤，按照自己的需求来筛选list中的数据，比如我筛选出不及格的（小于60分）的人,t为实例*/
        System.out.println("过滤前："+list);
        List<TestStreamModel> filterList = list.stream().filter(t -> t.getScore() < 60).collect(Collectors.toList());
        System.out.println("过滤后"+filterList);

        /*map, 提取对象中的某一元素，例子中我取的是每个人的name，注意list中类型对应，如果取的是id或者班级，就应该是integer类型*/
        System.out.println("提取前："+list);
        List<String> mapList = list.stream().map(t -> t.getName()).collect(Collectors.toList());
        System.out.println("提取后："+mapList);

        /*统计，统计所有人分数的和, 主要我设置的分数属性是double类型的，所以用mapToDouble，如果是int类型的，则需要用mapToInt*/
        double sum = list.stream().mapToDouble(t -> t.getScore()).sum();
        int count = list.stream().mapToInt(t -> t.getId()).sum();

        /*分组， 按照字段中某个属性将list分组*/
        Map<Integer, List<TestStreamModel>> map = list.stream().collect(Collectors.groupingBy(t -> t.getGrade()));
        System.out.println("按年级分组"+map);
        /*然后再对map处理，这样就方便取出自己要的数据*/
        for(Map.Entry<Integer, List<TestStreamModel>> entry : map.entrySet()){
            System.out.println("key:"+entry.getKey());
            System.out.println("value:"+entry.getValue());
        }

        /*多重分组，先按年级分组，再按班级分组*/
        Map<Integer/*年级id*/, Map<Integer/*班级id*/, List<TestStreamModel>>> groupMap = list.stream().collect(Collectors.groupingBy(t -> t.getGrade(), Collectors.groupingBy(t -> t.getClasses())));
        System.out.println("按照年级再按班级分组："+groupMap);
        System.out.println("取出一年级一班的list："+groupMap.get(1).get(1));

        /*多重分组，一般多重分组后都是为了统计，比如说统计每个年级，每个班的总分数*/
        Map<Integer/*年级id*/, Map<Integer/*班级id*/, Double>> sumMap = list.stream().collect(Collectors.groupingBy(t -> t.getGrade(), Collectors.groupingBy(t -> t.getClasses(), Collectors.summingDouble(t -> t.getScore()))));
        System.out.println(sumMap);
        System.out.println("取出一年级一班的总分："+sumMap.get(1).get(1));

        /*stream是链式的，这些功能是可以一起使用的，例如：计算每个年级每个班的及格人数*/
        Map<Integer/*年级*/, Map<Integer/*班级*/, Long/*人数*/>> integerMap = list.stream().filter(t -> t.getScore() >= 60).collect(Collectors.groupingBy(t -> t.getGrade(), Collectors.groupingBy(t -> t.getClasses(), Collectors.counting())));
        System.out.println("取出一年级一班及格人数："+integerMap.get(1).get(1));

    }
}
```

```
//List<对象>转成List<对象>
        public List<XcxjProblemRecord> setXcxjProblemRecord(List<ProblemRecord> list){
        UserLoginInfo userLogin = ThreadLocalUtils.getUserLoginInfo();
        Map<String,Object> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(PatLinConst.INITIAL_Date);
        map.put("personCode",userLogin.getUserCode());
        map.put("nowTime",sdf.format(new Date()));
        //通过用户编码和现在时间，查出巡查计划，巡查组名称
        Map<String,Object> maps = xcxjProblemRecordMapper.selectGroupAndPlan(map);
        Example example = new Example(XcxjTargetInfo.class);
        Example.Criteria criteria = example.createCriteria();
        List<XcxjProblemRecord>  XcxjProblemRecordList= Lists.transform(list, (entity) ->{
        //用过被巡查单位名称查出被巡察单位id，补全信息
        criteria.andEqualTo("dwMc",entity.getDwMc());
        XcxjProblemRecord xcxjProblemRecord =new XcxjProblemRecord();
        xcxjProblemRecord.setDwId(xcxjTargetInfoMapper.selectOneByExample(example).getDwBh());
        xcxjProblemRecord.setQuestionType(entity.getQuestionType());
        xcxjProblemRecord.setQuestionNr(entity.getQuestionNr());
        xcxjProblemRecord.setGroupName(entity.getGroupName());
        xcxjProblemRecord.setDwMc(entity.getDwMc());
        xcxjProblemRecord.setGroupId(String.valueOf(maps.get("groupId")));
        xcxjProblemRecord.setGroupName(String.valueOf(maps.get("groupName")));
        xcxjProblemRecord.setPlanId(String.valueOf(maps.get("planId")));
        UserInfoUtil.setUserInfo(xcxjProblemRecord,true);
        return setActivitiStates(xcxjProblemRecord);
    });
    return XcxjProblemRecordList;
}

========================================================================================================

//根据条件删除map中的value
map.values().removeIf(value -> value == 0);
map.entrySet().removeIf(m -> m.getValue()==0);

```

[JDK8\-Stream流常用方法\-我是七月呀\-博客园.pdf](./file/JDK8-Stream流常用方法-我是七月呀-博客园.pdf)
