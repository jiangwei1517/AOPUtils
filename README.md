# AOPUtils
## 参考资料
* 安卓AOP三剑客:APT,AspectJ,Javassist  <http://www.jianshu.com/p/dca3e2c8608a> 
* 安卓AOP实战：面向切片编程 <http://www.jianshu.com/p/b96a68ba50db>
* 深入理解Android之AOP <http://blog.csdn.net/innost/article/details/49387395>
* 【翻译】Android中的AOP编程 <http://www.jianshu.com/p/0fa8073fd144>

## BtnClickHelper
作用：***防止按钮被连续点击***
		
	@Aspect
	public class BtnAspect {
		 // 按钮连续被点击的时间间隔，默认为3000ms 
	    private static int PERIOD = 3000;
	    // onMethod
	    // com.jiangwei.aop_utils.btnclickhelper.BtnClickTwice注解地址
	    private static final String POINTCUT_METHOD = "execution(@com.jiangwei.aop_utils.btnclickhelper.BtnClickTwice * *(..))";

	    private long lastClickTime = 0;
	
	    private long currentTime = 0;
	
	    private long duration = 0;
	
	    @Pointcut(POINTCUT_METHOD)
	    public void onClick() {
	    }
	
	    @Around("onClick()")
	    public void joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
	        View view = null;
	        Object[] args = joinPoint.getArgs();
	        if (args.length == 1 && args[0] instanceof View) {
	            View v = (View) args[0];
	            currentTime = System.currentTimeMillis();
	            lastClickTime = v.getTag() != null ? (long) v.getTag() : 0;
	            duration = currentTime - lastClickTime;
	            if (duration > PERIOD) {
	                System.out.println("执行成功" + duration + "ms");
	                joinPoint.proceed();
	            } else {
	                System.out.println("执行失败" + duration + "ms");
	            }
	            v.setTag(currentTime);
	        } else {
	            throw new IllegalStateException("BtnClickTwice should be used on onClick(View view) method");
	        }
	    }
	}
	
## ClockHelper

作用：***监视方法的执行时间***

	@Aspect
	public class ClockAspect {
	    // onMethod
	    private static final String POINTCUT_METHOD = "execution(@com.jiangwei.aop_utils.clockhelper.Clock * *(..))";
	    // onConstructor
	    private static final String POINTCUT_CONSTRUCTOR = "execution(@com.jiangwei.aop_utils.clockhelper.Clock *.new(..))";
	
	    @Pointcut(POINTCUT_METHOD)
	    public void onPointCutMethod() {
	    }
	
	    @Pointcut(POINTCUT_CONSTRUCTOR)
	    public void onPointCutConstructor() {
	    }
	
	    @Around("onPointCutMethod() || onPointCutConstructor()")
	    public void joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
	        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	        // 监听的方法名字
	        String methodName = signature.getMethod().getName();
	        // 监听的全类名（simpleName）
	        String className = signature.getDeclaringType().getName();
	        WatchClock.reset();
	        WatchClock.start();
	        // 执行方法
	        joinPoint.proceed();
	        WatchClock.stop();
	        long totalTimeMillis = WatchClock.getTotalTimeMillis();
	        System.out.println(
	                "ClassName:" + className + "-->" + "MethodName:" + methodName + "-->" + "Duration:" + totalTimeMillis);
	    }
	}
	
## MemoryCacheHelper
作用：***工厂单例模式***

	@Aspect
	public class MemoryCacheAspect {
		// 根据map的key获取工厂模式的单例
	    private Map<String, Object> memoryMap = new HashMap<>();
	    private static final String POINT_CUT_METHOD =
	            "execution(@com.jiangwei.aop_utils.memorycachehelper.MemoryCache * *(..))";
	
	    @Pointcut(POINT_CUT_METHOD)
	    public void memoryCache() {
	    }
	
		//此处应该返回Object
	    @Around("memoryCache()")
	    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
	        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
	        String methodName = signature.getMethod().getName();
	        String className = signature.getDeclaringType().getSimpleName();
	        Object[] args = joinPoint.getArgs();
	        StringBuilder memoryBuilder = new StringBuilder();
	        memoryBuilder.append(className + methodName);
	        for (Object arg : args) {
	            memoryBuilder.append(((Class) arg).getSimpleName());
	        }
	        // ms（key值） = className + methodName + 参数名字
	        String ms = memoryBuilder.toString();
	        System.out.println(ms);
	        Object obj = memoryMap.get(ms);
	        if (obj != null) {
	            return obj;
	        }
	        Object result = joinPoint.proceed();
	        if (result instanceof List && result != null && ((List) result).size() > 0 // 列表不为空
	                || result instanceof String && !TextUtils.isEmpty((String) result)// 字符不为空
	                || result instanceof Object && result != null) {
	            memoryMap.put(ms, result);
	        }
	        return result;
	    }
	}
	
## PermissionHelper
作用：***检查方法的权限***

	@Aspect
	public class PermissionAspect {
		// 注解且有一个参数
	    private static final String CUT_POINT_METHOD =
	            "execution(@com.jiangwei.aop_utils.permissionhelper.PermissionCheck * *(..)) && @annotation(ann)";
	
	    @Pointcut(CUT_POINT_METHOD)
	    public void onMethod(PermissionCheck ann) {
	    }
	
		// CheckPermission：因为Aspect当中没有Context对象，所以需要将检测功能单独进行处理。
	    @Before("onMethod(ann)")
	    public void joinPoint(ProceedingJoinPoint point, PermissionCheck ann) throws Throwable {
	        if (TextUtils.isEmpty(ann.permission())) {
	            throw new IllegalStateException("you should declare permission on the method");
	        }    
	  		CheckPermission.getInstance().checkPermission(ann.permission());
	    }
	}
	
## 注意事项
	// 必须为public
 	@Pointcut(CUT_POINT_METHOD)
    public void onMethod(PermissionCheck ann) {
    }
    @Before("onMethod(ann)")
    public void joinPoint(ProceedingJoinPoint point, PermissionCheck ann) throws Throwable {
        if (TextUtils.isEmpty(ann.permission())) {
            throw new IllegalStateException("you should declare permission on the method");
        }
       CheckPermission.getInstance().checkPermission(ann.permission());
    }
    
   * Aspect当中最好引入application的context