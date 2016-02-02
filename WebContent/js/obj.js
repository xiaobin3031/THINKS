function Text(){
	this.txt = "";
	this.i = 1;
	this.t = '';
	this.n = '';
	this._ = function(v){
		this.txt += (typeof v == 'undefined' ? '' : v) + this.n;
		return this;
	}
	this._t = function(v){
		this.txt += _x(this.t,this.i) + (typeof v == 'undefined' ? '' : v) + this.n;
		return this;
	}
	this.__ = function(v){
		this.txt = v + this.txt.toString();
		return this;
	}
	this.toString = function(){
		return this.txt.toString();
	}
	this.close = function(){
		this.txt = "";
		this.i = 1;
		this.t = '';
		this.n = '';
		return this;
	}
	this.sett = function(v){
		this.t = typeof v == 'undefined' ? '\t' : '';
	}
	this.setn = function(v){
		this.n = typeof v == 'undefined' ? '\n' : '';
	}
	this.settn = function(v){
		this.sett(v);
		this.setn(v);
	}
}

function Map(){
	this.map = {};
	this.m = function(key,value){
		if(typeof value == 'undefined'){
			return map[key];
		}else{
			map[key] = value;
			return this;
		}
	}
}

function _x(v,i){
	var t = ''
	if(typeof i != 'undefined' && typeof v != 'undefined' && i>=0){
		while(i>0){
			t += v;
			i--;
		}
		return t;
	}else
		return '';
	
}
