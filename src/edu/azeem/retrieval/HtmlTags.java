package edu.azeem.retrieval;

public enum HtmlTags {

	HTML("<html.*?</html>"),
	
	HEAD("<head>.*?</head>"),
	HEAD_UPPERCASE("<HEAD>.*?</HEAD>"),
	
	HEAD1("<head.*?</head>"),
	HEAD1_UPPERCASE("<HEAD.*?</HEAD>"),	
	
	STYLE("<style.*?</style>"),
	STYLE_UPPERCASE("<STYLE.*?</STYLE>"),
	
	SCRIPT("<script.*?</script>"),
	SCRIPT_UPPERCASE("<SCRIPT.*?</SCRIPT>"),
	
	DIV("<div.*?</div>"),
	DIV_UPPERCASE("<DIV.*?</DIV>")
	;
	
	private String _value;
	
	private HtmlTags(String value){
		setValue(value);
	}
	
	public void setValue(String value) {
		this._value = value;
	}
	
	public String getValue() {
		return _value;
	}
	
}
