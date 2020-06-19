![logo](https://img.zouchanglin.cn/storage-spring-boot-starter-logo.png)

<div align="center">
Immediately enjoy the easy-to-use storage service, perfect support asynchronous with callback strategy.
<hr>
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
        <version>1.1</version>
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

The above example code demonstrates no callback URL, namely qiNiuUploadService.upload*()'s the third parameters is all NULL.

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

### File (batch) management

#### 1、All file list

```java
@Autowired
private QiNiuManageService qiNiuManageService;

List<FileInfo> allFileList = qiNiuManageService.getAllFileList();
```

#### 2、Search file by key

Pay attention to this all match from left to right:

```java
@Autowired
private QiNiuManageService qiNiuManageService;

List<FileInfo> filesByPrefix = qiNiuManageService.getFilesByPrefix(key);
```

#### 3、Batch file's mineType modification 

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

Map<String, String> keyMineMap = new HashMap<>();
keyMineMap.put("test.png", "image/png");
keyMineMap.put("test.mp4", "image/png");
keyMineMap.put("test.mp3", "application/zip");
int changeItems = qiNiuBatchManageService.batchChangeMimeType(keyMineMap);
```

#### 4、Batch copy files

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

String[] keys = {"test.png", "test.gif", "test.mp4"};
String suffix = "copy";
int copyItems = qiNiuBatchManageService.batchCopyFile(keys, suffix);
// test.png -> test.png.copy
// test.gif -> test.gif.copy
// test.mp4 -> test.mp4.copy
```

#### 5、Batch rename files

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

Map<String, String> keyNewName = new HashMap<>();
keyNewName.put("test.png", "new.png");
keyNewName.put("test.mp4", "new.mp4");
keyNewName.put("test.mp3", "new.mp3");
int changeItems = qiNiuBatchManageService.batchRenameFile(keyNewName);
```

#### 6、Batch delete files

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

String[] keys = {"test.png", "test.gif", "test.mp4"};
int changeItems = qiNiuBatchManageService.batchDeleteFile(keys);
```

# Version update

### 1.0

Support the seven NiuYun three Token, upload files, an array of bytes, flows and breakpoint continuingly support.

### 1.1

Support single file management, batch management. BUG fix Bean assembly problem.

