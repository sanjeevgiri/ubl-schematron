# schematron-example

Small example how to use schematron.

```
java -jar saxon-he-10.3.jar -s:example.sch \
    -xsl:schxslt/core/src/main/resources/xslt/2.0/compile-for-svrl.xsl \ 
    -o:example.xsl
```

```
java -jar saxon-he-10.3.jar -s:example.xml \
    -xsl:example.xsl \ 
    -o:result.xml
```

