package com.anshare.project.core;

/**
* @ClassName ProjectConstant
* @Description 项目常量
* @Author wukunfan
* @Date 2018/11/12 17:34
* @UpdateUser:
* @UpdateDate:     2018/11/12 17:34
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public final class ProjectConstant {
    public static final String BASE_PACKAGE = "com.anshare.project";//生成代码所在的基础包名称，可根据自己公司的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";//生成的Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";//生成的Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//生成的ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//生成的Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";//Mapper插件基础接口的完全限定名

    //分页常量设置
    public static final String PAGE_INDEX = "0";//当前页默认值
    public static final String PAGE_SIZE = "0";//页数大小默认值

    //响应code常量设置
    public static final int ACCESS_INVALID = 210; //权限不足
    public static final int SERVICE_INVALID = 500; //服务器异常
    public static final int TOKEN_INVALID = 600; //Token失效
    public static final int URL_INVALID = 601;//URL不存在
    public static final int PARM_INVALID = 602;//参数异常信息
    public static final int PARM_TEST = 10001;//系统专用测试参数
    public static final int IMPORT_FAIL = 603;//导入数据失败

    //档案状态常量设置
    public static final int DOCUMENT_BLANK = 700; //未查询到历史档案，是否创建空白档案信息
    public static final int DOCUMENT_EXIST = 701; //该年度档案已录入，请重新选择档案年度!
    public static final int DOCUMENT_YEAR = 702; //未查到档案信息，正在创建XX年度档案信息

    //用户登录信息常量设置
    public static final int USER_OR_PASSWORD_INVALID = 800;//用户名或密码错误
    public static final int USER_NO_ROLE = 801;//该用户未绑定角色

    public static final String USER_DEFAULT_PASSWORD= "admin123";//默认密码

    //word 转 pdf常量设置
    public static Integer SuccessStatus=0;        //状态:成功
    public static Integer FailStatus=1;           //状态:失败
    public static String DocToPDF="DocToPDF";
    public static String DocToDocx="DocToDocx";
    public static String DocToTexT="DocToTexT";
    public static String DocToXps="DocToXps";
    public static String HtmlToDoc="HtmlToDoc";
    public static String DocToJPEG="DocToJPEG";
    public static String DocxToPDF="DocxToPDF";

    //超级查询常量设置
    public static String Table_A ="a.";//主表

    //简历常量设置
    public static String RESUME_A ="_resume_a";//主表

}
