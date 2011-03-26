###UglifyJS-java - JavaScript parser / mangler / compressor / beautifier library for  for Java

yuanyan.cao@gmail.com

###About:
  The original UglifyJS based on nodejs, but it is difficult to be automated with ant or maven integration, so i moved UglifyJS to the java platform.

###Usage£º
    java -jar uglifyjs-java.jar [ options... ] [ filename ]

  filename should be the last argument and should name the file from which to read the JavaScript code.

####Supported options:


*  -b or --beautify ¡ª output indented code; when passed, additional options control the beautifier:
    * -i N or --indent N ¡ª indentation level (number of spaces)
    * -q or --quote-keys ¡ª quote keys in literal objects (by default, only keys that cannot be identifier names will be quotes).

*  --ascii ¡ª pass this argument to encode non-ASCII characters as \uXXXX sequences. By default UglifyJS won¡¯t bother to do it and will output Unicode   characters instead. (the output is always encoded in UTF8, but if you pass this option you¡¯ll only get ASCII).

*  -nm or --no-mangle ¡ª don¡¯t mangle variable names

*  -ns or --no-squeeze ¡ª don¡¯t call ast_squeeze() (which does various optimizations that result in smaller, less readable code).

*  -mt or --mangle-toplevel ¡ª mangle names in the toplevel scope too (by default we don¡¯t do this).

*  --no-seqs ¡ª when ast_squeeze() is called (thus, unless you pass --no-squeeze) it will reduce consecutive statements in blocks into a sequence. For example, ¡°a = 10; b = 20; foo();¡± will be written as ¡°a=10,b=20,foo();¡±. In various occasions, this allows us to discard the block brackets (since the block becomes a single statement). This is ON by default because it seems safe and saves a few hundred bytes on some libs that I tested it on, but pass --no-seqs to disable it.

*  --no-dead-code ¡ª by default, UglifyJS will remove code that is obviously unreachable (code that follows a return, throw, break or continue statement and is not a function/variable declaration). Pass this option to disable this optimization.

*  -nc or --no-copyright ¡ª by default, uglifyjs will keep the initial comment tokens in the generated code (assumed to be copyright information etc.). If you pass this it will discard it.

*  -o filename or --output filename ¡ª put the result in filename. If this isn¡¯t given, the result goes to conversion output filename.min.js.

*  --overwrite ¡ª if the code is read from a file and you pass --overwrite then the output will be written in the same file.

*  --ast ¡ª pass this if you want to get the Abstract Syntax Tree instead of JavaScript as output. Useful for debugging or learning more about the internals.

*  -v or --verbose ¡ª output some notes on STDERR (for now just how long each operation takes).

*  --extra ¡ª enable additional optimizations that have not yet been extensively tested. These might, or might not, break your code. If you find a bug using this option, please report a test case.

*  --unsafe ¡ª enable other additional optimizations that are known to be unsafe in some contrived situations, but could still be generally useful. For now only this:
    foo.toString() ==> foo+¡±¡±

*  --max-line-len (default 32K characters) ¡ª add a newline after around 32K characters. I¡¯ve seen both FF and Chrome croak when all the code was on a single line of around 670K. Pass ¨Cmax-line-len 0 to disable this safety feature.

*   --reserved-names ¡ª some libraries rely on certain names to be used, as pointed out in issue #92 and #81, so this option allow you to exclude such names from the mangler. For example, to keep names require and $super intact you¡¯d specify ¨Creserved-names ¡°require,$super¡±. 

###Links: 
  UglifyJS http://github.com/mishoo/UglifyJS

###Note:
  UglifyJS-java must be running on java 1.6 version or above


