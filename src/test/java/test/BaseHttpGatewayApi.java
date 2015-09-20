package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.bh.wechat.gateway.HttpGatewayTemplate;
import com.bh.wechat.gateway.SimpleJsonHttpGatewayTemplate;
import com.bh.wechat.response.BaseResponse;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月18日 下午1:29:59
 */
@RunWith(TestRunner.class)
public class BaseHttpGatewayApi {
	
	protected static SimpleJsonHttpGatewayTemplate httpGatewayTemplate;

	private static long startTime;

	private static long endTime;
	
	@BeforeClass
	public static void start0() throws Throwable {
		System.setProperty("catalina.home", "/Users/liufei/java/logs");// log4j
		httpGatewayTemplate = new SimpleJsonHttpGatewayTemplate();
		httpGatewayTemplate.afterPropertiesSet();
		startTime = System.nanoTime();// 纳秒
	}
	
	@AfterClass
	public static void end0() throws Throwable {
		endTime = System.nanoTime();
		System.err.println("耗时(秒): " + ((endTime - startTime) / HttpGatewayTemplate.NANO_UNIT) + "s");
	}

	@Before
	public void start() throws Throwable {
		
	}

	@After
	public void end() throws Throwable {
		
	}
	
	protected String isSuccess(BaseResponse response) {
		return response.isSuccess() ? "成功" : "失败";
	}
}