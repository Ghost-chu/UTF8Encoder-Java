# UTF8Encoder-Java
UTF8Encoder Java edition

## 制作属于你自己的UTF8Encoder

## 云端API
您可以在FilenameExtension.java中替换API地址为你自己的，但是API需要可以：

### 获取黑白名单数据
访问方式：`GET`
```json
{"white":["1","2","3"],"black":["a","b","c"]}
```

### 提交未知拓展名后缀数据
访问方式：`POST`
```json
{"unknown":["a","b","c"],"key":"YOUR PASSWORD HERE"}
```
key字段默认为`public`，可在SubmitUnknown.java中修改key字段.
