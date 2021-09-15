# Nacos
官网：https://nacos.io/zh-cn/docs/quick-start.html

Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。

Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施。

## 快速开始
### 从 Github 上下载源码方式
```Java
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U  
ls -al distribution/target/

// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin
```
### 下载编译后压缩包方式
```Java
  unzip nacos-server-$version.zip 或者 tar -xvf nacos-server-$version.tar.gz
  cd nacos/bin
```

### 启动服务器
Linux/Unix/Mac
启动命令(standalone代表着单机模式运行，非集群模式):
```Java
sh startup.sh -m standalone
```

Windows 启动命令
```Java
startup.cmd -m standalone
```

### 服务注册&发现和配置管理
服务注册
```Java
curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
```

服务发现
```Java
curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'
```

发布配置
```Java
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=HelloWorld"
```

获取配置
```Java
curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
```
## 配置文件
### 版本依赖
```java
<!--父依赖>
 <!-- spring-cloud-alibab-dependencies -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- spring-cloud-dependencies -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

            <!-- spring-boot-dependencies -->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.3.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>

<!--子依赖>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```

### 配置文件
```properties
server.port=8080

spring.application.name=service1

#nacos
# 配置中心的地址
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
#文件后缀，dataid 的名称就是application的name加上file-extension service1.yaml
spring.cloud.nacos.config.file-extension=properties
#指定命名空间
spring.cloud.nacos.config.namespace=97680f64-5986-44ab-87c9-ea6665d0c65b
#指定分组
spring.cloud.nacos.config.group=TEST_GROUP
#DataId 在默认分组，不支持动态更新
spring.cloud.nacos.config.ext-config[0].data-id=ext-config-common01.properties
#DataId 指定分组，不支持动态更新
spring.cloud.nacos.config.ext-config[1].data-id=ext-config-common02.properties
spring.cloud.nacos.config.ext-config[1].group=GLOBALE_GROUP
#DataId 指定分组，支持动态更新
spring.cloud.nacos.config.ext-config[2].data-id=ext-config-common03.properties
spring.cloud.nacos.config.ext-config[2].group=REFRESH_GROUP
spring.cloud.nacos.config.ext-config[2].refresh=true

```