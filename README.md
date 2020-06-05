
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zouchanglin/storage-spring-boot-starter)
![JitPack](https://img.shields.io/jitpack/v/github/zouchanglin/storage-spring-boot-starter)

# 项目简介
本项目集成了七牛云的对象存储服务SDK，阿里云的OOS存储服务SDK，并且简化了繁琐的配置，让用户到享受开箱即用的体验。后期将支持更多存储服务平台，并且将引入文件存储管理引擎，帮助用户快速搭建起基础服务设施。

# 使用必看

文件上传分为两种类型，一种是客户端上传，即用户终端设备Android/iOS或者React、Vue、Angular等前端应用自行上传文件至七牛云。另一种是服务端上传，即服务器端直接上传文件至七牛云。

* 客户端上传：需要从服务端获取对应的Token，Token只能使用一次，如果是断点续传，Token的有效期是一个小时
* 服务端上传：同样需要服务器自己获取Token，利用Token进行文件上传操作，如果是断点续传，Token的有效期是一个小时。

所以为了解决文件存储API的复杂性问题，如果是为了客户端上传的需求，则引入QiNiuAuthService，方便各种Token的获取。如果是服务器端上传，则直接引入QiNiuUploadService即可。

# 快速开始
### step1.引入依赖

> **Maven**

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.zouchanglin</groupId>
        <artifactId>storage-spring-boot-starter</artifactId>
        <version>v1.0</version>
    </dependency>
</dependencies>
```
> **gradle**

```groovy
allprojects {
    repositories {
        ...
            maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.zouchanglin:storage-spring-boot-starter:v1.0'
}
```



### step2.配置AK/SK

在application.yml配置七牛/阿里云的AK、SK、存储空间、机房地址。机房地址属于可选项，配置了机房地址可以减少一次请求，提升交互效率，配置为如下即可，可选的机房华东(huadong)、华北(huabei)、华南(huanan)、北美(beimei)、东南亚(xinjiapo）：

```yml
qiniu:
  access-key: 79sjsihduednaxnjdnekjnde
  secret-key: xnjdnekjnde21l3kne4br4
  # 空间配置
  bucket-name: image-server
  # 机房配置 huadong、huabei、huanan、beimei、xinjiapo
  region: huadong
```

### step3.注入组件

```java
@Service
public class DemoService {
    @Autowired
    private QiNiuAuthService qiNiuAuthService;

    @Autowired
    private QiNiuUploadService qiNiuUploadService;
}
```

# Getting Started
### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/reference/htmlsingle/#using-boot-devtools)

