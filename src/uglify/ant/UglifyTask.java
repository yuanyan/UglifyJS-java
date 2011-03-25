package uglify.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import uglify.UglifyJS;
import uglify.UglifyOptions;

/**
 * This class implements a simple Ant task to do almost the same as
 * CommandLineRunner.
 * 
 * Most of the public methods of this class are entry points for the Ant code to
 * hook into.
 * 
 */
public final class UglifyTask extends Task {

	public boolean verbose;

	// -b or --beautify — output indented code; when passed, additional options
	// control the beautifier:
	public boolean beautify;

	// -i N or --indent N — indentation level (number of spaces)
	public Integer indent;

	// -q or --quote-keys — quote keys in literal objects (by default, only keys
	// that cannot be identifier names will be quotes).
	public boolean quoteKeys;

	public boolean ascii;

	// -nm or --no-mangle — don't mangle variable names
	public boolean noMangle;

	// -ns or --no-squeeze — don't call ast_squeeze() (which does various
	// optimizations that result in smaller, less readable code).
	public boolean noSqueeze;

	// -mt or --mangle-toplevel — mangle names in the toplevel scope too (by
	// default we don't do this).
	public boolean mangleToplevel;

	// --no-seqs — when ast_squeeze() is called (thus, unless you pass
	// --no-squeeze) it will reduce consecutive statements in blocks into a
	// sequence. For example, "a = 10; b = 20; foo();" will be written as
	// "a=10,b=20,foo();". In various occasions, this allows us to discard the
	// block brackets (since the block becomes a single statement). This is ON
	// by default because it seems safe and saves a few hundred bytes on some
	// libs that I tested it on, but pass --no-seqs to disable it.
	public boolean noSeqs;

	// --no-dead-code — by default, UglifyJS will remove code that is obviously
	// unreachable (code that follows a return, throw, break or continue
	// statement and is not a function/variable declaration). Pass this option
	// to disable this optimization.
	public boolean noDeadCode;

	// -nc or --no-copyright — by default, uglifyjs will keep the initial
	// comment tokens in the generated code (assumed to be copyright information
	// etc.). If you pass this it will discard it.
	public boolean noCopyright;

	// --overwrite — if the code is read from a file (not from STDIN) and you
	// pass --overwrite then the output will be written in the same file.
	public boolean overwrite;

	// --ast — pass this if you want to get the Abstract Syntax Tree instead of
	// JavaScript as output. Useful for debugging or learning more about the
	// internals.
	public boolean ast;

	// --extra — enable additional optimizations that have not yet been
	// extensively tested. These might, or might not, break your code. If you
	// find a bug using this option, please report a test case.
	public boolean extra;

	// --unsafe — enable other additional optimizations that are known to be
	// unsafe in some contrived situations, but could still be generally useful.
	// For now only this:
	// foo.toString() ==> foo+""
	public boolean unsafe;
	// --max-line-len (default 32K characters) — add a newline after around 32K
	// characters. I've seen both FF and Chrome croak when all the code was on a
	// single line of around 670K. Pass –max-line-len 0 to disable this safety
	// feature.
	public Integer maxLineLen;

	// --reserved-names — some libraries rely on certain names to be used, as
	// pointed out in issue #92 and #81, so this option allow you to exclude
	// such names from the mangler. For example, to keep names require and
	// $super intact you'd specify –reserved-names "require,$super".
	public String reservedNames;

	// -o filename or --output filename — put the result in filename. If this
	// isn't given, the result goes to standard output (or see next one).
	//public String output;

	private String outputEncoding = "UTF-8";
	private File outputFile;
	private final List<FileList> sourceFileLists;

