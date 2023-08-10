package ai.magicemerge.bluebird.app.service.common.constant;

public class Const {

	public final static String CURRENT_USER = "_CURRENT_USER";

	public static final String BASE_API = "/api/v1";

	public static final String ADMIN_BASE_API = "/admin/api/v1";

	public static final Long DEFAULT_WORKSPACE = 1L;

	public interface Operate {
		Integer NO = 0;
		Integer YES = 1;
	}


	public interface Valid {
		Integer TRUE = 0;
		Integer FALSE = 1;
	}



	public interface DefaultPage {
		int DEFAULT_PAGE_NUM = 1;
		int DEFAULT_PAGE_SIZE = 10;
	}

	public interface ModelType {
		String GPT_3_5 = "GPT3.5";
		String GPT_4 = "GPT4";
		String GPT_3_5_16K = "GPT3.5-16K";
	}


	public static final Boolean SYS_USER = true;


	public static final String JSON_FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

}
