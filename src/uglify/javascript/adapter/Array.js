var $AP = Array.prototype;

/*Array prototype extentions start */

/**
 * remove the element from the first argument to the second argument,
 * if the second argument not gived ,it will remove the element 
 * which the fisrt argument index.  
 * @param {Object} from
 * @param {Object} to {optional}
 */
$AP.remove = function(from, to){
	var rest = this.slice((to || from) + 1 || this.length);
	this.length = from < 0 ? this.length + from: from;
	return this.push.apply(this, rest);
};


/**
 * ECMA-262-5 15.4.4.14
 * Returns the first (least) index of an element within the array
 * equal to the specified value, or -1 if none is found.
 *
 * @param {Mixed} searchElement
 * @param {number} fromIndex {optional}
 * @return {number}
 * @example ['a','b','c'].indexOf('b') === 1;
 */
$AP.indexOf ||
($AP.indexOf = function(searchElement, fromIndex){
	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.   		
	var len = this.length;

	if (len == 0){
		return - 1;
	}

	if (fromIndex === undefined){
		fromIndex = 0;
	} else{
		// If fromIndex is negative, fromIndex from the end of the array.
		if (fromIndex < 0)
		fromIndex = len + fromIndex;
		// If fromIndex is still negative, search the entire array.
		if (fromIndex < 0)
		fromIndex = 0;
	}
	if (searchElement !== undefined){
		for (var i = fromIndex; i < len; i++){
			if (this[i] === searchElement)
			return i;
		}
		return - 1;
	}
	// Lookup through the array.
	for (var i = fromIndex; i < len; i++){
		if (this[i] === undefined && i in this){
			return i;
		}
	}
	return - 1;
});



/**
 * ECMA-262-5 15.4.4.15
 * Returns the last (greatest) index of an element within the array
 * equal to the specified value, or -1 if none is found.
 *
 * @param {Mixed} searchElement
 * @param {number} fromIndex {optional}
 * @return {number}
 * @example ['a','a','c'].lastIndexOf('a') === 1;
 */
$AP.lastIndexOf ||
($AP.lastIndexOf = function(searchElement, fromIndex){
	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.      
	var len = this.length;

	if (len == 0){
		return - 1;
	}

	if (arguments.length < 2){
		fromIndex = len - 1;
	}
	else{
		// If index is negative, index from end of the array.
		if (fromIndex < 0)
		fromIndex = len + fromIndex;
		// If fromIndex is still negative, do not search the array.
		if (fromIndex < 0)
		fromIndex = -1;
		else
		if (fromIndex >= len)
		fromIndex = len - 1;
	}
	// Lookup through the array.
	if (searchElement !== undefined){
		for (var i = fromIndex; i >= 0; i--){
			if (this[i] === searchElement)
			return i;
		}
		return - 1;
	}
	for (var i = fromIndex; i >= 0; i--){
		if (this[i] === undefined && i in this){
			return i;
		}
	}
	return - 1;


});


/**
 * ECMA-262-5 15.4.4.16
 * Returns true if every element in this array
 * satisfies the provided testing function.
 * @param {Object} callbackfn
 * @param {Object} thisArg {optional}
 */
$AP.every ||
($AP.every = function(callbackfn, thisArg){

	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.       
	var len = this.length;

	for (var i = 0; i < len; ++i){
		var current = this[i];
		if (current !== undefined || i in this){
			if (!callbackfn.call(thisArg, current, i, this))
			return false;
		}
	}

	return true;
});


/**
 * ECMA-262-5 15.4.4.17
 * Returns true if at least one element in this array
 * satisfies the provided testing function.
 * @param {Object} callbackfn
 * @param {Object} thisArg {optional}
 */
$AP.some ||
($AP.some = function(callbackfn, thisArg){

	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.          
	var len = this.length;
	for (var i = 0; i < len; ++i){
		var current = this[i];
		if (current !== undefined || i in this){
			if (callbackfn.call(thisArg, current, i, this))
			return true;
		}
	}

	return false;
});


/**
 * ECMA-262-5 15.4.4.18
 * Calls a function for each element in the array.
 * @param {Object} callbackfn
 * @param {Object} thisArg   {optional}
 */
$AP.forEach ||
($AP.forEach = function(callbackfn, thisArg){

	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.        
	var len = this.length;
	for (var i = 0; i < len; ++i){
		var current = this[i];
		if (current !== undefined || i in this){
			callbackfn.call(thisArg, current, i, this);
		}
	}
});

/**
 * ECMA-262-5 15.4.4.19
 * Creates a new array with the results of calling
 * a provided function on every element in this array.
 * @param {Object} callbackfn
 * @param {Object} thisArg
 * @return {Array}
 */
$AP.map ||
($AP.map = function(callbackfn, thisArg){

	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.          
	var len = this.length,
	result = new Array(len);
	for (var i = 0; i < len; ++i){
		var current = this[i];
		if (current !== undefined || i in this){
			result[i] = callbackfn.call(thisArg, current, i, this);
		}
	}
	return result;
});


/**
 * ECMA-262-5 15.4.4.20
 * Creates a new array with all of the elements of this array
 * for which the provided filtering function returns true.
 * @param {Object} callbackfn
 * @param {Object} thisArg
 * @return {Array}
 */
$AP.filter ||
($AP.filter = function(callbackfn, thisArg){

	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.       
	var len = this.length,
	result = [];
	for (var i = 0; i < len; ++i){
		var current = this[i];
		if (current !== undefined || i in this){
			callbackfn.call(thisArg, current, i, this) && result.push(this[i]);
		}
	}

	return result;
});


/**
 * ECMA-262-5 15.4.4.21
 * Apply a function simultaneously against two values of
 * the array (from left-to-right) as to reduce it to a single value.
 * @param {Object} callbackfn
 * @param {Object} initialValue {optional}
 */
$AP.reduce ||
($AP.reduce = function(callbackfn, current){

	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.       
	var len = this.length,
		i = 0;

	find_initial:
	if (arguments.length < 2){
		for (; i < len; i++){
			current = this[i];
			if (current !== undefined || i in this){
				i++;
				break find_initial;
			}
		}
		println("reduce of empty array with no initial value");
	}

	for (; i < len; i++){
		var element = this[i];
		if (element !== undefined || i in this){
			current = callbackfn.call(null, current, element, i, this);
		}
	}
	return current;
});


/**
 * ECMA-262-5 15.4.4.22
 * Apply a function simultaneously against two values of
 * the array (from right-to-left) as to reduce it to a single value.
 * @param {Object} callbackfn
 * @param {Object} initialValue  {optional}
 */
$AP.reduceRight ||
($AP.reduceRight = function(callbackfn, current){
	if (!san.isFunction(callbackfn))
		san.error(callbackfn + " is not a function");
	// Pull out the length so that modifications to the length in the
	// loop will not affect the looping.        
	var i = this.length - 1;

	find_initial:
	if (arguments.length < 2){
		for (; i >= 0; i--){
			current = this[i];
			if (current !== undefined || i in this){
				i--;
				break find_initial;
			}
		}
		san.error("reduce of empty array with no initial value");
	}

	for (; i >= 0; i--){
		var element = this[i];
		if (element !== undefined || i in this){
			current = callbackfn.call(null, current, element, i, this);
		}
	}
	return current;
});
