package uglify;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class UglifyJS {

	public static void main(String[] args) {
		
		new UglifyJS().exec(args);
	}

	public Reader getResourceReader(String url) {

		Reader reader = null;
		
		try {
			//TODO jar包内资源加载有通常三种方式，但测试中发现只有第一种方式才找到了资源
			if(reader == null) {
				reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(url));
			}
			
			/*
			if (reader == null) {
				reader = new InputStreamReader(getClass().getResourceAsStream("../"+url));
			}

			if (reader == null) {
				reader = new InputStreamReader(getClass().getResourceAsStream("/"+url));
			}
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reader;
	}

	public void exec(String[] args) {

		ScriptEngine engine = ECMAScriptEngineFactory.getECMAScriptEngine();
		engine.put("uglify_args", args);
		engine.put("uglify_no_output", false);
		run(engine);
		
		try {
			engine.eval("uglify();");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	
	private void run(ScriptEngine engine) {
		try {

			Reader parsejsReader = getResourceReader("uglify/javascript/parse-js.js");
			Reader processjsReader = getResourceReader("uglify/javascript/process.js");
			Reader sysjsReader = getResourceReader("uglify/javascript/adapter/sys.js");
			Reader jsonjsReader = getResourceReader("uglify/javascript/adapter/JSON.js");
			Reader arrayjsReader = getResourceReader("uglify/javascript/adapter/Array.js");
			Reader uglifyjsReader = getResourceReader("uglify/javascript/uglifyjs.js");

			engine.eval(arrayjsReader);
			engine.eval(sysjsReader);
			engine.eval(parsejsReader);
			engine.eval(processjsReader);
			engine.eval(jsonjsReader);
			engine.eval(uglifyjsReader);

		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
	}

	public String uglify(String[] args){
		
		ScriptEngine engine = ECMAScriptEngineFactory.getECMAScriptEngine();
		engine.put("uglify_args", args);
		engine.put("uglify_no_output", true);
		run(engine);
		
		String result = null;
		
		try {
			result = (String)engine.eval("uglify();");
		} catch (ScriptException e) {
			e.printStackTrace();
		}		
		
		return result;
	}
	
	
	

}
