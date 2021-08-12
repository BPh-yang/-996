# Linux 上安装Reids基本流程

1、检查安装依赖程序

yum install gcc\-c\+\+

yum install \-y tcl

yum install wget

2、获取安装文件

wget [http://download.redis.io/releases/](http://download.redis.io/releases/)

这里面有很多版本，自己选择需要的下载

3、解压文件

   自己新建一个目录将redis解压到里面

tar \-zxvf redis\-3.2.01.tar.gz

mv redis\-3.2.01 /usr/local/redis

4、进入目录

cd /usr/local/redis

5、编译安装

make

make install

6、设置配置文件路径

mkdir \-p /etc/redis

cp redis.conf/etc/redis

7、修改配置文件

　redis.conf是redis的配置文件，redis.conf在redis源码目录。

　注意修改port作为redis进程的端口,port默认6379。如果需要搭建redis集群，千万别忘了修改端口号。

   redis有两种启动方式，直接运行bin/redis\-server将以前端模式启动，前端模式启动的缺点是ssh命令窗口关闭则redis\-server程序结束，不推荐使用此方法。如下图：

   ![f7006ec1148c4344baf0f6361ee30c8a.png](image/f7006ec1148c4344baf0f6361ee30c8a.png)

 **  后端模式启动**

   修改redis.conf配置文件， daemonize yes 以后端模式启动。**推荐！**

   打开redis.conf,使用命令 :/ daemonize 快速查找到daemonize然后修改。

vi /etc/redis/redis.conf

仅修改： daemonize yes （no\-\-\>yes）

8、启动

/usr/local/bin/redis\-server /etc/redis/redis.conf

9、查看启动

ps \-ef | grep redis

10、使用客户端

redis\-cli

\>set name david

OK

\>get name

"david"

11.关闭客户端

redis\-cli shutdown

12、开机启动配置

echo "/usr/local/bin/redis\-server /etc/redis/redis.conf &" \>\> /etc/rc.local

开机启动要配置在 rc.local 中，而 /etc/profile 文件，要有用户登录了，才会被执行。

13、设置密码

因为这是给局域网内的很多人使用，所以设置一个访问密码很有必要。

修改redis.conf文件配置 

使用命令 :/ requirepass 快速查找到 \# requirepass foobared 然后去掉注释，这个foobared改为自己的密码。然后wq保存。

14、重启redis

 sudo service redis restart  这个时候尝试登录redis，发现可以登上，但是执行具体命令是提示操作不允许

1. redis\-cli \-h 127.0.0.1 \-p 6379  
2. redis 127.0.0.1:6379\>  
3. redis 127.0.0.1:6379\> keys \*  
4. **\(error\) ERR operation not permitted �**�

尝试用密码登录并执行具体的命令看到可以成功执行

1. redis\-cli \-h 127.0.0.1 \-p 6379 \-a password
2. redis 127.0.0.1:6379\> keys \*
3. 1\) "myset"  
4. 2\) "mysortset"  
5. redis 127.0.0.1:6379\> select 1  
6. OK  

如果是自己在本机上使用现在已经可以了，因为我这是局域网内提供给大家使用的所以还需要最后的配置。

当时修改开发的配置文件后，启动项目报错。

org.springframework.data.redis.RedisConnectionFailureException: Cannot get Jedis connection; nested exception is redis.clients.jedis.exceptions.JedisConnectionException: **Could not get a resource from the pool**

    at org.springframework.data.redis.connection.jedis.JedisConnectionFactory.fetchJedisConnector\(JedisConnectionFactory.java:162\) ~\[spring\-data\-redis\-1.5.0.RELEASE.jar:1.5.0.RELEASE\]

    at org.springframework.data.redis.connection.jedis.JedisConnectionFactory.getConnection\(JedisConnectionFactory.java:251\) ~\[spring\-data\-redis\-1.5.0.RELEASE.jar:1.5.0.RELEASE\]

    at org.springframework.data.redis.connection.jedis.JedisConnectionFactory.getConnection\(JedisConnectionFactory.java:58\) ~\[spring\-data\-redis\-1.5.0.RELEASE.jar:1.5.0.RELEASE\]

    at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection\(RedisConnectionUtils.java:128\) ~\[spring\-data\-redis\-1.5.0.RELEASE.jar:1.5.0.RELEASE\]

 打开cmd 然后使用 telnet ip 端口 来ping 配置的redis（要保证redis已启动），发现无法ping通。

这是因为在redis.conf中有个配置 bind 127.0.0.1 这个是默认只有本机访问，把这个注释掉就好了，注释以后查看redis进程就变为下面这样：

\[root@localhost redis\]\# ps \-ef | grep redis

root      5655     1  0 11:40 ?        00:00:23 **./redis\-server \*:6379**

root     21184 18040  0 17:33 pts/1    00:00:00 grep \-\-color=auto redis

这个**\***号就表示允许其它用户访问了。然后在用打开本机的 cmd使用 telnet ip 端口 就能ping通了。

 

以上是全部内容，不足之处欢迎指出，互相交流才有进步！
