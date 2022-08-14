package org.tedros.fx.domain;


public enum TButtonStyleEnum {
	
	GREEN ("-fx-background-color:  \n"+
	        "linear-gradient(#f0ff35, #a9ff00),  \n"+
	        "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);  \n"+
		    "-fx-background-radius: 6, 5;  \n"+
		    "-fx-background-insets: 0, 1;  \n"+
		    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); \n"+
		    "-fx-text-fill: #395306; \n"),
		    
	ROUND_RED (	"-fx-background-color: linear-gradient(#ff5400, #be1d00); \n"+
			    "-fx-background-radius: 30; \n"+
			    "-fx-background-insets: 0; \n"+
			    "-fx-text-fill: white; " ),
	BEVEL_GREY ("-fx-background-color: \n"+ 
		        "linear-gradient(#f2f2f2, #d6d6d6), \n"+
		        "linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%), \n"+
		        "linear-gradient(#dddddd 0%, #f6f6f6 50%); \n"+
			    "-fx-background-radius: 8,7,6; \n"+
			    "-fx-background-insets: 0,1,2; \n"+
			    "-fx-text-fill: black; \n"+
			    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); \n"),
	    
	GLASS_GREY ("-fx-background-color:  \n"+ 
		        "#c3c4c4, \n"+
		        "linear-gradient(#d6d6d6 50%, white 100%), \n"+
		        "radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%); \n"+
			    "-fx-background-radius: 30; \n"+
			    "-fx-background-insets: 0,1,1; \n"+
			    "-fx-text-fill: black; \n"+
			    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );"),
	SHINY_ORANGE (	"-fx-background-color: \n"+ 
			        "linear-gradient(#ffd65b, #e68400), \n"+
			        "linear-gradient(#ffef84, #f2ba44), \n"+
			        "linear-gradient(#ffea6a, #efaa22), \n"+
			        "linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%), \n"+
			        "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0)); \n"+
				    "-fx-background-radius: 30; \n"+
				    "-fx-background-insets: 0,1,2,3,0; \n"+
				    "-fx-text-fill: #654b00; \n"+
				    "-fx-font-weight: bold; \n"+
				    "-fx-font-size: 14px; \n"+
				    "-fx-padding: 5 10 5 10; \n"),
	DARK_BLUE ( "-fx-background-color: \n"+ 
		        "#090a0c, \n"+
		        "linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%), \n"+
		        "linear-gradient(#20262b, #191d22), \n"+
		        "radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); \n"+
			    "-fx-background-radius: 5,4,3,5; \n"+
			    "-fx-background-insets: 0,1,2,0; \n"+
			    "-fx-text-fill: white; \n"+
			    "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); \n"+
			    "-fx-font-family: \"Arial\"; \n"+
			    "-fx-text-fill: linear-gradient(white, #d0d0d0); \n"+
			    "-fx-font-size: 12px; \n"+
			    "-fx-padding: 5 10 5 10; ",
			    "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 ); \n"),
	RECORD_SALES (	"-fx-padding: 4 8 8 8; \n"+
					"-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; \n"+
					"-fx-background-radius: 8; \n"+
					"-fx-background-color: \n"+
					"linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), \n"+
					"#9d4024,"+
					"#d86e3a,"+
					"radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); \n"+
					"-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); \n"+
				    "-fx-font-weight: bold; \n"+
				    "-fx-font-size: 1.1em; ",
/*#record-sales:hover*/ 
				    "-fx-background-color: \n"+ 
				    "linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), \n"+
				    "#9d4024, \n"+
				    "#d86e3a, \n"+
				    "radial-gradient(center 50% 50%, radius 100%, #ea7f4b, #c54e2c); ",
/*#record-sales:pressed*/ 
				    "-fx-padding: 5 10 8 10; \n"+
				    "-fx-background-insets: 2 0 0 0,2 0 3 0, 2 0 4 0, 2 0 5 0; ",

/* #record-sales Text */ 
	    			"-fx-fill: white; \n"+
	    			"-fx-effect: dropshadow( gaussian , #a30000 , 0,0,0,2 ); \n"),
	RICH_BLUE (	"-fx-background-color: \n"+ 
	        	"#000000, \n"+
	        	"linear-gradient(#7ebcea, #2f4b8f), \n"+
		        "linear-gradient(#426ab7, #263e75), \n"+
		        "linear-gradient(#395cab, #223768); \n"+
			    "-fx-background-insets: 0,1,2,3; \n"+
			    "-fx-background-radius: 3,2,2,2; \n"+
			    "-fx-padding: 5 10 5 10; \n"+
			    "-fx-text-fill: white; \n"+
			    "-fx-font-size: 12px; ",
/*	#rich-blue Text */
			    "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.8) , 0, 0.0 , 0 , 1); \n"),
	BIG_YELLOW ("-fx-background-color: \n"+ 
	        	"#ecebe9, \n"+
		        "rgba(0,0,0,0.05), \n"+
		        "linear-gradient(#dcca8a, #c7a740), \n"+
		        "linear-gradient(#f9f2d6 0%, #f4e5bc 20%, #e6c75d 80%, #e2c045 100%), \n"+
		        "linear-gradient(#f6ebbe, #e6c34d); \n"+
			    "-fx-background-insets: 0,9 9 8 9,9,10,11; \n"+
			    "-fx-background-radius: 50; \n"+
			    "-fx-padding: 5 10 5 10; \n"+
			    "-fx-font-family: \"Helvetica\"; \n"+
			    "-fx-font-size: 18px; \n"+
			    "-fx-text-fill: #311c09; \n"+
			    "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.1) , 2, 0.0 , 0 , 1);",
/* #big-yellow Text */
			    "-fx-effect: dropshadow( one-pass-box , rgba(255,255,255,0.5) , 0, 0.0 , 0 , 1); \n"),
	IPHONE ("-fx-background-color: \n"+ 
	        "#a6b5c9, \n"+
	        "linear-gradient(#303842 0%, #3e5577 20%, #375074 100%), \n"+
	        "linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%); \n"+
		    "-fx-background-insets: 0 0 -1 0,0,1; \n"+
		    "-fx-background-radius: 5,5,4; \n"+
		    "-fx-padding: 5 10 5 10; \n"+
		    "-fx-text-fill: #242d35; \n"+
		    "-fx-font-family: \"Helvetica\"; \n"+
		    "-fx-font-size: 12px; \n"+
		    "-fx-text-fill: white; ",
/*	#iphone Text */ 
		    "-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.8) , 0, 0.0 , 0 , -1 ); \n"),
	IPAD_DARK_GREY ("-fx-background-color: \n"+ 
			        "linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), \n"+
			        "linear-gradient(#020b02, #3a3a3a), \n"+
			        "linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%), \n"+
			        "linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%), \n"+
			        "linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%); \n"+
				    "-fx-background-insets: 0,1,4,5,6; \n"+
				    "-fx-background-radius: 9,8,5,4,3; \n"+
				    "-fx-padding: 5 10 5 10; \n"+
				    "-fx-font-family: \"Helvetica\"; \n"+
				    "-fx-font-size: 18px; \n"+
				    "-fx-font-weight: bold; \n"+
				    "-fx-text-fill: white; \n"+
				    "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1); ",
/*	#ipad-dark-grey Text */ 
				     "-fx-effect: dropshadow( one-pass-box , black , 0, 0.0 , 0 , -1 ); \n"),
	IPAD_GREY (	"-fx-background-color: \n"+ 
		        "linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), \n"+
		        "linear-gradient(#020b02, #3a3a3a), \n"+
		        "linear-gradient(#b9b9b9 0%, #c2c2c2 20%, #afafaf 80%, #c8c8c8 100%), \n"+
		        "linear-gradient(#f5f5f5 0%, #dbdbdb 50%, #cacaca 51%, #d7d7d7 100%); \n"+
			    "-fx-background-insets: 0,1,4,5; \n"+
			    "-fx-background-radius: 9,8,5,4; \n"+
			    "-fx-padding: 5 10 5 10; \n"+
			    "-fx-font-family: \"Helvetica\"; \n"+
			    "-fx-font-size: 18px; \n"+
			    "-fx-font-weight: bold; \n"+
			    "-fx-text-fill: #333333; \n"+
			    "-fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1); ",
/* #ipad-grey Text */
			    "-fx-effect: dropshadow( one-pass-box , white , 0, 0.0 , 0 , 1 ); \n"),
	LION_DEFAULT (	"-fx-background-color: \n"+ 
			        "rgba(0,0,0,0.08), \n"+
			        "linear-gradient(#5a61af, #51536d), \n"+
			        "linear-gradient(#e4fbff 0%,#cee6fb 10%, #a5d3fb 50%, #88c6fb 51%, #d5faff 100%); \n"+
				    "-fx-background-insets: 0 0 -1 0,0,1; \n"+
				    "-fx-background-radius: 5,5,4; \n"+
				    "-fx-padding: 3 10 3 10; \n"+
				    "-fx-text-fill: #242d35; \n"+
				    "-fx-font-size: 14px; \n"),
	LION (	"-fx-background-color: \n"+ 
			"rgba(0,0,0,0.08), \n"+
			"linear-gradient(#9a9a9a, #909090), \n"+
			"linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%); \n"+
			"-fx-background-insets: 0 0 -1 0,0,1; \n"+
		    "-fx-background-radius: 5,5,4; \n"+
		    "-fx-padding: 3 10 3 10; \n"+
		    "-fx-text-fill: #242d35; \n"+
		    "-fx-font-size: 14px; \n"),
	WINDOWS7_DEFAULT (	"-fx-background-color: \n"+ 
				        "#3c7fb1, \n"+
				        "linear-gradient(#fafdfe, #e8f5fc), \n"+
				        "linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%); \n"+
					    "-fx-background-insets: 0,1,2; \n"+
					    "-fx-background-radius: 3,2,1; \n"+
					    "-fx-padding: 3 10 3 10; \n"+
					    "-fx-text-fill: black; \n"+
					    "-fx-font-size: 14px; \n"),
	WINDOWS7 (	"-fx-background-color: \n"+ 
		        "#707070, \n"+
		        "linear-gradient(#fcfcfc, #f3f3f3), \n"+
		        "linear-gradient(#f2f2f2 0%, #ebebeb 49%, #dddddd 50%, #cfcfcf 100%); \n"+
			    "-fx-background-insets: 0,1,2; \n"+
			    "-fx-background-radius: 3,2,1; \n"+
			    "-fx-padding: 3 10 3 10; \n"+
			    "-fx-text-fill: black; \n"+
			    "-fx-font-size: 14px; \n");
		  
		    
	
	private String buttonStyle;
	private String textStyle;
	private String hover;
	private String pressed;
	
	private TButtonStyleEnum(String style) {
		this.buttonStyle = style;
	}
	
	private TButtonStyleEnum(String style, String text) {
		this.buttonStyle = style;
		this.textStyle = text;
	}
	
	private TButtonStyleEnum(String style, String houver, String pressed, String text) {
		this.buttonStyle = style;
		this.textStyle = text;
		this.hover = houver;
		this.pressed = pressed;
	}
	
	public static TButtonStyleEnum fromId(String id){
		String name = id.toUpperCase().replaceAll("-", "_");
		return valueOf(name);
	}
	
	@Override
	public String toString() {
		return buttonStyle;
	}

	public String getButtonStyle() {
		return buttonStyle;
	}

	public String getTextStyle() {
		return textStyle;
	}

	public String getHover() {
		return hover;
	}

	public String getPressed() {
		return pressed;
	}
	
	public String getId(){
		return name().toLowerCase().replaceAll("_", "-");
	}
	 
	
}
