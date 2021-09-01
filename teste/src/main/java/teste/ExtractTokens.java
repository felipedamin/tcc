package teste;

import com.github.javaparser.ast.Node;
import java.util.List;

public class ExtractTokens {
    public List<Node> tokens;

    public static void main(Node node) {
        System.out.println("ExtractTokens");
        if (node.getChildNodes().size() == 0) {
            System.out.println("caso base");
            System.out.println(node);
        }
        
        if (node.getChildNodes().size() > 0) {
            System.out.println("chamar recursao e adicionar token à lista");
        }
    }

}
