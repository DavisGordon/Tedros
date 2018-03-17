package com.tedros.fxapi.annotation.parser;

import javafx.scene.Node;

import com.tedros.fxapi.annotation.scene.TNode;

public class TNodeParser extends TAnnotationParser<TNode, Node>{
	
	private static TNodeParser instance;
	
	private TNodeParser(){
		
	}
	
	public static TNodeParser getInstance(){
		if(instance==null)
			instance = new TNodeParser();
		return instance;
		
	}
		
}
