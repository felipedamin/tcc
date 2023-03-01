import integracao.Methods;
import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;

@RunWith(JQF.class)
public class SimpleTest {
	//----------------------------------------------------------------------------------------------
	@Fuzz
	public void fuzz(String randomInput) {
		Methods.funcaoNestedIfs();
		Integer a = Methods.getA();
		Methods.funcaoIfElseNested();
		Methods.funcaoIfElseIf();
		Methods.funcaoIfmultiplasCondicoes();
		Methods.funcaoIfTryCatch();
		Methods.funcaoIfElseTryCatch();
		Methods.funcaoIfNestedTryCatch();
		Methods.funcaoTernarioNested();
		Methods.funcaoForWhile();
		Methods.funcaoSwitch(true, a);
		Methods.funcaoSwitch(false, a);
		Methods.funcaoSwitchNested(false, a);
		Methods.funcaoSwitchNested(true, a);


	}
	//----------------------------------------------------------------------------------------------
}
