![logo](logo.png)

<div align="center">
Immediately enjoy the easy-to-use storage service, perfect support asynchronous with callback strategy.

![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/zouchanglin/storage-spring-boot-starter)![JitPack](https://img.shields.io/jitpack/v/github/zouchanglin/storage-spring-boot-starter)[![GitHub license](https://img.shields.io/github/license/zouchanglin/storage-spring-boot-starter?label=license)](https://github.com/zouchanglin/storage-spring-boot-starter/blob/master/LICENSE)

</div>

English | [简体中文](./README.zh-CN.md) 

# Project introduction
This project integrates seven NiuYun object storage services SDK, ali cloud OOS storage services SDK, and simplifies the complicated configuration, let users to enjoy the out-of-the-box experience.
Later will support more storage service platform, and will introduce file storage management engine, help users quickly set up infrastructure services.

# Use will see

File upload was divided into two types, one is the client upload, namely the user terminal device Android/iOS or React, Vue, Angular and more the front application to upload files to seven NiuYun.
The other is a server-side upload, namely the server upload files to seven NiuYun directly.

* the client upload: need to get the corresponding Token from the service side, Token can be used only once, if it is a breakpoint continuingly, Token is valid for one hour.
* server upload: also need the server access Token itself, the use of Token for file upload operation, if it is a breakpoint continuingly, Token is valid for one hour.

So, in order to solve the problem of the complexity of file storage API, if is for the sake of the demand of the client to upload, then introduce QiNiuAuthService, convenient various access Token. If it is the server upload directly introducing QiNiuUploadService can.

# Quick start
### step1. Introduction of dependences

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



### step2. configuration AK/SK

In application, yml configuration seven cows/ali cloud AK, SK, storage space, room address.
Room address belongs to option, the configuration room address can reduce request at a time, improve efficiency of interaction, can be configured as the following, optional room (huadong) east China, north China, huabei), south China (huanan) (beimei), North America, southeast Asia, xinjiapo:

```yml
qiniu:
  access-key: your service providers's access-key
  secret-key: your service providers's secret-key
  # Spatial configuration
  bucket-name: your bucker bucket-name
  # Computer region configuration, example huadong、huabei、huanan、beimei、xinjiapo
  region: huadong
```

### step3. Injection components

```java
@Service
public class DemoService {
    @Autowired
    private QiNiuAuthService qiNiuAuthService;

    @Autowired
    private QiNiuUploadService qiNiuUploadService;
}
```

### step4. Enjoy the process

Enjoy benefits of simplified configuration.



# API reference


### Get Token

#### 1、Get generally token

```java
String token = qiNiuAuthService.getToken();
```

#### 2、Get filecover Token

```java
String oldFileName = "example.file";
String token = qiNiuAuthService.getTokenByCover(oldFileName);
```

#### 3、Get carry callback url's token

```java
String callBackUrl = "http://example.com/fileupload/callback";
String token = qiNiuAuthService.getTokenCallBack(callBackUrl);
```

Set the callback address will immediately return, return the data is NULL. Callback interface to receive data as follows:

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

About the JSON fields can refer to the explanation of [document ](https://developer.qiniu.com/kodo/manual/1235/vars#magicvar).

### File Upload

#### 4、Local file upload

```java
// Local file path
String localFilePath = "/root/example.txt";
// The target file name
String descFileName = "myQiNiuExample.txt";

ReturnBody returnBody = qiNiuUploadService.uploadLocalFile(localFilePath, descFileName, null);
```

#### 5、ByteArray upload

```java
byte[] bytes = "ExampleString".getBytes();
// The target file name
String descFileName = "myQiNiuExample";

ReturnBody returnBody = qiNiuUploadService.uploadByteArray(bytes, descFileName, null);
```

#### 6、Stream upload

```java
// The target file name
String descFileName = "myQiNiuExample";

byte[] bytes = "ExampleString".getBytes();
ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

ReturnBody returnBody = qiNiuUploadService.uploadStream(stream, descFileName, null);
```

#### 7、Three upload way callback

上面的示例代码演示了无回调URL的情况，即qiNiuUploadService.upload*()的第三个参数都是NULL。

```java
// Local file path
String localFilePath = "/root/example.txt";
// // The target file name
String descFileName = "myQiNiuExample.txt";
// callback url
String callBackUrl = "http://example.com/upload/callback";

qiNiuUploadService.uploadLocalFile(localFilePath, descFileName, callBackUrl);
```

The above example，http://example.com/upload/callback Interface to the received data is received in the form of POST ReturnBody object. Reference 3, Get carry callback url's token.

# Version update

### V1.0

Support the seven NiuYun three Token, upload files, an array of bytes, flows and breakpoint continuingly support.