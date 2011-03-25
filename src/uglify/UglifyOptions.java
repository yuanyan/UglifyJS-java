package uglify;

import java.util.ArrayList;

public class UglifyOptions {
	//-v or --verbose — output some notes on STDERR (for now just how long each operation takes).
	public boolean verbose;
	
	//-b or --beautify — output indented code; when passed, additional options control the beautifier:
	public boolean beautify;
	
	 //-i N or --indent N — indentation level (number of spaces)
	public Integer indent;
	
	// -q or --quote-keys — quote keys in literal objects (by default, only keys that cannot be identifier names will be quotes).
	public boolean quoteKeys;
	
	public boolean ascii;
	
	//-nm or --no-mangle — don't mangle variable names
	public boolean noMangle;
	
	//-ns or --no-squeeze — don't call ast_squeeze() (which does various optimizations that result in smaller, less readable code).
	public boolean noSqueeze;
	
	//-mt or --mangle-toplevel — mangle names in the toplevel scope too (by default we don't do this).
	public boolean mangleToplevel;
	
	//--no-seqs — when ast_squeeze() is called (thus, unless you pass --no-squeeze) it will reduce consecutive statements in blocks into a sequence. For example, "a = 10; b = 20; foo();" will be written as "a=10,b=20,foo();". In various occasions, this allows us to discard the block brackets (since the block becomes a single statement). This is ON by default because it seems safe and saves a few hundred bytes on some libs that I tested it on, but pass --no-seqs to disable it.
	public boolean noSeqs;
	
	//--no-dead-code — by default, UglifyJS will remove code that is obviously unreachable (code that follows a return, throw, break or continue statement and is not a function/variable declaration). Pass this option to disable this optimization.
	public boolean noDeadCode;
	
	//-nc or --no-copyright — by default, uglifyjs will keep the initial comment tokens in the generated code (assumed to be copyright information etc.). If you pass this it will discard it.
	public boolean noCopyright;
	
	//-o filename or --output filename — put the result in filename. If this isn't given, the result goes to standard output (or see next one).
	public String output;
	
	//--overwrite — if the code is read from a file (not from STDIN) and you pass --overwrite then the output will be written in the same file.
	public boolean overwrite;
	
	//--ast — pass this if you want to get the Abstract Syntax Tree instead of JavaScript as output. Useful for debugging or learning more about the internals.
	public boolean ast;
	
	//--extra — enable additional optimizations that have not yet been extensively tested. These might, or might not, break your code. If you find a bug using this option, please report a test case.
	public boolean extra;
	
	//--unsafe — enable other additional optimizations that are known to be unsafe in some contrived situations, but could still be generally useful. For now only this:
	//foo.toString() ==> foo+""
	public boolean unsafe;
	//--max-line-len (default 32K characters) — add a newline after around 32K characters. I've seen both FF and Chrome croak when all the code was on a single line of around 670K. Pass –max-line-len 0 to disable this safety feature.
	public Integer maxLineLen;
	
	//--reserved-names — some libraries rely on certain names to be used, as pointed out in issue #92 and #81, so this option allow you to exclude such names from the mangler. For example, to keep names require and $super intact you'd specify –reserved-names "require,$super".
	public String reservedNames;


	
	public ArrayList<String> toArgList(){
		
		ArrayList<String> options= new ArrayList<String>();
		
		if(verbose){
			options.add("--verbose");
		} 
		if(beautify){
			options.add("--beautify");
		} 
		if(quoteKeys){
			options.add("--quote-keys");
		}  
		if(noMangle){
			options.add("--no-mangle");
		}  
		if(noSqueeze ){
			options.add("--no-squeeze");
		} 
		if(mangleToplevel){
			options.add("--mangle-toplevel");
		}  
		if(noSeqs){
			options.add("--no-seqs");
		}  
		if(noDeadCode){
			options.add("--no-dead-code");
		}  
		if(noCopyright){
			options.add("--no-copyright");
		}  
		if(overwrite){
			options.add("--overwrite");
		}  
		if(ast){
			options.add("--ast");
		} 
		if(extra){
			options.add("--extra");
		}  
		if(unsafe){
			options.add("--unsafe");
		}  
		if(ascii){
			options.add("--ascii");
		} 
		
		if(maxLineLen != null){
			options.add("--max-line-len");
			options.add(""+maxLineLen);				
		}
		
		if(indent != null){
			options.add("--indent");
			options.add(""+indent);				

		}
		if(reservedNames != null){
			options.add("--reserved-names");
			options.add(reservedNames);				
		}
		
		if(output != null){
			options.add("--output");
			options.add(output);
		}
		
		return options;	
	}

}
