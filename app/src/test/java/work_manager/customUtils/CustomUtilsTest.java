package work_manager.customUtils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import work_manager.JSONHandler.JSONHandler;

/**
 * Testing the methods connected to the CustomUtils class
 */
public class CustomUtilsTest {

	@Before
	public void setup() {
		CustomUtils.checkDirectories();
		CustomUtils.checkFiles();
	}

	/**
	 * Testing that all the necessary directories and files are properly created
	 */
	@Test
	public void directoryAndFileSetupTest() {
		File f = new File("data/");
		assertEquals(f.exists() && f.isDirectory(), true);
		f = new File("data/common");
		assertEquals(f.exists() && f.isDirectory(), true);
		f = new File("data/project");
		assertEquals(f.exists() && f.isDirectory(), true);
		f = new File("data/user");
		assertEquals(f.exists() && f.isDirectory(), true);
		f = new File("data/common/config.json");
		assertEquals(f.exists() && f.isFile(), true);
		f = new File("data/common/users.json");
		assertEquals(f.exists() && f.isFile(), true);
	}

	/**
	 * Setting up the config file as if someone was logged in then testing it is
	 * properly written into the config file
	 */
	@Test
	public void configFileTest() {
		CustomUtils.logoutConfig();
		JSONObject configData = JSONHandler.readConfigData();
		assertEquals(configData.get("loggedin"), "");
		CustomUtils.loginConfig("testUserID");
		configData = JSONHandler.readConfigData();
		assertEquals(configData.get("loggedin"), "testUserID");
		CustomUtils.logoutConfig();
		configData = JSONHandler.readConfigData();
		assertEquals(configData.get("loggedin"), "");
	}
}
