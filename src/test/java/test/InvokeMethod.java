package test;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.junit.Assert;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.bh.wechat.response.BaseResponse;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月19日 下午2:01:59
 */
public class InvokeMethod extends Statement {

	private final FrameworkMethod fTestMethod;
	
	private Object fTarget;
	
	private final ScriptEngineManager factory;
    private final ScriptEngine engine;

	public InvokeMethod(FrameworkMethod testMethod, Object target) {
		fTestMethod = testMethod;
		fTarget = target;
		factory = new ScriptEngineManager();
		engine = factory.getEngineByName("JavaScript");
	}

	@Override
	public void evaluate() throws Throwable {
		Object ret = null;
		try {
			ret = fTestMethod.invokeExplosively(fTarget);
		} catch (Throwable e) {
			e.printStackTrace(System.err);
		}
		AssertResponse resp = fTestMethod.getMethod().getAnnotation(AssertResponse.class);
		if (ret != null && ret instanceof BaseResponse && resp != null) {
			BaseResponse response = (BaseResponse) ret;
			ScriptContext context = new SimpleScriptContext();
	        Bindings engineScope = context.getBindings(ScriptContext.ENGINE_SCOPE);
	        engineScope.put("$success", response.isSuccess() ? "成功" : "失败");
	        engineScope.put("$returnObj", response.toString());
	        engine.eval("print(" + resp.value() + ");", context);
			Assert.assertTrue(response.isSuccess());;
	        return;
		}
		System.out.println("结果：" + ret);
		Assert.assertNotNull(ret);
	}
}