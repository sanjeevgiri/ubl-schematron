import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ValidateSchema {

    public static void main(String[] args) {
        try {
            Processor processor = new Processor(false);
            XsltCompiler compiler = processor.newXsltCompiler();
            XsltExecutable xslt = compiler.compile(new StreamSource(
                new File("target/example.xsl")
            ));
            XsltTransformer transformer = xslt.load();

            transformer.setSource(new StreamSource(new File("example.xml")));
            XdmDestination chainResult = new XdmDestination();
            transformer.setDestination(chainResult);
            transformer.transform();

            List<String> errorList = new ArrayList<>();
            XdmNode rootnode = chainResult.getXdmNode();
            for (XdmNode node : rootnode.children().iterator().next().children()) {
                if(!"failed-assert".equals(node.getNodeName().getLocalName())) continue;
                String res = node.children().iterator().next().getStringValue();
                errorList.add(trim(res));
            }

            for (String s : errorList) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String trim(String s) {
        s = s.replaceAll("\n", "").replaceAll("\t", " ");
        while (s.indexOf("  ") != -1) {
            s = s.replaceAll("  ", " ");
        }
        return s.trim();
    }
}
