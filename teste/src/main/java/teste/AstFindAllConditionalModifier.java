package teste;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;

public class AstFindAllConditionalModifier {
    public static Statement addBeforeThisStmt;
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/test/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        // Process all if's:
        cu.findAll(IfStmt.class).forEach(ifStmt -> {
            findAndAddLog(ifStmt);
        }); 
        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
    }

    public static NodeList<Statement> findTopLevelIf(Node parent) {
        IfStmt parentAsIfStmt = ((IfStmt) parent);
        addBeforeThisStmt = parentAsIfStmt;
        NodeList<Statement> statements = new NodeList<>();
        
        if (parentAsIfStmt.getParentNode().isPresent()) {
            Node granpa = parentAsIfStmt.getParentNode().get();
            if (granpa.getClass().getName() == "com.github.javaparser.ast.stmt.IfStmt") {
                statements = findTopLevelIf(granpa);
            }
            else {
                statements = ((BlockStmt) parentAsIfStmt.getParentNode().get()).getStatements();
            } 
        }
        return statements;
    }

    public static void findAndAddLog(IfStmt ifStmt) {
        Expression condition = ifStmt.getCondition();
            // adicionei essa condiçao para tentar evitar o erro dos "else if"... nao deu certo (acho q da pra remover...)
            // if (!ifStmt.hasCascadingIfStmt()) {
                // We have to manipulate the list the if is in, so let's figure it out:
                ifStmt.getParentNode()
                        // Se o código java é válido, sempre vai existir esse parent
                        .map(parent -> {
                            addBeforeThisStmt = ifStmt;
                            System.out.println(parent.getClass().getName());
                            // System.out.println(parent);
                            NodeList<Statement> statements = new NodeList<>();
                            if (parent.getClass() == IfStmt.class) {
                                statements = findTopLevelIf(parent);
                            }
                            if (parent.getClass().getName() == "com.github.javaparser.ast.stmt.SwitchEntry") {
                                SwitchEntry parentAsSwitchEntry = ((SwitchEntry) parent);
                                statements = parentAsSwitchEntry.getStatements();
                            }
                            if (parent.getClass().getName() == "com.github.javaparser.ast.stmt.BlockStmt") {
                                statements = ((BlockStmt) parent).getStatements();
                            }
                            return statements;
                        })
                        // pelo que entendi a prox linha esta chamando getStatements() para cada item do .map()
                        //.map(BlockStmt::getStatements)
                        // removi pois estou fazendo isso no .map() anterior
                        .ifPresent(statements -> {
                            if (statements.size() > 0) {
                                String methodDetails = "TODO:nomeDaClasse#nomeDoMetodo" +", if params: " + condition;
                                MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                                ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                                // System.out.println("statements: ");
                                // System.out.println(statements);
                                // System.out.println("ifStmt: ");
                                // System.out.println(ifStmt);
                                statements.addBefore(exprStmt, addBeforeThisStmt); 
                            }
    
                            // Copy the statements in the then-block next to the if statement.
                            // thenBlock.getStatements().forEach(thenStmt ->
                            //         // Use addBefore to get them in the right order (try addAfter to see why)
                            //         // Clone the statement we're copying to avoid touching the existing AST.
                            //         statements.addBefore(thenStmt.clone(), ifStmt));
    
                            // Remove the if statement. (Try removing this line.)
                            // ifStmt.remove();
                        });
                // }
            
    }
}
