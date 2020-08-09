# usage

## build

```
$ ./gradlew clean build shadowJar
```

## run

```
$ java -jar build/libs/app-dep-tool-0.1.0-all.jar -d dirpath -n basename
```

## visualize

Prerequisites: The PlantUML Jar and Graphviz are installed.

```
$ java -jar path/to/plantuml.jar basename_01.txt
```
