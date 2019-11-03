## 背景

这个工程参考 arthas 热更新原理，实现实现在线热更新代码。

>ps:重点提醒，线上代码千万不要随便热更新代码！！！Arthas 提醒您：**诊断千万条，规范第一条，热更不规范，同事两行泪**。

详情参考： [Arthas实践--jad/mc/redefine线上热更新一条龙](http://hengyunabc.github.io/arthas-online-hotswap/)

## 使用方法

git clone 完整代码工程之后，运行 `mvn clean package` 打包工程，然后将会在 `target` 目录下生成 `hotswap-jdk.jar`。

运行主程序需要指定两个参数，第一个为需要热更新代码 Java 程序 Pid(进程 ID)，第二个为需要热更新替换 class 文件路径。

JDK 8 运行指令为：

`java -Xbootclasspath/a:$JAVA_HOME/lib/tools.jar  -jar hotswap-jdk.jar PID classpath`

> $JAVA_HOME 为当前系统 JAVA_HOME 的路径，若不存在，可以替换成 Java 安装位置的全路径。

JDK 9 之后运行指定为:

`java -jar hotswap-jdk.jar PID classpath`


## 相关问题

由于上述热更新需要使用 JDK tools.jar 中 API，而 JDK8 之前 tools.jar 与我们常用 jar 包并不在一个文件夹中，所以可能导致maven 编译，程序运行发生错误。解决方法参考如下：

如果使用 JDK9 之后，不用担心下面问题。

### maven 编译 tools.jar 找不到

需要再 pom 下增加

```xml
        <dependency>
            <groupId>jdk.tools</groupId>
            <artifactId>jdk.tools</artifactId>
            <scope>system</scope>
            <version>1.6</version>
            <systemPath>${java.home}/../lib/tools.jar</systemPath>
        </dependency>
```

或者

```xml
        <dependency>
            <groupId>com.github.olivergondza</groupId>
            <artifactId>maven-jdk-tools-wrapper</artifactId>
            <version>0.1</version>
            <scope>provided</scope>
            <optional>true</optional>
        </dependency>
```

### java 运行 tools.jar 找不到


运行参数加入 `Xbootclasspath/a:tools.jar全路径`

`java -jar -Xbootclasspath/a:${java_home}/lib/tools.jar`


## 帮助文档

[深入探索 Java 热部署](https://www.ibm.com/developerworks/cn/java/j-lo-hotdeploy/index.html)

[Instrumentation 新功能](https://www.ibm.com/developerworks/cn/java/j-lo-jse61/index.html)