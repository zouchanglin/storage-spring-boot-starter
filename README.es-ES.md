![logo](https://img.zouchanglin.cn/storage-spring-boot-starter-logo.png)

<div align="center">
Disfruta inmediatamente del servicio de almacenamiento fácil de usar, con soporte perfecto para asíncrono con estrategia de callback.
<hr>
</div>

English | [简体中文](./README.zh-CN.md) 

# Introducción del proyecto
Este proyecto integra siete SDK de servicios de almacenamiento de objetos de NiuYun, el SDK de servicios de almacenamiento OOS de Ali Cloud, y simplifica la configuración complicada, permitiendo a los usuarios disfrutar de la experiencia directa.
Más adelante se admitirán más plataformas de servicio de almacenamiento, y se introducirá un motor de gestión de almacenamiento de archivos, ayudando a los usuarios a configurar rápidamente servicios de infraestructura.

# Cómo usar

La carga de archivos se divide en dos tipos: uno es la carga del cliente, es decir, el dispositivo terminal del usuario Android/iOS o React, Vue, Angular y más aplicaciones frontales cargan archivos a NiuYun.
El otro es la carga del lado del servidor, es decir, el servidor carga archivos directamente a NiuYun.

* Carga del cliente: necesita obtener el Token correspondiente del lado del servicio, el Token solo se puede usar una vez, si es una continuación de punto de interrupción, el Token es válido por una hora.
* Carga del servidor: también necesita el Token de acceso del propio servidor, el uso del Token para la operación de carga de archivos, si es una continuación de punto de interrupción, el Token es válido por una hora.

Por lo tanto, para resolver el problema de la complejidad de la API de almacenamiento de archivos, si es para satisfacer la demanda de carga del cliente, entonces se introduce QiNiuAuthService, conveniente para varios Tokens de acceso. Si es la carga directa del servidor, se introduce QiNiuUploadService.

# Inicio rápido
### Paso 1. Introducción de dependencias

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



### Paso 2. Configuración de AK/SK

En application.yml configura AK y SK de NiuYun/Ali Cloud, nombre del espacio de almacenamiento y dirección de la sala.
La dirección de la sala es opcional; configurar la dirección de la sala puede reducir las solicitudes a la vez, mejorando la eficiencia de la interacción, se puede configurar como sigue, salas opcionales (huadong) China Oriental, (huabei) China Septentrional, (huanan) China Meridional, (beimei) América del Norte, (xinjiapo) Sudeste Asiático:

```yml
qiniu:
  access-key: tu access-key del proveedor de servicios
  secret-key: tu secret-key del proveedor de servicios
  # Configuración del espacio de almacenamiento
  bucket-name: tu nombre de bucket
  # Configuración de la región del equipo, ejemplo huadong、huabei、huanan、beimei、xinjiapo
  region: huadong
```

### Paso 3. Inyección de componentes

```java
@Service
public class DemoService {
    @Autowired
    private QiNiuAuthService qiNiuAuthService;

    @Autowired
    private QiNiuUploadService qiNiuUploadService;
}
```

### Paso 4. Disfruta del proceso

Disfruta de los beneficios de la configuración simplificada.



# Referencia de API


### Obtener Token

#### 1. Obtener token general

```java
String token = qiNiuAuthService.getToken();
```

#### 2. Obtener Token de sobrescritura de archivo

```java
String oldFileName = "example.file";
String token = qiNiuAuthService.getTokenByCover(oldFileName);
```

#### 3. Obtener Token con URL de callback

```java
String callBackUrl = "http://example.com/fileupload/callback";
String token = qiNiuAuthService.getTokenCallBack(callBackUrl);
```

La dirección de callback devuelve inmediatamente, los datos devueltos son NULL. La interfaz de callback recibe los datos de la siguiente manera:

```json
{
	"bucket": "tu bucket",
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

Sobre los campos JSON puedes consultar la explicación de la [documentación](https://developer.qiniu.com/kodo/manual/1235/vars#magicvar).

### Subida de archivos

#### 4. Subida de archivo local

```java
// Ruta del archivo local
String localFilePath = "/root/example.txt";
// El nombre del archivo de destino
String descFileName = "myQiNiuExample.txt";

ReturnBody returnBody = qiNiuUploadService.uploadLocalFile(localFilePath, descFileName, null);
```

#### 5. Subida de ByteArray

```java
byte[] bytes = "ExampleString".getBytes();
// El nombre del archivo de destino
String descFileName = "myQiNiuExample";

ReturnBody returnBody = qiNiuUploadService.uploadByteArray(bytes, descFileName, null);
```

#### 6. Subida de Stream

```java
// El nombre del archivo de destino
String descFileName = "myQiNiuExample";

byte[] bytes = "ExampleString".getBytes();
ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

ReturnBody returnBody = qiNiuUploadService.uploadStream(stream, descFileName, null);
```

#### 7. Callback de los tres métodos de subida

Los ejemplos de código anteriores demuestran que no hay URL de callback, es decir, el tercer parámetro de qiNiuUploadService.upload*() es todos NULL.

```java
// Ruta del archivo local
String localFilePath = "/root/example.txt";
// El nombre del archivo de destino
String descFileName = "myQiNiuExample.txt";
// URL de callback
String callBackUrl = "http://example.com/upload/callback";

qiNiuUploadService.uploadLocalFile(localFilePath, descFileName, callBackUrl);
```

El ejemplo anterior, la interfaz http://example.com/upload/callback recibe los datos en forma de objeto ReturnBody mediante POST. Consulta el punto 3, Obtener Token con URL de callback.

### Gestión de archivos (por lotes)

#### 1. Lista de todos los archivos

```java
@Autowired
private QiNiuManageService qiNiuManageService;

List<FileInfo> allFileList = qiNiuManageService.getAllFileList();
```

#### 2. Buscar archivo por clave

Presta atención: esta búsqueda coincide de izquierda a derecha:

```java
@Autowired
private QiNiuManageService qiNiuManageService;

List<FileInfo> filesByPrefix = qiNiuManageService.getFilesByPrefix(key);
```

#### 3. Modificación por lotes del tipo MIME de archivos

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

Map<String, String> keyMineMap = new HashMap<>();
keyMineMap.put("test.png", "image/png");
keyMineMap.put("test.mp4", "image/png");
keyMineMap.put("test.mp3", "application/zip");
int changeItems = qiNiuBatchManageService.batchChangeMimeType(keyMineMap);
```

#### 4. Copia por lotes de archivos

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

#### 5. Renombrado por lotes de archivos

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

Map<String, String> keyNewName = new HashMap<>();
keyNewName.put("test.png", "new.png");
keyNewName.put("test.mp4", "new.mp4");
keyNewName.put("test.mp3", "new.mp3");
int changeItems = qiNiuBatchManageService.batchRenameFile(keyNewName);
```

#### 6. Eliminación por lotes de archivos

```java
@Autowired
private QiNiuBatchManageService qiNiuBatchManageService;

String[] keys = {"test.png", "test.gif", "test.mp4"};
int changeItems = qiNiuBatchManageService.batchDeleteFile(keys);
```

# Actualización de versiones

### 1.0

Soporta los tres Tokens de NiuYun, carga de archivos, array de bytes, flujos y soporte de continuación de punto de interrupción.

### 1.1

Soporta gestión de archivos individuales y gestión por lotes. Corrección de errores en el ensamblaje de Beans.

### 1.2
Soporta ReturnBody personalizado, es decir, cuando el callback puede traer sus propios datos personalizados.
