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

public class AstFindAllConditionalModifier {
    public static void main(String[] args) throws IOException {
        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
        // In this case the root directory is found by taking the root from the current Maven module,
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogForEachMethod.class).resolve("src/test/java/teste"));
        // Our sample is in the root of this directory, so no package name.
        CompilationUnit cu = sourceRoot.parse("", "App.java");

        // Process all if's:
        cu.findAll(IfStmt.class).forEach(ifStmt -> {
            // This is the then block, cloned to avoid touching the AST (for now)
            BlockStmt thenBlock = ifStmt.getThenStmt().clone().asBlockStmt();
            // We have to manipulate the list the if is in, so let's figure it out:
            ifStmt.getParentNode()
                    // Do we have a parent? (In valid Java there should be one)
                    // Assume it's a BlockStmt (you might want to improve this to do what you want)
                    // TODO: isso esta dando problema com os else if - pq neles o parentBlock é outro ifstmt
                    .map(parent -> {
                        return ((BlockStmt) parent);
                    })
                    // Get the statements (one of these should be the if-statement)
                    .map(BlockStmt::getStatements)
                    .ifPresent(statements -> {
                        // Copy the statements in the then-block next to the if statement.
                        thenBlock.getStatements().forEach(thenStmt ->
                                // Use addBefore to get them in the right order (try addAfter to see why)
                                // Clone the statement we're copying to avoid touching the existing AST.
                                statements.addBefore(thenStmt.clone(), ifStmt));
                        // Remove the if statement. (Try removing this line.)
                        // ifStmt.remove();
                    });
        });
        System.out.println(cu);
    
        // This saves back the file we read with the changes we made. Easy!
        sourceRoot.saveAll();
    }
}
