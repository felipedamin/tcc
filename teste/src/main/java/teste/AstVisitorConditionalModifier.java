package teste;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AstVisitorConditionalModifier {
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/test/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "Methods.java");

        cu.accept(new ModifierVisitor<String[]>() {
            String[] names = {"", ""}; 
            @Override
            public Visitable visit(ClassOrInterfaceDeclaration classDeclaration, String[] arg) {
                String className = classDeclaration.getNameAsString();
                this.names[0] = className;

                // "we make a call to super to ensure child nodes of the current node are also visited"
                // entao se essa classe herdar de uma que tem um visit() alterado, pode funcionar nos childnodes
                return super.visit(classDeclaration, this.names);
            }
            
            @Override
            public Visitable visit(MethodDeclaration md, String[] arg) {
                String methodName = md.getNameAsString();
                this.names[1] = methodName;
                return super.visit(md, this.names);
            }

            @Override
            public Visitable visit(IfStmt stmt, String[] arg) {
                String identifier = arg[0] + "#" + arg[1]; 
                System.out.println(identifier);

                BlockStmt clone = stmt.clone().asBlockStmt();
                Expression condition = stmt.getCondition();
                String methodDetails = identifier +", if params: " + condition;
                MethodCallExpr testExpr = new MethodCallExpr("System.out.println", new StringLiteralExpr(methodDetails));
                ExpressionStmt exprStmt = new ExpressionStmt(testExpr);
                
                // NodeList list = new NodeList<>();
                // list.add(exprStmt);
                // list.add(stmt);
                // System.out.println("stmt");
                // System.out.println(stmt);
                
                // Node parentNode = null;
                // list.setParentNode(parentNode);
                // System.out.println("parentNode");
                // System.out.println(parentNode);
                // stmt.replace(parentNode);

                // clone.asBlockStmt().addStatement(0, exprStmt);
                BlockStmt block = new BlockStmt();
                block.addStatement(exprStmt);
                block.addStatement(stmt);
                System.out.println("block");
                System.out.println(block);

                stmt.getParentNode().get().replace(block);
                // clone.replace(block);
                // System.out.println("clone");
                // System.out.println(clone);
                super.visit(stmt, arg);

                return stmt;
            }   

        }, null);
    
        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
    }
}
