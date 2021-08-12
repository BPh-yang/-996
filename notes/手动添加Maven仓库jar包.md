# 手动添加Maven仓库jar包

这里以Orcale驱动依赖举例

由于Oracle数据库是收费的，所以oracle数据库的驱动依赖，maven仓库不提供，需要手动添加，具体操作如下：

1、到Oracle安装目录找到jdbc驱动，目录：\-Oracle\\product\\12.2.0\\dbhome\_1\\jdbc\\lib，复制ojdbc6.jar，这里我把它拷贝到F盘.  全路径F:\\ojdbc6.jar。

2、dos下执行命令mvn install:install\-file \-DgroupId=com.oracle \-DartifactId=ojdbc6 \-Dversion=10.2.0.2.0 \-Dpackaging=jar \-Dfile=F:\\ojdbc6.jar（注：得配置好mvn的环境变量）

3、执行完毕之后，出现BUILD SUCCESS，就说明成功了，就可以到C:\\Users\\XXX\\.m2\\repository\\com\\oracle\\ojdbc6\\目录里面把10.2.0.2.0整个文件夹复制到自己手动配置的maven本地仓库了。

4、pom.xml文件引入依赖：
