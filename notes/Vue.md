# Vue

1.Vue的特点: ①遵循MVVM模式

                     ②编码简洁，体积小，运行效率高，适合移动/PC开发

                     ③它本身只关注UI，可以轻松引入vue插件或其他第三方开发项目

2.Vue扩展插件

        vue\-cli：vue脚手架

        vue\-resource（axios）：ajax请求

        vue\-router：路由

        vuex：状态管理

        vue\-lazyload：图片懒加载

        vue\-scroller：页面滑动相关

        mint\-ui：基于vue的UI组件库（移动端）

        element\-ui：基于vue的UI组件库（PC端）

3.Vue的使用

```
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>01_HelloWorld</title>
</head>
<body>

<!--
  1. 引入Vue.js
  2. 创建Vue对象
    el : 指定根element(选择器)
    data : 初始化数据(页面可以访问)
  3. 双向数据绑定 : v-model
  4. 显示数据 : {{xxx}}
  5. 理解vue的mvvm实现
-->

<!--模板-->
<div id="app">
  <input type="text" v-model="username">
  <p>Hello {{username}}</p>
</div>

<script type="text/javascript" src="../js/vue.js"></script>
<script type="text/javascript">
//创建Vue实例
  const app = new new Vue({ //配置对象
    el: '#app', //elemetn ：选择器
    data: { //数据（model）
      username: 'Hello Vue!'
    }
  })
</script>
</body>
</html>
```

4.理解Vue的MMVM

        view：视图，模板页面

        model：模型，数据对象（data）

        viewModel：视图模型（Vue的实例）

5.Vue模板语法

```
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>02_模板语法</title>
</head>
<body>
<!--
1. 模板的理解:
  动态的html页面
  包含了一些JS语法代码
    大括号表达式
    指令(以v-开头的自定义标签属性)
2. 双大括号表达式
  语法: {{exp}} 或 {{{exp}}}
  功能: 向页面输出数据
  可以调用对象的方法
3. 指令一: 强制数据绑定
  功能: 指定变化的属性值
  完整写法:
    v-bind:xxx='yyy'  //yyy会作为表达式解析执行
  简洁写法:
    :xxx='yyy'
4. 指令二: 绑定事件监听
  功能: 绑定指定事件名的回调函数
  完整写法:
    v-on:click='xxx'
  简洁写法:
    @click='xxx'
-->
<div id="app">
  <h2>1. 双大括号表达式</h2>
  <p>{{msg}}</p>
  <!-- 全部大写 -->
  <p>{{msg.toUpperCase()}}</p>
  <p v-text="msg"></p> <!-- textCotent 该标签把值理解为文本 -->
  <p v-html="html"></p> <!--innerHTML 该标签把值理解为标签  -->

  <h2>2. 指令一: 强制数据绑定</h2>
  <img v-bind:src="imgUrl" >
  <img :src="imgUrl" > <!-- 简写 -->

  <h2>3. 指令二: 绑定事件监听</h2>
  <button v-on:click="button">按钮</button>
  <button @click="button">按钮2</button> <!-- 简写 -->
  <button @click="button2(msg)">按钮3</button> <!-- 把msg的值传到button2()去 -->
</div>
<script type="text/javascript" src="../js/vue.js"></script>
<script type="text/javascript">
//创建Vue实例
new Vue({
  //绑定模板
  el:'#app',
  data : { //data里的所有属性都会成功成为vm对象的属性，而模板页面中可以直接访问
      msg:'I will back!',
      html:'<a href="http://www.baidu.com">html</a>',
      text:'我是文本',
      imgUrl:'https://cn.vuejs.org/images/logo.png'
  },
  methods: {
    button(){
      alert('点了button按钮!')
    },
    button2(content){
      alert(content)
    }
  }
})
</script>
</body>
</html>
```
