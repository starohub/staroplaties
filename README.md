![StaroPlaties](https://github.com/starohub/staroplaties/raw/master/resources/images/staroplaties-64.png)

# Staro JSON Engine for Java

### Origin

[GSON - A Java serialization/deserialization library to convert Java Objects into JSON and back](https://github.com/google/gson/tree/gson-parent-2.8.5)

### Release

[Release 0.0.2](https://github.com/starohub/staroplaties/releases/tag/0.0.2)

### Maven

```
        <!-- https://github.com/starohub/staroplaties/releases/tag/0.0.2 -->
        <dependency>
            <groupId>com.starohub.platies</groupId>
            <artifactId>staroplaties</artifactId>
            <version>0.0.2</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/lib/staroplaties.jar</systemPath>
        </dependency>
```

### Usage

```
public class T002 extends BaseCase {
    public T002() {
        super("T002");
    }

    private static class TagReader extends TagJsonReader {
        public TagReader(String json) {
            super(json);
        }

        public TagReader(Reader in) {
            super(in);
        }

        @Override
        public Object tagValue(String s) {
            if (s.contains("__b__")) {
                return s.replaceAll("__b__", "John");
            }
            if (s.contains("__c__")) {
                return 32.56;
            }
            return s;
        }

        @Override
        public String tagField(String s) {
            if (s.contains("__a__")) {
                return "a";
            }
            return s;
        }
    }

    @Override
    public void exec() {
        try {
            Map r = new HashMap<>();
            r.put("bc__a__de", "Hello world!");
            r.put("b", "Hello __b__!");
            r.put("c", "__c__");
            String t = new Platies().toJson(r);
            log("t = " + t);

            Map t2 = new Platies().fromJson(new TagReader(t), Map.class);
            String t3 = new Platies().toJson(t2);
            log("t3 = " + t3);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
```

### Results

```
[T002] Started

[T002] t = {"b":"Hello __b__!","c":"__c__","bc__a__de":"Hello world!"}

[T002] t3 = {"b":"Hello John!","c":32.56,"a":"Hello world!"}

[T002] Stopped
```
