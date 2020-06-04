
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zouchanglin/storage-spring-boot-starter)
![JitPack](https://img.shields.io/jitpack/v/github/zouchanglin/storage-spring-boot-starter)

# 项目简介
本项目集成了七牛云的对象存储服务SDK，阿里云的OOS存储服务SDK，并且简化了繁琐的配置，让用户到享受开箱即用的体验。后期将支持更多存储服务平台，并且将引入文件存储管理引擎，帮助用户快速搭建起基础服务设施。

# 快速开始
### step1.引入依赖
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
### step2.配置AK/SK
![mark](https://img.zouchanglin.cn///20200604/NzAeTTgkKCJ7.png)

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