	public UglifyTask() {
		this.sourceFileLists = new LinkedList<FileList>();
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public void setBeautify(boolean beautify) {
		this.beautify = beautify;
	}

	public void setIndent(Integer indent) {
		this.indent = indent;
	}

	public void setQuoteKeys(boolean quoteKeys) {
		this.quoteKeys = quoteKeys;
	}

	public void setAscii(boolean ascii) {
		this.ascii = ascii;
	}

	public void setNoMangle(boolean noMangle) {
		this.noMangle = noMangle;
	}

	public void setNoSqueeze(boolean noSqueeze) {
		this.noSqueeze = noSqueeze;
	}

	public void setMangleToplevel(boolean mangleToplevel) {
		this.mangleToplevel = mangleToplevel;
	}

	public void setNoSeqs(boolean noSeqs) {
		this.noSeqs = noSeqs;
	}

	public void setNoDeadCode(boolean noDeadCode) {
		this.noDeadCode = noDeadCode;
	}

	public void setNoCopyright(boolean noCopyright) {
		this.noCopyright = noCopyright;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public void setAst(boolean ast) {
		this.ast = ast;
	}

	public void setExtra(boolean extra) {
		this.extra = extra;
	}

	public void setUnsafe(boolean unsafe) {
		this.unsafe = unsafe;
	}

	public void setMaxLineLen(Integer maxLineLen) {
		this.maxLineLen = maxLineLen;
	}

	/**
	 * super,class
	 * 
	 * @param reservedNames
	 */
	public void setReservedNames(String reservedNames) {
		this.reservedNames = reservedNames;
	}

	/**
	 * Set output file. file or dir all ok
	 */
	public void setOutput(File value) {
		this.outputFile = value;
	}

	/**
	 * Sets the source files.
	 */
	public void addSources(FileList list) {
		this.sourceFileLists.add(list);
	}

	public void execute() {

		File[] sources = findSourceFiles();

		if (sources != null) {
			log("Compiling " + sources.length + " file(s)");
			
			UglifyJS uglifyjs = new UglifyJS();
			
			ArrayList<String> options = createUglifyOptions();

			for (File source : sources) {
				
				options.add(source.getAbsolutePath());

				String[] args = new String[options.size()];

				args = options.toArray(args);

				if (this.outputFile == null) {
					
					uglifyjs.exec(args);
					
				} else {

					String result = uglifyjs.uglify(args);
					if (result != null) {
						writeResult(result);
					} else {
						throw new BuildException("uglify failed.");
					}

				}

			}

		} else {
			log("no source.");
		}
	}

	private ArrayList<String> createUglifyOptions() {

		UglifyOptions options = new UglifyOptions();

		options.ascii = this.ascii;
		options.ast = this.ast;
		options.verbose = this.verbose;
		options.beautify = this.beautify;
		options.indent = this.indent;
		options.quoteKeys = this.quoteKeys;
		options.ascii = this.ascii;
		options.noMangle = this.noMangle;
		options.noSqueeze = this.noSqueeze;
		options.mangleToplevel = this.mangleToplevel;
		options.noSeqs = this.noSeqs;
		options.noDeadCode = this.noDeadCode;
		options.noCopyright = this.noCopyright;
		options.overwrite = this.overwrite;
		options.ast = this.ast;
		options.extra = this.extra;
		options.unsafe = this.unsafe;
		options.maxLineLen = this.maxLineLen;
		options.reservedNames = this.reservedNames;

		return options.toArgList();
	}

	private File[] findSourceFiles() {
		List<File> files = new LinkedList<File>();

		for (FileList list : this.sourceFileLists) {
			files.addAll(findJavaScriptFiles(list));
		}

		return files.toArray(new File[files.size()]);
	}

	/**
	 * Translates an Ant file list into the file format that the compiler
	 * expects.
	 */
	private List<File> findJavaScriptFiles(FileList fileList) {
		List<File> files = new LinkedList<File>();
		File baseDir = fileList.getDir(getProject());

		for (String included : fileList.getFiles(getProject())) {
			files.add(new File(baseDir, included));
		}

		return files;
	}

	private void writeResult(String source) {
		if (this.outputFile.getParentFile().mkdirs()) {
			log("Created missing parent directory "
					+ this.outputFile.getParentFile(), Project.MSG_DEBUG);
		}

		try {
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(this.outputFile), this.outputEncoding);
			out.append(source);
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new BuildException(e);
		}

		log("Compiled javascript written to "
				+ this.outputFile.getAbsolutePath(), Project.MSG_DEBUG);
	}

}
