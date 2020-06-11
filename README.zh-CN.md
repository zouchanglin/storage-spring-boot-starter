![logo](logo.png)

<div align="center">
立马享受到简单易用的存储服务，完美支持异步与回调策略

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zouchanglin/storage-spring-boot-starter)   ![JitPack](https://img.shields.io/jitpack/v/github/zouchanglin/storage-spring-boot-starter)[![GitHub license](https://img.shields.io/github/license/zouchanglin/storage-spring-boot-starter?label=license)](https://github.com/zouchanglin/storage-spring-boot-starter/blob/master/LICENSE)

</div>

[English](./README.md) | 简体中文

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

### step4.享受过程

尽情享用简化配置的带来的好处吧。

# API参考

### 获取Token

#### 1、获取常规Token

```java
String token = qiNiuAuthService.getToken();
```

#### 2、获取文件覆盖的Token

```java
String oldFileName = "example.file";
String token = qiNiuAuthService.getTokenByCover(oldFileName);
```

#### 3、获取带回调的Token

```java
String callBackUrl = "http://example.com/fileupload/callback";
String token = qiNiuAuthService.getTokenCallBack(callBackUrl);
```

设置回调地址的话会立即返回，返回数据为NULL。回调接口收到的数据为：

```json
{
	"bucket": "your bucket",
	"ext": ".txt",
	"fname": "example.txt",
	"fprefix": "example",
	"fsize": 1024,
	"hash": "FmFit5fEfTPsDLSyeD3OoSKqsGpS",
	"key": "example.md",
	"mimeType": "application/octet-stream",
	"uuid": "8875ea3f-b93f-4b58-9f2f-54fcd68ccab3"
}
```

关于这些JSON字段的解释可以参考[文档 ](https://developer.qiniu.com/kodo/manual/1235/vars#magicvar)

### 文件上传

#### 4、本地文件上传

```java
// 本地文件路径
String localFilePath = "/root/example.txt";
// 目标文件名称
String descFileName = "myQiNiuExample.txt";

ReturnBody returnBody = qiNiuUploadService.uploadLocalFile(localFilePath, descFileName, null);
```

#### 5、ByteArray上传

```java
byte[] bytes = "ExampleString".getBytes();
// 目标文件名称
String descFileName = "myQiNiuExample";

ReturnBody returnBody = qiNiuUploadService.uploadByteArray(bytes, descFileName, null);
```

#### 6、Stream上传

```java
// 目标文件名称
String descFileName = "myQiNiuExample";

byte[] bytes = "ExampleString".getBytes();
ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

ReturnBody returnBody = qiNiuUploadService.uploadStream(stream, descFileName, null);
```

#### 7、三种上传回调

上面的示例代码演示了无回调URL的情况，即qiNiuUploadService.upload*()的第三个参数都是NULL。

```java
// 本地文件路径
String localFilePath = "/root/example.txt";
// 目标文件名称
String descFileName = "myQiNiuExample.txt";
// 回调URL
String callBackUrl = "http://example.com/upload/callback";

qiNiuUploadService.uploadLocalFile(localFilePath, descFileName, callBackUrl);
```

上述例子中，http://example.com/upload/callback 接口接收的数据就是以POST形式接收的ReturnBody对象。参考3、获取带回调的Token中的JSON数据。

# 版本更新

### V1.0

支持七牛云的三种Token获取，文件、字节数组、流的上传，且支持断点续传。